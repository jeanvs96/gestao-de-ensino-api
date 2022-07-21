package br.com.dbccompany.time7.gestaodeensino.service;

import br.com.dbccompany.time7.gestaodeensino.dto.AlunoDTO;
import br.com.dbccompany.time7.gestaodeensino.dto.ProfessorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender emailSender;

    public void sendEmailCriarAluno(AlunoDTO alunoDTO){
        log.info("Enviando e-mail para " + alunoDTO.getNome());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(alunoDTO.getEmail());
        message.setSubject("Bem vindo(a)!");
        message.setText("Olá " +alunoDTO.getNome()+ ", \n" +
                "Estamos felizes em ter você em nosso sistema :) \n"+
                "Seu cadastro foi realizado com sucesso, seu identificador é " +alunoDTO.getIdAluno()+"\n" +
                "Qualquer dúvida é só contatar o suporte pelo e-mail " + from
                + "\nAtt, Sistema de Gestão de Ensino");
        emailSender.send(message);
        log.info("E-mail enviado");
    }

    public void sendEmailCriarProfessor(ProfessorDTO professorDTO){
        log.info("Enviando e-mail para " + professorDTO.getNome());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(professorDTO.getEmail());
        message.setSubject("Bem vindo(a)!");
        message.setText("Olá " +professorDTO.getNome()+ ", \n" +
                "Estamos felizes em ter você em nosso sistema :) \n"+
                "Seu cadastro foi realizado com sucesso, seu identificador é " +professorDTO.getIdProfessor()+"\n" +
                "Qualquer dúvida é só contatar o suporte pelo e-mail " + from
                + "\nAtt, Sistema de Gestão de Ensino");
        emailSender.send(message);
        log.info("E-mail enviado");
    }
}
