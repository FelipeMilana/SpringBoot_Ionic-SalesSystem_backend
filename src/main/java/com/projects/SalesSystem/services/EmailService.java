package com.projects.SalesSystem.services;

import java.io.File;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.projects.SalesSystem.entities.User;
import com.projects.SalesSystem.security.UserSS;
import com.projects.SalesSystem.services.exceptions.AuthorizationException;
import com.projects.SalesSystem.services.exceptions.EmailException;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private UserService userService;
	
	private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);
	
	public void sendMonthlyReportToEmail(File path) {
		try {
			MimeMessage mm = prepareMimeMessage(path);
			sendEmail(mm);
		}
		catch(MessagingException e) {
			throw new EmailException(e.getMessage());
		}
	}
	
	private MimeMessage prepareMimeMessage(File path) throws MessagingException {
		UserSS authUser = userService.getAuthenticatedUser();

		if (authUser == null) {
			throw new AuthorizationException("Acesso Negado");
		}

		User user = userService.findUserByEmail(authUser.getUsername());
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(user.getEmail());
		mmh.setSubject("Relatório");
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText("Relatório");
		mmh.addAttachment("Relatorio.xlsx", path);
		return mimeMessage;
	}
	
	private void sendEmail(MimeMessage message) {
		LOG.info("Enviando email");
		javaMailSender.send(message);
		LOG.info("Email enviado");
	}
}
