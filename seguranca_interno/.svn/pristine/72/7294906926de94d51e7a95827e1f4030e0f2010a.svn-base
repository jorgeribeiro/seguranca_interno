package br.gov.ma.tce.seguranca.pages;

import java.util.Collection;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.Scope;
import org.zkoss.bind.annotation.ScopeParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;

import br.gov.ma.tce.seguranca.interno.server.beans.usuario.Usuario;
import br.gov.ma.tce.seguranca.interno.server.beans.usuario.UsuarioFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupo;
import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupoFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.exception.BusinessException;

public class LoginVM {
	private Usuario usuario;

	private Integer login;
	private String senha;

	private UsuarioFacadeBean usuarioFacadeBean;
	private UsuarioGrupoFacadeBean usuarioGrupoFacadeBean;

	public LoginVM() {
		try {
			InitialContext ctx = new InitialContext();
			usuarioFacadeBean = (UsuarioFacadeBean) ctx.lookup(UsuarioFacadeBean.URI);
			usuarioGrupoFacadeBean = (UsuarioGrupoFacadeBean) ctx.lookup(UsuarioGrupoFacadeBean.URI);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Init
	public void init(
			@ScopeParam(scopes = Scope.SESSION, value = "usuario") Usuario usuario) {
		if (usuario != null) {
			Executions.sendRedirect("/index.zul");
		}
	}

	@Command
	@NotifyChange(".")
	public void login() {
		try {
			Boolean temErro = true;
			if ((login == null) || (senha == null || senha.trim().isEmpty())) {
				throw new BusinessException("Preencha os campos de login e senha.");
			}

			usuario = usuarioFacadeBean.findByMatricula(login);
			if (usuario == null) {
				throw new BusinessException("Credenciais inválidas.");
			} else if (!usuario.getSenha().equals(senha)) {
				throw new BusinessException("Credenciais inválidas.");
			}

			else {
				Collection<UsuarioGrupo> usuarioGrupos = usuarioGrupoFacadeBean.findGruposByUsuario(usuario);
				for (UsuarioGrupo grupo : usuarioGrupos) {
					if (grupo.getGrupoUsuario().getSistema().getSigla().equals("SEG")) {
						temErro = false;
						Sessions.getCurrent().setAttribute("usuario", usuario);
						Sessions.getCurrent().setAttribute("usuarioGrupo", grupo);
						Executions.sendRedirect("/index.zul");
					}
				}

				if (temErro) {
					throw new BusinessException("Usuário sem permissão de acesso ao Segurança.");
				}
			}
		} catch (BusinessException e) {
			Clients.showNotification(e.getMessage(), Clients.NOTIFICATION_TYPE_WARNING, null, null, 3000, true);
		}
	}

	@Command
	@NotifyChange(".")
	public void sair() {
		try {
			Sessions.getCurrent().invalidate();
			Executions.sendRedirect("/login.zul");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getLogin() {
		return login;
	}

	public void setLogin(Integer login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
