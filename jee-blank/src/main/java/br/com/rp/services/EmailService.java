package br.com.rp.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.rp.domain.Email;
import br.com.rp.repository.EmailRepository;

@Stateless
public class EmailService {
	
	@EJB
	private EmailRepository emailRepository;
	
	public List<Email> getAll(){
		return emailRepository.getAll();
	}
	
	public Email save(Email email){
		return emailRepository.save(email);
	}
	
	public Email findById(Long id){
		return emailRepository.findById(id);
	}
	
	public void remove(Long id){
		emailRepository.remove(id);
	}

}
