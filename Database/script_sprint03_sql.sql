-- Gerado por Oracle SQL Developer Data Modeler 24.3.1.351.0831
--   em:        2025-09-17 08:51:19 BRT
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g



-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE TABLE T_HSCS_ACESSO_PACIENTE 
    ( 
     Id_acesso                   INTEGER  NOT NULL , 
     Id_login_paciente           INTEGER  NOT NULL , 
     Nm_senha_paciente           NUMBER (8) , 
     CPF                         VARCHAR2 (15)  NOT NULL , 
     Rg                          VARCHAR2 (15) , 
     Nm_completo                 VARCHAR2 (50) , 
     T_HSCS_PACIENTE_Id_paciente INTEGER  NOT NULL 
    ) 
;
CREATE UNIQUE INDEX T_HSCS_ACESSO_PACIENTE__IDX ON T_HSCS_ACESSO_PACIENTE 
    ( 
     T_HSCS_PACIENTE_Id_paciente ASC 
    ) 
;

ALTER TABLE T_HSCS_ACESSO_PACIENTE 
    ADD CONSTRAINT T_HSCS_ACESSO_PACIENTE_PK PRIMARY KEY ( Id_acesso ) ;

ALTER TABLE T_HSCS_ACESSO_PACIENTE 
    ADD CONSTRAINT T_HSCS_ACESSO_PACIENTE_CPF_UN UNIQUE ( CPF ) ;

CREATE TABLE T_HSCS_ASSSISTENTE 
    ( 
     Id_assistente               INTEGER  NOT NULL , 
     Consulta                    VARCHAR2 (30)  NOT NULL , 
     Msg_medico                  VARCHAR2 (100) , 
     T_HSCS_PACIENTE_Id_paciente INTEGER  NOT NULL 
    ) 
;

ALTER TABLE T_HSCS_ASSSISTENTE 
    ADD CONSTRAINT T_HSCS_ASSSISTENTE_PK PRIMARY KEY ( Id_assistente ) ;

CREATE TABLE T_HSCS_CONSULTA 
    ( 
     Id_paciente                 INTEGER  NOT NULL , 
     Dt_consulta                 Date  NOT NULL , 
     Id_consulta                 INTEGER  NOT NULL , 
     T_HSCS_PACIENTE_Id_paciente INTEGER  NOT NULL 
    ) 
;

ALTER TABLE T_HSCS_CONSULTA 
    ADD CONSTRAINT T_HSCS_CONSULTA_PK PRIMARY KEY ( Id_consulta ) ;

CREATE TABLE T_HSCS_EXAME 
    ( 
     Id_paciente                 INTEGER  NOT NULL , 
     Tp_exame                    VARCHAR2 (50) , 
     Id_exame                    INTEGER  NOT NULL , 
     T_HSCS_CONSULTA_Id_consulta INTEGER  NOT NULL , 
     T_HSCS_PACIENTE_Id_paciente INTEGER  NOT NULL 
    ) 
;

ALTER TABLE T_HSCS_EXAME 
    ADD CONSTRAINT T_HSCS_EXAME_PK PRIMARY KEY ( Id_exame, T_HSCS_PACIENTE_Id_paciente ) ;

CREATE TABLE T_HSCS_PACIENTE 
    ( 
     Id_paciente    INTEGER  NOT NULL , 
     Nm_completo    VARCHAR2 (100) , 
     Rg             VARCHAR2 (20)  NOT NULL , 
     Dt_nascimento  DATE , 
     Sexo           VARCHAR2 (20) , 
     Altura         NUMBER (3,2) , 
     Peso           NUMBER (2) , 
     Email_paciente VARCHAR2 (50) 
    ) 
;

ALTER TABLE T_HSCS_PACIENTE 
    ADD CONSTRAINT T_HSCS_PACIENTE_PK PRIMARY KEY ( Id_paciente ) ;



ALTER TABLE T_HSCS_PACIENTE 
    ADD CONSTRAINT UN_EMAIL_PACIENTE UNIQUE ( Email_paciente ) ;


ALTER TABLE T_HSCS_ACESSO_PACIENTE 
    ADD CONSTRAINT FK_ACESSO_PACIENTE_PACIENTE FOREIGN KEY 
    ( 
     T_HSCS_PACIENTE_Id_paciente
    ) 
    REFERENCES T_HSCS_PACIENTE 
    ( 
     Id_paciente
    ) 
;


ALTER TABLE T_HSCS_ASSSISTENTE 
    ADD CONSTRAINT FK_ASSSISTENTE_PACIENTE FOREIGN KEY 
    ( 
     T_HSCS_PACIENTE_Id_paciente
    ) 
    REFERENCES T_HSCS_PACIENTE 
    ( 
     Id_paciente
    ) 
;


ALTER TABLE T_HSCS_CONSULTA 
    ADD CONSTRAINT FK_CONSULTA_PACIENTE FOREIGN KEY 
    ( 
     T_HSCS_PACIENTE_Id_paciente
    ) 
    REFERENCES T_HSCS_PACIENTE 
    ( 
     Id_paciente
    ) 
;


ALTER TABLE T_HSCS_EXAME 
    ADD CONSTRAINT FK_EXAME_CONSULTA FOREIGN KEY 
    ( 
     T_HSCS_CONSULTA_Id_consulta
    ) 
    REFERENCES T_HSCS_CONSULTA 
    ( 
     Id_consulta
    ) 
;


ALTER TABLE T_HSCS_EXAME 
    ADD CONSTRAINT FK_EXAME_PACIENTE FOREIGN KEY 
    ( 
     T_HSCS_PACIENTE_Id_paciente
    ) 
    REFERENCES T_HSCS_PACIENTE 
    ( 
     Id_paciente
    ) 
;
ALTER TABLE T_HSCS_PACIENTE
    ADD CONSTRAINT CK_PESO
    CHECK   (Peso > 0);

ALTER TABLE T_HSCS_PACIENTE
    ADD CONSTRAINT CK_ALTURA
    CHECK   (Altura > 0);

ALTER TABLE T_HSCS_PACIENTE
    ADD CONSTRAINT CK_SEXO
    CHECK   (Sexo IN ('M','F'));

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (1, 'Ana Clara Santos', '1234567890', TO_DATE('15/05/1990','DD/MM/YYYY'), 'F', 1.65, 62, 'ana.santos@email.com');

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (2, 'Bruno Mendes Lima', '2345678901', TO_DATE('20/10/1985','DD/MM/YYYY'), 'M', 1.78, 85, 'bruno.lima@email.com');

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (3, 'Carla Oliveira Rocha', '3456789012', TO_DATE('03/01/2000','DD/MM/YYYY'), 'F', 1.70, 70, 'carla.rocha@email.com');

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (4, 'Daniel Pereira Costa', '4567890123', TO_DATE('25/11/1972','DD/MM/YYYY'), 'M', 1.83, 92, 'daniel.costa@email.com');

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (5, 'Elisa Gomes Alves', '5678901234', TO_DATE('08/07/2005','DD/MM/YYYY'), 'F', 1.60, 55, 'elisa.alves@email.com');

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (6, 'Felipe Souza Junior', '6789012345', TO_DATE('12/03/1998','DD/MM/YYYY'), 'M', 1.75, 78, 'felipe.souza@email.com');

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (7, 'Gabriela Neves Dias', '7890123456', TO_DATE('28/09/1965','DD/MM/YYYY'), 'F', 1.58, 68, 'gabriela.dias@email.com');

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (8, 'Henrique Silva Melo', '8901234567', TO_DATE('04/02/1980','DD/MM/YYYY'), 'M', 1.90, 95, 'henrique.melo@email.com');

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (9, 'Isabela Ribeiro Neta', '9012345678', TO_DATE('17/06/1993','DD/MM/YYYY'), 'F', 1.68, 65, 'isabela.neta@email.com');

INSERT INTO T_HSCS_PACIENTE (Id_paciente, Nm_completo, Rg, Dt_nascimento, Sexo, Altura, Peso, Email_paciente) 
VALUES (10, 'João Victor Ferreira', '0123456789', TO_DATE('30/12/2002','DD/MM/YYYY'), 'M', 1.80, 80, 'joao.ferreira@email.com');

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (1, TO_DATE('10/09/2025','DD/MM/YYYY'), 1001, 1);

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (2, TO_DATE('05/10/2025','DD/MM/YYYY'), 1002, 2);

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (3, TO_DATE('18/09/2025','DD/MM/YYYY'), 1003, 3);

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (4, TO_DATE('01/10/2025','DD/MM/YYYY'), 1004, 4);

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (5, TO_DATE('25/09/2025','DD/MM/YYYY'), 1005, 5);

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (6, TO_DATE('12/10/2025','DD/MM/YYYY'), 1006, 6);

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (7, TO_DATE('03/10/2025','DD/MM/YYYY'), 1007, 7);

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (8, TO_DATE('20/09/2025','DD/MM/YYYY'), 1008, 8);

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (9, TO_DATE('08/10/2025','DD/MM/YYYY'), 1009, 9);

INSERT INTO T_HSCS_CONSULTA (Id_paciente, Dt_consulta, Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (10, TO_DATE('15/09/2025','DD/MM/YYYY'), 1010, 10);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (1, 'Hemograma Completo', 1, 1001, 1);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (1, 'Urina', 2, 1001, 1);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (2, 'Raio-X Tórax', 3, 1002, 2);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (2, 'Eletrocardiograma', 4, 1002, 2);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (3, 'Colesterol Total', 5, 1003, 3);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (3, 'Glicemia', 6, 1003, 3);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (4, 'Ultrassom Abdominal', 7, 1004, 4);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (4, 'Provas de Função Hepática', 8, 1004, 4);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (5, 'Cultura de Secreção', 9, 1005, 5);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (5, 'Teste de Gravidez', 10, 1005, 5);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (6, 'Ressonância Magnética', 11, 1006, 6);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (6, 'Teste Ergométrico', 12, 1006, 6);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (7, 'Densitometria Óssea', 13, 1007, 7);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (7, 'Endoscopia', 14, 1007, 7);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (8, 'Tomografia Computadorizada', 15, 1008, 8);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (8, 'Teste de Alergia', 16, 1008, 8);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (9, 'Mamografia', 17, 1009, 9);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (9, 'Papanicolau', 18, 1009, 9);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (10, 'Teste de COVID-19', 19, 1010, 10);

INSERT INTO T_HSCS_EXAME (Id_paciente, Tp_exame, Id_exame, T_HSCS_CONSULTA_Id_consulta, T_HSCS_PACIENTE_Id_paciente) 
VALUES (10, 'Audiometria', 20, 1010, 10);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (1, 101, 12345678, '11122233344', '1234567890', 'Ana Clara Santos', 1);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (2, 102, 87654321, '22233344455', '2345678901', 'Bruno Mendes Lima', 2);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (3, 103, 98761234, '33344455566', '3456789012', 'Carla Oliveira Rocha', 3);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (4, 104, 45678901, '44455566677', '4567890123', 'Daniel Pereira Costa', 4);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (5, 105, 54321987, '55566677788', '5678901234', 'Elisa Gomes Alves', 5);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (6, 106, 21093456, '66677788899', '6789012345', 'Felipe Souza Junior', 6);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (7, 107, 76541230, '77788899900', '7890123456', 'Gabriela Neves Dias', 7);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (8, 108, 34567890, '88899900011', '8901234567', 'Henrique Silva Melo', 8);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (9, 109, 10987654, '99900011122', '9012345678', 'Isabela Ribeiro Neta', 9);

INSERT INTO T_HSCS_ACESSO_PACIENTE (Id_acesso, Id_login_paciente, Nm_senha_paciente, CPF, Rg, Nm_completo, T_HSCS_PACIENTE_Id_paciente) 
VALUES (10, 110, 56789012, '00011122233', '0123456789', 'João Victor Ferreira', 10);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (1, 'Pedir receita médica', 'Receita enviada para o seu email.', 1);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (2, 'Horário da próxima consulta', 'Agendada para 05/11/2025 às 14:30.', 2);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (3, 'Pedir receita médica', 'Receita enviada para o seu email.', 3);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (4, 'Horário da próxima consulta', 'Agendada para 05/11/2025 às 14:30.', 4);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (5, 'Dúvidas sobre o medicamento', 'Uso contínuo, uma vez ao dia.', 5);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (6, 'Solicitar atestado', 'Atestado emitido por 3 dias.', 6);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (7, 'Informar sintoma', 'Dores de cabeça persistentes. Agendamento urgente.', 7);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (8, 'Confirmar cirurgia', 'Cirurgia confirmada para 20/12/2025.', 8);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (9, 'Informação de dieta', 'Seguir dieta de baixo sódio.', 9);

INSERT INTO T_HSCS_ASSSISTENTE (Id_assistente, Consulta, Msg_medico, T_HSCS_PACIENTE_Id_paciente) 
VALUES (10, 'Reagendar fisioterapia', 'Novo horário confirmado para quarta-feira.', 10);

-- Relatórios (selects)

SELECT
    Id_paciente,
    Nm_completo,
    Rg,
    Dt_nascimento
FROM
    T_HSCS_PACIENTE
ORDER BY
    Dt_nascimento ASC;

SELECT
    Id_consulta,
    Dt_consulta,
    T_HSCS_PACIENTE_Id_paciente AS Id_Paciente
FROM
    T_HSCS_CONSULTA
ORDER BY
    Dt_consulta DESC;

SELECT
    Nm_completo,
    ROUND(Altura, 2) AS Altura_M,
    ROUND(Peso, 0) AS Peso_KG,
    ROUND(Peso / (Altura * Altura), 2) AS IMC
FROM
    T_HSCS_PACIENTE;

SELECT
    Id_acesso,
    Nm_completo,
    Nm_senha_paciente AS Senha_Hash,
    TRUNC(Id_acesso / 2) AS Metade_Id_Acesso
FROM
    T_HSCS_ACESSO_PACIENTE;

SELECT
    T_HSCS_PACIENTE_Id_paciente AS Id_Paciente,
    COUNT(Id_consulta) AS Total_Consultas
FROM
    T_HSCS_CONSULTA
GROUP BY
    T_HSCS_PACIENTE_Id_paciente
HAVING
    COUNT(Id_consulta) > 0
ORDER BY
    Total_Consultas DESC;

SELECT
    Sexo,
    COUNT(*) AS Total_Pacientes,
    ROUND(AVG(Altura), 2) AS Media_Altura_M,
    ROUND(AVG(Peso), 1) AS Media_Peso_KG
FROM
    T_HSCS_PACIENTE
GROUP BY
    Sexo;

SELECT
    Id_paciente,
    Nm_completo
FROM
    T_HSCS_PACIENTE
WHERE
    Id_paciente IN (
        SELECT
            T_HSCS_PACIENTE_Id_paciente
        FROM
            T_HSCS_EXAME
        WHERE
            Tp_exame = 'Hemograma Completo'
    );

SELECT
    Id_consulta,
    T_HSCS_PACIENTE_Id_paciente AS Id_Paciente,
    Dt_consulta
FROM
    T_HSCS_CONSULTA
WHERE
    Dt_consulta > (
        SELECT
            TO_DATE('05/10/2025', 'DD/MM/YYYY')
        FROM
            dual -- Tabela virtual usada no Oracle para consultas de valor único
    )
ORDER BY
    Dt_consulta ASC;

SELECT
    P.Nm_completo AS Nome_Paciente,
    C.Id_consulta,
    C.Dt_consulta
FROM
    T_HSCS_PACIENTE P
INNER JOIN
    T_HSCS_CONSULTA C ON P.Id_paciente = C.T_HSCS_PACIENTE_Id_paciente
ORDER BY
    P.Nm_completo;

SELECT
    P.Nm_completo AS Nome_Paciente,
    E.Tp_exame AS Tipo_Exame,
    E.Id_exame
FROM
    T_HSCS_PACIENTE P
INNER JOIN
    T_HSCS_EXAME E ON P.Id_paciente = E.T_HSCS_PACIENTE_Id_paciente
ORDER BY
    P.Nm_completo, E.Id_exame;


-- Relatório do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             5
-- CREATE INDEX                             1
-- ALTER TABLE                             12
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
