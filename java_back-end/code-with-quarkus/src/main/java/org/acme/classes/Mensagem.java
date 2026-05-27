package org.acme.classes;

import org.acme.model.Paciente;

import java.util.Scanner;

public class Mensagem {
    private String conteudo;
    private String rementente;
    private String destinatario;
    private Paciente paciente;

    public Mensagem() {
    }

    public Mensagem(String conteudo, String rementente, String destinatario, Paciente paciente) {
        this.conteudo = conteudo;
        this.rementente = rementente;
        this.destinatario = destinatario;
        this.paciente = paciente;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getRementente() {
        return rementente;
    }

    public void setRementente(String rementente) {
        this.rementente = rementente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void enviarMensagem() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite oque vc deseja enviar para o médico");
        conteudo = sc.nextLine();
        System.out.println("Confirme o conteudo da mensagem:" + conteudo);
        while (true) {
            try {
                System.out.println("caso deseje reescrever a mensagem digite 1 e para parar qualquer outro número");
                int escolha = Integer.parseInt(sc.nextLine());
                if (escolha == 1) {
                    System.out.println("reescreva a mensagem");
                    conteudo = sc.nextLine();
                    System.out.println("Confirme o conteudo da mensagem:" + conteudo);
                    System.out.println("Mensagem enviada");
                } else {
                    System.out.println("Mensagem enviada");
                    break;

                }
            } catch (NumberFormatException e) {
                System.out.println("Erro! Digite apenas números");
            }
        }
    }
}
