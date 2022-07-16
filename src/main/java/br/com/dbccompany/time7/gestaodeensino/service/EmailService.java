package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {
    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    public void sendEmailCriarAluno(AlunoDTO alunoDTO){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(alunoDTO.getEmail());
        message.setSubject("Bem vindo(a)!");
        message.setText("Olá " +alunoDTO.getNome()+ ", \n" +
                "Estamos felizes em ter você em nosso sistema :) \n"+
                "Seu cadastro foi realizado com sucesso, seu identificador é " +alunoDTO.getIdAluno()+"\n" +
                "Qualquer dúvida é só contatar o suporte pelo e-mail suporte@sistema.com.br \n"+
                "Att, Sistema de Gestão de Ensino");
        emailSender.send(message);
    }
}
