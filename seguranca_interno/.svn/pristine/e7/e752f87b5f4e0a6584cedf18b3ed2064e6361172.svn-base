package br.gov.ma.tce.seguranca.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.Transactional;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import br.gov.ma.tce.seguranca.interno.server.beans.dependente.Dependente;
import br.gov.ma.tce.seguranca.interno.server.beans.dependente.DependenteFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.beans.servidor.Servidor;
import br.gov.ma.tce.seguranca.interno.server.beans.servidor.ServidorFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.beans.usuario.Usuario;
import br.gov.ma.tce.seguranca.interno.server.beans.usuario.UsuarioFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupo;
import br.gov.ma.tce.seguranca.server.beans.dependenteparentesco.DependenteParentescoFacadeBean;
import br.gov.ma.tce.seguranca.server.beans.funcionario.Funcionario;
import br.gov.ma.tce.seguranca.server.beans.funcionario.FuncionarioFacadeBean;

public class IndexVM {
	Integer maxIdServidor, maxIdFuncionario, maxIdDependente, maxIdDependenteSeg;
	private Usuario usuario;
	private UsuarioGrupo usuarioGrupo;

	private Collection<Servidor> servidores, servidoresComAlteracao;
	private Collection<Dependente> dependentes, dependentesComAlteracao;

	private ServidorFacadeBean servidorFacadeBean;
	private UsuarioFacadeBean usuarioFacadeBean;
	private DependenteFacadeBean dependenteFacadeBean;
	private FuncionarioFacadeBean funcionarioFacadeBean;
	private br.gov.ma.tce.seguranca.server.beans.dependente.DependenteFacadeBean dependenteSegFacadeBean;
	private DependenteParentescoFacadeBean dependenteParentescoFacadeBean;	

	@Wire("#modalVerificarNovosUsuarios")
	private Window modalVerificarNovosUsuarios;

	@Wire("#modalVerificarNovosDependentes")
	private Window modalVerificarNovosDependentes;

	@Wire("#modalAtualizarUsuarios")
	private Window modalAtualizarUsuarios;

	@Wire("#modalAtualizarDependentes")
	private Window modalAtualizarDependentes;

	public IndexVM() {
		try {
			InitialContext ctx = new InitialContext();
			servidorFacadeBean = (ServidorFacadeBean) ctx.lookup(ServidorFacadeBean.URI);
			usuarioFacadeBean = (UsuarioFacadeBean) ctx.lookup(UsuarioFacadeBean.URI);
			dependenteFacadeBean = (DependenteFacadeBean) ctx.lookup(DependenteFacadeBean.URI);
			funcionarioFacadeBean = (FuncionarioFacadeBean) ctx.lookup(FuncionarioFacadeBean.JNDI_SEGURANCA);
			dependenteSegFacadeBean = (br.gov.ma.tce.seguranca.server.beans.dependente.DependenteFacadeBean) ctx
					.lookup(br.gov.ma.tce.seguranca.server.beans.dependente.DependenteFacadeBean.JNDI_SEGURANCA);
			dependenteParentescoFacadeBean = (DependenteParentescoFacadeBean) ctx
					.lookup(DependenteParentescoFacadeBean.JNDI_SEGURANCA);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Init
	public void init() {
		usuario = (Usuario) Sessions.getCurrent().getAttribute("usuario");
		usuarioGrupo = (UsuarioGrupo) Sessions.getCurrent().getAttribute("usuarioGrupo");
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange(".")
	public void abrirModalVerificaNovosUsuarios(@BindingParam("visible") Boolean visible) {
		try {
			if (visible) {
				servidores = new ArrayList<Servidor>();
				maxIdServidor = servidorFacadeBean.findMaxId();
				maxIdFuncionario = funcionarioFacadeBean.findMaxId();

				// Se for diferente, existem funcionários novos
				if (maxIdServidor != maxIdFuncionario) {
					// Percorre cada um dos novos IDs e insere na lista de funcionários
					for (int i = maxIdServidor + 1; i <= maxIdFuncionario; i++) {
						Funcionario f = funcionarioFacadeBean.findById(i);
						if (f != null) {
							servidores.add(copiaFuncionario(f));
						}
					}
				}
			}

			modalVerificarNovosUsuarios.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@Command
	@NotifyChange(".")
	public void criarNovosUsuarios() {
		try {
			for (Servidor s : servidores) {
				servidorFacadeBean.insert(s);

				Usuario novoUsuario = new Usuario(usuarioFacadeBean.findMaxId() + 1, s, s.getMatricula(), null);
				usuarioFacadeBean.insert(novoUsuario);

				// Dependentes do funcionário
				List<br.gov.ma.tce.seguranca.server.beans.dependente.Dependente> dependentes = dependenteSegFacadeBean
						.findDependenteByFuncionarioId(s.getServidorId());
				for (br.gov.ma.tce.seguranca.server.beans.dependente.Dependente d : dependentes) {
					Dependente novoDependente = copiaDependente(d, s);
					dependenteFacadeBean.insert(novoDependente);
				}
			}

			modalVerificarNovosUsuarios.setVisible(false);
			servidores = new ArrayList<Servidor>();
			Clients.showNotification("Usuários copiados com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null, null, 3000,
					true);
		} catch (Exception e) {
			e.printStackTrace();
			Clients.showNotification("Ocorreu um erro ao copiar novos usuários. Tente novamente mais tarde.",
					Clients.NOTIFICATION_TYPE_WARNING, null, null, 3000, true);
		}
	}

	@Command
	@NotifyChange(".")
	public void abrirModalVerificarNovosDependentes(@BindingParam("visible") Boolean visible) {
		try {
			dependentes = new ArrayList<Dependente>();
			maxIdDependente = dependenteFacadeBean.findMaxId();
			maxIdDependenteSeg = dependenteSegFacadeBean.findMaxId();

			// Se for diferente, existem dependentes novos
			if (maxIdDependente != maxIdDependenteSeg) {
				for (int i = maxIdDependente + 1; i <= maxIdDependenteSeg; i++) {
					br.gov.ma.tce.seguranca.server.beans.dependente.Dependente d = dependenteSegFacadeBean
							.findByPrimaryKey(i);
					if (d != null) {
						dependentes.add(copiaDependente(d));
					}
				}
			}

			modalVerificarNovosDependentes.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@Command
	@NotifyChange(".")
	public void criarNovosDependentes() {
		try {
			for (Dependente d : dependentes) {
				dependenteFacadeBean.insert(d);
			}

			modalVerificarNovosDependentes.setVisible(false);
			dependentes = new ArrayList<Dependente>();
			Clients.showNotification("Dependentes copiados com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null, null,
					3000, true);
		} catch (Exception e) {
			Clients.showNotification("Ocorreu um erro ao copiar novos dependentes. Tente novamente mais tarde.",
					Clients.NOTIFICATION_TYPE_WARNING, null, null, 3000, true);
		}
	}

	@Command
	@NotifyChange(".")
	public void abrirModalAtualizarUsuarios(@BindingParam("visible") Boolean visible) {
		try {
			if (visible) {
				servidoresComAlteracao = new ArrayList<Servidor>();
				servidores = servidorFacadeBean.findAll();
				for (Servidor s : servidores) {
					// Servidor Elisiane Maria que dá problema sempre
					if (s.getMatricula() != 5736) {
						Servidor s2 = copiaFuncionario(funcionarioFacadeBean.findById(s.getServidorId()));
						if (!s.equals(s2)) {
							servidoresComAlteracao.add(s2);
						}
					}
				}
			} else {
				servidores = new ArrayList<Servidor>();
			}

			modalAtualizarUsuarios.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange(".")
	public void atualizarUsuarios() {
		try {
			for (Servidor s : servidoresComAlteracao) {
				servidorFacadeBean.update(s);
			}

			modalAtualizarUsuarios.setVisible(false);
			servidoresComAlteracao = new ArrayList<Servidor>();
			Clients.showNotification("Usuários atualizados com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null, null,
					3000, true);
		} catch (Exception e) {
			e.printStackTrace();
			Clients.showNotification("Ocorreu um erro ao atualizar os usuários. Tente novamente mais tarde.",
					Clients.NOTIFICATION_TYPE_WARNING, null, null, 3000, true);
		}
	}

	@Command
	@NotifyChange(".")
	public void abrirModalAtualizarDependentes(@BindingParam("visible") Boolean visible) {
		try {
			if (visible) {
				dependentesComAlteracao = new ArrayList<Dependente>();
				dependentes = dependenteFacadeBean.findAll();
				for (Dependente d : dependentes) {
					Dependente d2 = copiaDependente(dependenteSegFacadeBean.findByPrimaryKey(d.getDependenteId()));
					if (!d.equals(d2)) {
						dependentesComAlteracao.add(d2);
					}
				}
			} else {
				dependentes = new ArrayList<Dependente>();
			}

			modalAtualizarDependentes.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange(".")
	public void atualizarDependentes() {
		try {
			for (Dependente d : dependentesComAlteracao) {
				dependenteFacadeBean.update(d);
			}

			modalAtualizarDependentes.setVisible(false);
			dependentesComAlteracao = new ArrayList<Dependente>();
			Clients.showNotification("Dependentes atualizados com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null, null,
					3000, true);
		} catch (Exception e) {
			e.printStackTrace();
			Clients.showNotification("Ocorreu um erro ao atualizar os dependentes. Tente novamente mais tarde.",
					Clients.NOTIFICATION_TYPE_WARNING, null, null, 3000, true);
		}
	}

	public Servidor copiaFuncionario(Funcionario funcionario) {
		Servidor novoServidor = new Servidor();
		if (funcionario.getCodigo() != null) {
			novoServidor.setServidorId(funcionario.getCodigo());
		}

		if (funcionario.getFuncionarioFuncional().getLotacaoContracheque() != null) {
			novoServidor.setLotacaoContracheque(
					funcionario.getFuncionarioFuncional().getLotacaoContracheque().getDescricao());
		}

		if (funcionario.getFuncionarioFuncional().getLotacaoOrigem() != null) {
			novoServidor.setLotacaoOrigem(funcionario.getFuncionarioFuncional().getLotacaoOrigem().getDescricao());
		}

		if (funcionario.getFuncionarioFuncional().getLotacaoExercicio() != null) {
			novoServidor
					.setLotacaoExercicio(funcionario.getFuncionarioFuncional().getLotacaoExercicio().getDescricao());
			novoServidor.setSetorSigla(funcionario.getFuncionarioFuncional().getLotacaoExercicio().getSigla());
			novoServidor.setLotacaoExercicioId(funcionario.getFuncionarioFuncional().getLotacaoExercicio().getId());
			novoServidor.setLotacaoSubordinacao(
					funcionario.getFuncionarioFuncional().getLotacaoExercicio().getLotacaoSubordinacao());
			novoServidor.setLotacaoExercicioTelefone(
					funcionario.getFuncionarioFuncional().getLotacaoExercicio().getTelefonePrincipal());
		}

		if (funcionario.getFuncionarioFuncional().getCargoFuncao() != null) {
			novoServidor.setCargoFuncao(funcionario.getFuncionarioFuncional().getCargoFuncao().getDescricao());
			novoServidor.setCargoFuncaoId(funcionario.getFuncionarioFuncional().getCargoFuncao().getId());

			if (funcionario.getFuncionarioFuncional().getCargoFuncao().getFuncao() != null) {
				novoServidor.setFuncaoHierarquia(
						funcionario.getFuncionarioFuncional().getCargoFuncao().getFuncao().getHierarquia());
			}
		}

		if (funcionario.getFuncionarioFuncional().getCargoEfetivo() != null) {
			novoServidor.setCargoEfetivo(funcionario.getFuncionarioFuncional().getCargoEfetivo().getDescricao());
		}

		if (funcionario.getMatricula() != null) {
			novoServidor.setMatricula(funcionario.getMatricula());
		}

		if (funcionario.getNome() != null) {
			novoServidor.setNome(funcionario.getNomeFuncionario());
		}

		if (funcionario.getPessoa() != null) {
			novoServidor.setCpf(funcionario.getPessoa().getCpf());
			novoServidor.setDataNascimento(funcionario.getPessoa().getDataNascimento());
			novoServidor.setTipoSanguineo(funcionario.getPessoa().getTipoSanguineoFormatado());
			novoServidor.setDoador(funcionario.getPessoa().getDoadorFormatado());
			novoServidor.setEmail(funcionario.getPessoa().getEmail());
			novoServidor.setDataAdmissao(funcionario.getPessoa().getDataAdmissao());
			novoServidor.setSexo(funcionario.getPessoa().getSexoFormatado());
			novoServidor.setTelefone(funcionario.getPessoa().getTelefone());
		}

		return novoServidor;
	}

	public Usuario copiaUsuario(br.gov.ma.tce.seguranca.server.beans.usuario.Usuario usuario, Servidor s) {
		Usuario novoUsuario = new Usuario();
		if (usuario.getUsuarioId() != null) {
			novoUsuario.setUsuarioId(usuarioFacadeBean.findMaxId() + 1);
		}

		if (usuario.getMatricula() != null) {
			novoUsuario.setMatricula(usuario.getMatricula());
		}

		if (usuario.getSenha() != null) {
			novoUsuario.setSenha(usuario.getSenha());
		}

		novoUsuario.setServidor(s);

		return novoUsuario;
	}

	public Dependente copiaDependente(br.gov.ma.tce.seguranca.server.beans.dependente.Dependente dependente,
			Servidor s) {
		Dependente novoDependente = new Dependente();
		if (dependente.getId() != null) {
			novoDependente.setDependenteId(dependente.getId());
		}

		if (dependente.getDataNasc() != null) {
			novoDependente.setDataNascimento(dependente.getDataNasc());
		} else {

			novoDependente.setDataNascimento(null);
		}

		if (dependente.getNome() != null) {
			novoDependente.setNome(dependente.getNomeDependente());
		} else {

			novoDependente.setNome(null);
		}

		if (dependente.getSexoFormatado() != null) {
			novoDependente.setSexo(dependente.getSexoFormatado());
		} else {

			novoDependente.setSexo(null);
		}

		if (dependente.getParentesco() != null) {
			novoDependente.setParentesco(
					dependenteParentescoFacadeBean.findByPrimaryKey(dependente.getParentesco()).getDescricao());
		} else {

			novoDependente.setParentesco(null);
		}

		if (dependente.getTipoSangue() != null) {
			novoDependente.setTipoSanguineo(dependente.getTipoSangue());
		} else {

			novoDependente.setTipoSanguineo(null);
		}

		if (dependente.getTelefone() != null) {
			novoDependente.setTelefone(dependente.getTelefone());
		} else {

			novoDependente.setTelefone(null);
		}

		if (dependente.getDataExclusao() != null) {
			novoDependente.setDataExclusao(dependente.getDataExclusao());
		} else {

			novoDependente.setDataExclusao(null);
		}

		novoDependente.setServidor(s);

		return novoDependente;
	}

	public Dependente copiaDependente(br.gov.ma.tce.seguranca.server.beans.dependente.Dependente dependente) {
		Dependente novoDependente = new Dependente();
		if (dependente.getId() != null) {
			novoDependente.setDependenteId(dependente.getId());
		}

		if (dependente.getServidor() != null) {
			novoDependente.setServidor(servidorFacadeBean.findByPrimaryKey(dependente.getServidor()));
		}

		if (dependente.getDataNasc() != null) {
			novoDependente.setDataNascimento(dependente.getDataNasc());
		}

		if (dependente.getNome() != null) {
			novoDependente.setNome(dependente.getNomeDependente());
		}

		if (dependente.getSexoFormatado() != null) {
			novoDependente.setSexo(dependente.getSexoFormatado());
		}

		if (dependente.getParentesco() != null) {
			novoDependente.setParentesco(
					dependenteParentescoFacadeBean.findByPrimaryKey(dependente.getParentesco()).getDescricao());
		}

		if (dependente.getTipoSangue() != null) {
			novoDependente.setTipoSanguineo(dependente.getTipoSangue());
		}

		if (dependente.getTelefone() != null) {
			novoDependente.setTelefone(dependente.getTelefone());
		}

		if (dependente.getDataExclusao() != null) {
			novoDependente.setDataExclusao(dependente.getDataExclusao());
		}

		return novoDependente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioGrupo getUsuarioGrupo() {
		return usuarioGrupo;
	}

	public void setUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		this.usuarioGrupo = usuarioGrupo;
	}

	public Collection<Servidor> getServidores() {
		return servidores;
	}

	public void setServidores(Collection<Servidor> servidores) {
		this.servidores = servidores;
	}

	public Collection<Servidor> getServidoresComAlteracao() {
		return servidoresComAlteracao;
	}

	public void setServidoresComAlteracao(Collection<Servidor> servidoresComAlteracao) {
		this.servidoresComAlteracao = servidoresComAlteracao;
	}

	public Collection<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(Collection<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	public Collection<Dependente> getDependentesComAlteracao() {
		return dependentesComAlteracao;
	}

	public void setDependentesComAlteracao(Collection<Dependente> dependentesComAlteracao) {
		this.dependentesComAlteracao = dependentesComAlteracao;
	}

}
