import os
import oracledb
import requests
import json
from datetime import datetime
 
# ---------------------- Conexão ----------------------
def conectar_bd() -> oracledb.Connection | None:
    """
    Conecta ao banco de dados Oracle.
 
    Retorna:
        conn (oracledb.Connection): Conexão com o banco.
        None: Caso haja erro na conexão.
    """
    try:
        conn = oracledb.connect(
            user="RM565537",
            password="050607",
            dsn="oracle.fiap.com.br:1521/ORCL"
        )
        return conn
    except Exception as e:
        print("Erro ao conectar no BD:", e)
        return None
 
# ---------------------- Funções Paciente ----------------------
def criar_json_paciente(lista_pacientes: list) -> None:
    """
    Recebe uma lista de pacientes e pergunta ao usuário se deseja
    exportar os dados para um arquivo JSON.
 
    Args:
        lista_pacientes (list): Lista de tuplas com dados dos pacientes.
    """
    escolha = input("\nExportar para JSON (s/n)? ").lower()
    while escolha not in ["s", "n"]:
        escolha = input("Inválido! Digite apenas (s/n): ").lower()
 
    if escolha == "s":
        lista_json = []
        for p in lista_pacientes:
            lista_json.append({
                "id_paciente": p[0],
                "nome": p[1],
                "data_nascimento": str(p[2]),
                "idade": p[3],
                "sexo": p[4],
                "cpf": p[5],
                "telefone": p[6],
                "email": p[7],
                "endereco": p[8]
            })
 
        nome_json = input("Nome do arquivo JSON: ")
        with open(nome_json + ".json", "w", encoding="utf-8") as f:
            json.dump(lista_json, f, ensure_ascii=False, indent=4)
        print(f"\nArquivo JSON criado: {nome_json}.json")
 
 
def criar_json_consulta(lista_consultas: list) -> None:
    """
    Recebe uma lista de consultas e pergunta ao usuário se deseja
    exportar os dados para um arquivo JSON.
 
    Args:
        lista_consultas (list): Lista de tuplas com dados das consultas.
    """
    escolha = input("\nExportar para JSON (s/n)? ").lower()
    while escolha not in ["s", "n"]:
        escolha = input("Inválido! Digite (s/n): ").lower()
 
    if escolha == "s":
        lista_json = []
        for p in lista_consultas:
            lista_json.append({
                "id_consulta": p[0],
                "nome": p[1],
                "data_hora": str(p[2]),
            })
 
        nome_json = input("Nome do arquivo JSON: ")
        with open(nome_json + ".json", "w", encoding="utf-8") as f:
            json.dump(lista_json, f, ensure_ascii=False, indent=4)
        print(f"\nArquivo JSON criado: {nome_json}.json")
 
 
def cadastrar_paciente(conn, cursor) -> None:
    """
    Solicita informações do paciente ao usuário, valida entradas
    e cadastra no banco de dados.
    """
    try:
        print("----- CADASTRAR PACIENTE -----")
 
        # Validação de nome
        nome = input("Nome: ").strip()
        while not nome:
            print("Nome não pode estar vazio!")
            nome = input("Nome: ").strip()
 
        # Validação da data de nascimento
        while True:
            data_nasc = input("Data de nascimento (dd/mm/yyyy): ")
            try:
                datetime.strptime(data_nasc, "%d/%m/%Y")
                break
            except:
                print("Formato inválido! Ex: 25/09/2004")
 
        # Validação de idade
        while True:
            try:
                idade = int(input("Idade: "))
                if idade > 0:
                    break
                print("Idade deve ser maior que 0!")
            except:
                print("Digite um número válido!")
 
        # Validação de sexo
        sexo = input("Sexo (M ou F): ").upper()
        while sexo not in ["M", "F"]:
            print("Digite apenas M ou F!")
            sexo = input("Sexo (M ou F): ").upper()
 
        # Validação de CPF
        while True:
            cpf = input("CPF (11 números): ")
            if cpf.isdigit() and len(cpf) == 11:
                break
            print("CPF inválido!")
 
        # Validação de telefone
        telefone = input("Telefone: ").strip()
        while not telefone:
            print("Telefone não pode estar vazio!")
            telefone = input("Telefone: ").strip()
 
        # Validação de email
        email = input("Email: ").strip()
        while "@" not in email or "." not in email:
            print("Email inválido!")
            email = input("Email: ").strip()
 
        # Validação de CEP e obtenção do endereço via API
        while True:
            cep = input("Digite o CEP (8 números): ")
            if not (cep.isdigit() and len(cep) == 8):
                print("CEP inválido!")
                continue
 
            res = requests.get(f"https://viacep.com.br/ws/{cep}/json/").json()
            if "erro" in res:
                print("CEP não encontrado!")
            else:
                endereco = res["logradouro"]
                break
 
        # Inserção no banco de dados
        sql = """INSERT INTO pacientes
                 (nome, data_nascimento, idade, sexo, cpf, telefone, email, endereco)
                 VALUES (:1, TO_DATE(:2, 'DD/MM/YYYY'), :3, :4, :5, :6, :7, :8)"""
        cursor.execute(sql, (nome, data_nasc, idade, sexo, cpf, telefone, email, endereco))
        conn.commit()
 
        print("##### Paciente cadastrado com sucesso! #####")
 
    except Exception as e:
        print("Erro ao cadastrar paciente:", e)
 
 
def listar_pacientes(cursor) -> list | None:
    """
    Lista todos os pacientes cadastrados no banco.
 
    Retorna:
        list: Lista de tuplas com os dados dos pacientes.
        None: Se não houver pacientes.
    """
    try:
        cursor.execute("SELECT * FROM pacientes ORDER BY id_paciente")
        data = cursor.fetchall()
 
        if not data:
            print("Nenhum paciente cadastrado!")
            return None
 
        print("---- LISTA DE PACIENTES ----")
        for p in data:
            print(f"ID: {p[0]} | Nome: {p[1]} | CPF: {p[5]} | Telefone: {p[6]}")
        return data
    except Exception as e:
        print("Erro ao listar pacientes:", e)
 
 
def alterar_paciente(conn, cursor) -> None:
    """
    Permite alterar um campo específico de um paciente existente.
    """
    listar_pacientes(cursor)
    try:
        id_paciente = input("Digite o ID do paciente: ")
 
        campo = input("Campo para alterar (nome, idade, telefone, email, endereco): ").lower()
        while campo not in ["nome", "idade", "telefone", "email", "endereco"]:
            campo = input("Campo inválido! Digite de novo: ").lower()
 
        novo_valor = input("Novo valor: ").strip()
        while not novo_valor:
            novo_valor = input("Valor não pode ser vazio! Novo valor: ").strip()
 
        sql = f"UPDATE pacientes SET {campo} = :1 WHERE id_paciente = :2"
        cursor.execute(sql, (novo_valor, id_paciente))
        conn.commit()
 
        print("Paciente alterado com sucesso!")
 
    except Exception as e:
        print("Erro ao alterar paciente:", e)
 
 
def excluir_paciente(conn, cursor) -> None:
    """
    Remove um paciente do banco de dados.
    """
    listar_pacientes(cursor)
    try:
        id_paciente = input("Digite o ID do paciente para remover: ")
        cursor.execute("DELETE FROM pacientes WHERE id_paciente = :1", (id_paciente,))
        conn.commit()
        print("Paciente removido com sucesso!")
    except Exception as e:
        print("Erro ao remover paciente:", e)
 
# ---------------------- Funções Consulta ----------------------
def marcar_consulta(conn, cursor) -> None:
    """
    Marca uma nova consulta para um paciente existente.
    """
    try:
        print("----- MARCAR CONSULTA -----")
        id_paciente = input("ID do paciente: ")
 
        # Validação do dia
        while True:
            try:
                dia = int(input("Dia (1-31): "))
                if 1 <= dia <= 31:
                    break
                print("Dia inválido!")
            except:
                print("Digite um número válido!")
 
        # Validação do mês
        while True:
            try:
                mes = int(input("Mês (1-12): "))
                if 1 <= mes <= 12:
                    break
                print("Mês inválido!")
            except:
                print("Digite um número válido!")
 
        # Validação da hora
        while True:
            hora = input("Hora (HH:MM): ")
            try:
                datetime.strptime(hora, "%H:%M")
                break
            except:
                print("Formato inválido!")
 
        # Combina data e hora e insere no banco
        data_str = f"{dia}/{mes}/2025 {hora}"
        data_consulta = datetime.strptime(data_str, "%d/%m/%Y %H:%M")
 
        sql = """INSERT INTO consultas (id_paciente, data_consulta)
                 VALUES (:1, :2)"""
        cursor.execute(sql, (id_paciente, data_consulta))
        conn.commit()
        print("Consulta marcada com sucesso!")
 
    except Exception as e:
        print("Erro ao marcar consulta:", e)
 
 
def listar_consultas(cursor) -> list | None:
    """
    Lista todas as consultas agendadas.
 
    Retorna:
        list: Lista de tuplas com dados das consultas.
        None: Se não houver consultas.
    """
    try:
        cursor.execute("""
            SELECT c.id_consulta, p.nome, TO_CHAR(c.data_consulta, 'DD/MM/YYYY HH24:MI')
            FROM consultas c
            JOIN pacientes p ON c.id_paciente = p.id_paciente
            ORDER BY c.data_consulta
        """)
        data = cursor.fetchall()
 
        if not data:
            print("Nenhuma consulta marcada!")
            return None
 
        print("---- CONSULTAS ----")
        for c in data:
            print(f"ID: {c[0]} | Paciente: {c[1]} | Data/Hora: {c[2]}")
        return data
    except Exception as e:
        print("Erro ao listar consultas:", e)
 
 
def alterar_consulta(conn, cursor) -> None:
    """
    Permite alterar a data ou hora de uma consulta existente.
    """
    listar_consultas(cursor)
    try:
        id_consulta = input("ID da consulta: ")
        escolha = input("Alterar data ou hora? ").lower()
        while escolha not in ["data", "hora"]:
            escolha = input("Inválido! Digite 'data' ou 'hora': ").lower()
 
        if escolha == "data":
            while True:
                nova_data = input("Nova data (dd/mm/yyyy): ")
                try:
                    datetime.strptime(nova_data, "%d/%m/%Y")
                    break
                except:
                    print("Data inválida!")
 
            hora_atual = input("Hora atual da consulta (HH:MM): ")
            nova_datahora = f"{nova_data} {hora_atual}"
            data_nova = datetime.strptime(nova_datahora, "%d/%m/%Y %H:%M")
 
            cursor.execute(
                "UPDATE consultas SET data_consulta = :1 WHERE id_consulta = :2",
                (data_nova, id_consulta)
            )
 
        else:
            while True:
                nova_hora = input("Nova hora (HH:MM): ")
                try:
                    datetime.strptime(nova_hora, "%H:%M")
                    break
                except:
                    print("Hora inválida!")
 
            data_atual = input("Data atual (dd/mm/yyyy): ")
            nova_datahora = f"{data_atual} {nova_hora}"
            hora_nova = datetime.strptime(nova_datahora, "%d/%m/%Y %H:%M")
 
            cursor.execute(
                "UPDATE consultas SET data_consulta = :1 WHERE id_consulta = :2",
                (hora_nova, id_consulta)
            )
 
        conn.commit()
        print("Consulta alterada com sucesso!")
 
    except Exception as e:
        print("Erro ao alterar consulta:", e)
 
 
def cancelar_consulta(conn, cursor) -> None:
    """
    Cancela uma consulta existente.
    """
    listar_consultas(cursor)
    try:
        id_consulta = input("ID da consulta para cancelar: ")
        cursor.execute("DELETE FROM consultas WHERE id_consulta = :1", (id_consulta,))
        conn.commit()
        print("Consulta cancelada!")
    except Exception as e:
        print("Erro:", e)
 
# ---------------------- Exames ----------------------
def ver_exame(cursor) -> None:
    """
    Exibe os exames de um paciente específico.
    """
    try:
        nome_paciente = input("Digite o nome do paciente: ").strip()
        while not nome_paciente:
            nome_paciente = input("Nome não pode estar vazio: ").strip()
 
        sql = """SELECT e.tipo, e.data_exame, e.resultado
                 FROM exames e
                 JOIN pacientes p ON e.id_paciente = p.id_paciente
                 WHERE LOWER(p.nome) = LOWER(:1)"""
        cursor.execute(sql, (nome_paciente,))
        data = cursor.fetchall()
 
        if not data:
            print("Nenhum exame encontrado!")
        else:
            for e in data:
                print(f"Exame: {e[0]} | Data: {e[1]} | Resultado: {e[2]}")
 
    except Exception as e:
        print("Erro ao exibir exames:", e)
 
# ---------------------- Mensagens ----------------------
def enviar_mensagem(conn, cursor) -> None:
    """
    Envia uma mensagem de um paciente ao médico.
    """
    try:
        print("----- MENSAGEM AO MÉDICO -----")
        id_paciente = input("ID do paciente: ")
 
        mensagem = input("Digite sua mensagem: ").strip()
        while not mensagem:
            mensagem = input("Mensagem não pode ser vazia! Digite: ").strip()
 
        data_envio = datetime.now()
 
        sql = """INSERT INTO mensagens (id_paciente, mensagem, data_envio)
                 VALUES (:1, :2, :3)"""
        cursor.execute(sql, (id_paciente, mensagem, data_envio))
        conn.commit()
        print("Mensagem enviada!")
 
    except Exception as e:
        print("Erro ao enviar mensagem:", e)
 
# ---------------------- Programa Principal ----------------------
def menu() -> None:
    """
    Menu principal do sistema, permite acessar consultas, exames,
    mensagens e cadastro de pacientes.
    """
    os.system("cls")
    conn = conectar_bd()
    if not conn:
        exit()
 
    cursor = conn.cursor()
 
    while True:
        os.system("cls")
        print("""
============================
 AIDA - Assistente de Saúde
============================
1 - Área de Consultas
2 - Ver Resultados de Exames
3 - Enviar Mensagem ao Médico
4 - Cadastro do Paciente
0 - Sair
""")
        escolha = input("Escolha: ")
 
        # Menu de Consultas
        if escolha == "1":
            os.system("cls")
            print("""
1 - Marcar Consulta
2 - Listar Consultas
3 - Alterar Consulta
4 - Cancelar Consulta
0 - Voltar
""")
            op = input("Escolha: ")
            match op:
                case "1": marcar_consulta(conn, cursor)
                case "2":
                    lista_consulta = listar_consultas(cursor)
                    if lista_consulta:
                        criar_json_consulta(lista_consulta)
                case "3": alterar_consulta(conn, cursor)
                case "4": cancelar_consulta(conn, cursor)
                case "0": continue
                case _: print("Opção inválida!")
 
        # Menu Exames
        elif escolha == "2":
            os.system("cls")
            ver_exame(cursor)
 
        # Menu Mensagens
        elif escolha == "3":
            os.system("cls")
            enviar_mensagem(conn, cursor)
 
        # Menu Pacientes
        elif escolha == "4":
            os.system("cls")
            print("""
1 - Cadastrar Paciente
2 - Listar Pacientes
3 - Alterar Cadastro
4 - Remover Paciente
0 - Voltar
""")
            op = input("Escolha: ")
            match op:
                case "1": cadastrar_paciente(conn, cursor)
                case "2":
                    lista_paciente = listar_pacientes(cursor)
                    if lista_paciente:
                        criar_json_paciente(lista_paciente)
                case "3": alterar_paciente(conn, cursor)
                case "4": excluir_paciente(conn, cursor)
                case "0": continue
                case _: print("Opção inválida!")
 
        # Sair
        elif escolha == "0":
            print("\nObrigado por usar a assistente AIDA!")
            break
 
        else:
            print("Opção inválida!")
 
        input("\nPressione ENTER para continuar...")
 
# Início do programa
menu()