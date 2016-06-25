package br.com.rp.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.rp.domain.Cliente;
import br.com.rp.domain.Conta;
import br.com.rp.domain.Email;
import br.com.rp.domain.MotivoRejeicao;
import br.com.rp.domain.Proposta;
import br.com.rp.domain.SituacaoEmail;
import br.com.rp.domain.SituacaoProposta;
import br.com.rp.domain.UsuarioCliente;
import br.com.rp.domain.UsuarioFuncionario;
import br.com.rp.repository.PropostaRepository;

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
public class PropostaService {

	@PersistenceContext(unitName = "vbankpu")
	private EntityManager em;

	@EJB
	private PropostaRepository propostaRepository;

	@EJB
	private EmailService emailService;

	@EJB
	private MotivoRejeicaoService motivoRejeicaoService;

	@EJB
	private UsuarioFuncionarioService usuarioFuncionarioService;

	@EJB
	private ContaService contaService;

	@EJB
	private UsuarioClienteService usuarioClienteService;

	@EJB
	private ClienteService clienteService;

	public List<Proposta> getAll() {
		return propostaRepository.getAll();
	}

	public Proposta save(Proposta proposta) {
		return propostaRepository.save(proposta);
	}

	public Proposta findById(Long id) {
		return propostaRepository.findById(id);
	}

	public void remove(Long id) {
		propostaRepository.remove(id);
	}

	public List<Proposta> findByDataUltimaProposta(String cpf) {
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DATE, -30);
		return em
				.createQuery(
						"from " + Proposta.class.getSimpleName() + " where cpf = '" + cpf + "' and dataProposta > '"
								+ new SimpleDateFormat("yyyy-MM-dd").format(calendario.getTime()) + "'",
						Proposta.class)
				.getResultList();
	}

	public Boolean isPropostaUltimosTrintaDias(String cpf) {
		if (findByDataUltimaProposta(cpf).size() > 0) {
			return true;
		}
		return false;
	}

	public Boolean isCpfValido(String cpf) {
		try {
			new CPFValidator().assertValid(cpf);
		} catch (InvalidStateException e) {
			return false;
		}
		return true;
	}

	public Proposta aprovarProposta(Long idProposta, Long idUsuarioAnalisou) {
		Proposta proposta = propostaRepository.findById(idProposta);
		if (proposta != null) {
			UsuarioFuncionario usuarioFuncionarioAnalise = usuarioFuncionarioService.findById(idUsuarioAnalisou);
			if (usuarioFuncionarioAnalise != null) {
				proposta.setUsuarioAnalise(usuarioFuncionarioAnalise);
				proposta.setSituacaoProposta(SituacaoProposta.APROVADA);
				save(proposta);

				Cliente cliente = clienteService.save(new Cliente(proposta.getNome(), proposta.getCpf(),
						proposta.getEmail(), proposta.getRenda(), contaService
								.save(new Conta(new Integer(new Random().nextInt(9999999)).toString(), true, false))));

				usuarioClienteService.save(new UsuarioCliente(cliente.getNome(), cliente.getConta().getNrConta(),
						new Integer(new Random().nextInt(9999999)).toString(), cliente));

				Email email = new Email();
				email.setAssunto("Proposta de abertura de conta Aprovada");
				email.setDescricao("Prezada(a) Sr(a) " + proposta.getNome() + ".\n\n"
						+ "Informamos que o sua proposta de abertura de conta foi aprovada.\n\n"
						+ "O número da sua conta e login é: " + cliente.getConta().getNrConta()
						+ "\n\nPor favor altere sua senha acessando o seguinte endereço: \n\n" + "http://vbank.com.br/"
						+ cliente.getNome() + "/alterarSenha \n\n" + "Atenciosamente,\nEquipe Vbank");
				email.setDestinatario(cliente.getEmail());
				email.setRemetente(usuarioFuncionarioAnalise.getFuncionario().getEmail());
				email.setSituacao(SituacaoEmail.ENVIADO);
				email.setDhEnvio(Calendar.getInstance().getTime());
				email.setProposta(proposta);
				email.setCliente(cliente);
				emailService.save(email);
				emailService.enviarEmail(email);

				return proposta;
			} else
				throw new RuntimeException("O Usuário de analise não existe.");
		} else
			throw new RuntimeException("A Proposta não existe.");
	}

	public Proposta rejeitarProposta(Long idProposta, String mensagemRejeicao, Long idUsuarioAnalise) {
		Proposta proposta = findById(idProposta);
		if (proposta != null) {
			UsuarioFuncionario usuarioFuncionario = usuarioFuncionarioService.findById(idUsuarioAnalise);
			if (usuarioFuncionario != null) {
				MotivoRejeicao motivoRejeicao = new MotivoRejeicao();
				motivoRejeicao.setDsMotivo(mensagemRejeicao);
				motivoRejeicaoService.save(motivoRejeicao);

				proposta.setSituacaoProposta(SituacaoProposta.REJEITADA);
				proposta.setMotivoRejeicao(motivoRejeicao);
				proposta.setUsuarioAnalise(usuarioFuncionario);
				save(proposta);

				Email email = new Email();
				email.setAssunto("Proposta de abertura de conta rejeitada");
				email.setDescricao("Prezada(a) Sr(a) " + proposta.getNome() + ".\n\n"
						+ "Informamos que o sua proposta de abertura de conta foi rejeitada pelo seguinte motivo: "
						+ mensagemRejeicao + ".\n\n"
						+ "Aguarde 30 dias para realizar uma nova proposta.\n\nAtenciosamente, \nEquipe VBank");
				email.setDestinatario(proposta.getEmail());
				email.setRemetente(usuarioFuncionario.getFuncionario().getEmail());
				email.setSituacao(SituacaoEmail.ENVIADO);
				email.setDhEnvio(Calendar.getInstance().getTime());
				email.setProposta(proposta);
				emailService.save(email);
				emailService.enviarEmail(email);
				return proposta;

			} else
				throw new RuntimeException("O Usuário de analise não existe.");

		} else
			throw new RuntimeException("A Proposta não existe.");

	}

	public List<Proposta> findByRegiao(String regiao) {
		return em
				.createQuery("from " + Proposta.class.getSimpleName() + " where regiao like '%" + regiao
						+ "%' and situacaoProposta = '" + SituacaoProposta.ABERTA + "'", Proposta.class)
				.getResultList();
	}

}
