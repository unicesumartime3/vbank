package br.com.rp.repository.impl;

import javax.ejb.Stateless;

import br.com.rp.domain.Email;
import br.com.rp.repository.EmailRepository;

/**
 * 
 * @author Christian Marchiori
 * @email cmxk@live.com
 *
 * @author Flávia Ferreira
 * @email flaviahferreirah@gmail.com
 *
 * @author Júlio Serra
 * @email julioserraaraujo@gmail.com 
 * 
 * @author Rafael Suzin
 * @email rafaelsuzin1@gmail.com
 *
 */

@Stateless
public class EmailRepositoryImpl extends AbstractRepositoryImpl<Email> implements EmailRepository {

	public EmailRepositoryImpl() {
		super(Email.class);
	}

}
