package org.acme.service;


import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class MensagemService {

    private String conteudo;

    public void enviarMensagem(String mensagem) {
        this.conteudo = mensagem;
        System.out.println("Confirme o conteúdo da mensagem: " + conteudo);
        // Aqui você poderia adicionar lógica de validação, logs ou persistência
    }

    public String getConteudo() {
        return conteudo;
    }

}
