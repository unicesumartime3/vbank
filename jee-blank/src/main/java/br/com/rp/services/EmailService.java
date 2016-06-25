package br.com.rp.services;

import java.util.List;
import java.util.Properties;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.rp.domain.Email;
import br.com.rp.repository.EmailRepository;

@Stateless
public class EmailService {

	@PersistenceContext(unitName = "vbankpu")
	private EntityManager em;

	@EJB
	private EmailRepository emailRepository;

	public List<Email> getAll() {
		return emailRepository.getAll();
	}

	public Email save(Email email) {
		return emailRepository.save(email);
	}

	public Email findById(Long id) {
		return emailRepository.findById(id);
	}

	public void remove(Long id) {
		emailRepository.remove(id);
	}

	public Email findByProposta(Long idProposta) {
		return em.createQuery("from Email where proposta_id = " + idProposta, Email.class).getSingleResult();
	}

	@Asynchronous
	public void enviarEmail(Email email) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("javaeetime3envemail@gmail.com", "javaeetime3envioemail123");
			}
		});
		session.setDebug(true);
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("javaeetime3envemail@gmail.com"));

			Address[] toUser = InternetAddress 
					.parse(email.getDestinatario());

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(email.getAssunto());
			message.setText(email.getDescricao());

			Transport.send(message);

			System.out.println("Email enviado com sucesso!!");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
