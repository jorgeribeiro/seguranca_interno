package br.gov.ma.tce.seguranca.pages;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

import br.gov.ma.tce.seguranca.interno.server.beans.grupousuario.GrupoUsuario;
import br.gov.ma.tce.seguranca.interno.server.beans.grupousuario.GrupoUsuarioFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.beans.sistema.Sistema;
import br.gov.ma.tce.seguranca.interno.server.beans.sistema.SistemaFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupo;
import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupoFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.exception.BusinessException;
import br.gov.ma.tce.seguranca.util.Report;
import br.gov.ma.tce.seguranca.vo.UsuariosGrupoVO;

public class GrupoUsuarioVM {
	private Sistema sistemaSelecionado;
	private GrupoUsuario novoGrupo, grupoSelecionado;
	private UsuarioGrupo usuarioGrupoSelecionado;

	private Collection<Sistema> sistemas;
	private Collection<UsuarioGrupo> usuariosGrupo;
	private Collection<GrupoUsuario> gruposSistema;
	private File arquivo;

	private SistemaFacadeBean sistemaFacade;
	private UsuarioGrupoFacadeBean usuarioGrupoFacade;
	private GrupoUsuarioFacadeBean grupoUsuarioFacade;
	

	@Wire("#modalNovoGrupo")
	private Window modalNovoGrupo;

	@Wire("#modalVisualizarGrupos")
	private Window modalVisualizarGrupos;

	@Wire("#modalVisualizarUsuarios")
	private Window modalVisualizarUsuarios;
	
	@Wire("#modalConfirmaRemoverDoGrupo")
	private Window modalConfirmaRemoverDoGrupo;
	
	@Wire("#modalNovoGrupo #comboSistemas")
	private Combobox comboSistemas;
	
	@Wire("#modalVisualizaRelatorioUsuarios #iframeRelatorio")
	private Iframe iframeRelatorio;

	@Wire("#modalVisualizaRelatorioUsuarios")
	private Window modalVisualizaRelatorioUsuarios;

	public GrupoUsuarioVM() {
		try {
			InitialContext ctx = new InitialContext();
			sistemaFacade = (SistemaFacadeBean) ctx.lookup(SistemaFacadeBean.URI);
			usuarioGrupoFacade = (UsuarioGrupoFacadeBean) ctx.lookup(UsuarioGrupoFacadeBean.URI);
			grupoUsuarioFacade = (GrupoUsuarioFacadeBean) ctx.lookup(GrupoUsuarioFacadeBean.URI);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Init
	public void init() {
		try {
			sistemas = sistemaFacade.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	@Command
	@NotifyChange(".")
	public void abrirModalNovoGrupo(@BindingParam("visible") boolean visible) {
		try {			
			if (visible) {
				novoGrupo = new GrupoUsuario();
			} else {
				novoGrupo.setSistema(null);
				novoGrupo.setNome(null);
			}
			
			comboSistemas.setSelectedItem(null);			
			modalNovoGrupo.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange(".")
	public void incluirGrupo() {
		try {
			if ((novoGrupo.getNome() == null || novoGrupo.getNome().trim().isEmpty())
					|| novoGrupo.getSistema() == null) {
				throw new BusinessException("Preencha todos os campos exibidos.");
			}

			modalNovoGrupo.setVisible(false);
			grupoUsuarioFacade.insert(novoGrupo);
			Clients.showNotification("Grupo incluído com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null, null, 3000,
					true);
		} catch (BusinessException e) {
			Clients.showNotification(e.getMessage(), Clients.NOTIFICATION_TYPE_WARNING, null, null, 3000, true);
		}
	}

	@Command
	@NotifyChange(".")
	public void abrirModalVisualizarGrupos(@BindingParam("visible") boolean visible,
			@BindingParam("sistema") Sistema sistema) {
		try {
			if (visible) {
				gruposSistema = grupoUsuarioFacade.findGruposBySistema(sistema);
			}

			sistemaSelecionado = sistema;
			modalVisualizarGrupos.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange(".")
	public void abrirModalVisualizarUsuarios(@BindingParam("visible") boolean visible,
			@BindingParam("grupo") GrupoUsuario grupo) {
		try {
			if (visible) {
				usuariosGrupo = usuarioGrupoFacade.findUsuariosByGrupo(grupo);
			}

			grupoSelecionado = grupo;
			modalVisualizarUsuarios.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange(".")
	public void abrirModalRelatorioUsuariosPorGrupo(@BindingParam("visible") Boolean visible) {
		try {
			if (visible) {
				String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
				List<UsuariosGrupoVO> usuariosGrupoVO = UsuariosGrupoVO.preencheLista(usuariosGrupo);
				Map<String, Object> parameters = new HashMap<String, Object>();
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				parameters.put("DATA_CRIACAO", df.format(new Date()));
				parameters.put("NOME_GRUPO", grupoSelecionado.getNomeGrupoSistema().toUpperCase());
				parameters.put("IMAGE_DIR", path + "/imagens");

				String filename = "usuarios_grupo.jasper";
				arquivo = Report.getReportUsuariosGrupo(path + "/jasper", usuariosGrupoVO, filename, parameters);
				iframeRelatorio.setContent(new AMedia(arquivo, "application/pdf", null));
				modalVisualizaRelatorioUsuarios.setVisible(visible);
			} else {
				arquivo = null;
				iframeRelatorio.setContent(null);
				modalVisualizaRelatorioUsuarios.setVisible(visible);
			}
		} catch (Exception e) {
			Clients.showNotification(e.getMessage(), Clients.NOTIFICATION_TYPE_WARNING, null, null, 3000, true);
		}
	}
	
	
	@Command
	@NotifyChange(".")
	public void abrirModalConfirmaRemoverDoGrupo(@BindingParam("visible") Boolean visible,
			@BindingParam("usuarioGrupo") UsuarioGrupo usuarioGrupo) {
		try {
			usuarioGrupoSelecionado = usuarioGrupo;
			modalConfirmaRemoverDoGrupo.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange(".")
	public void removerDoGrupo() {
		try {
			usuarioGrupoFacade.delete(usuarioGrupoSelecionado.getUsuarioGrupoId());
			usuariosGrupo.remove(usuarioGrupoSelecionado);
			modalConfirmaRemoverDoGrupo.setVisible(false);
			Clients.showNotification("Usuário removido do grupo com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null,
					null, 3000, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Sistema getSistemaSelecionado() {
		return sistemaSelecionado;
	}

	public void setSistemaSelecionado(Sistema sistemaSelecionado) {
		this.sistemaSelecionado = sistemaSelecionado;
	}

	public GrupoUsuario getNovoGrupo() {
		return novoGrupo;
	}

	public void setNovoGrupo(GrupoUsuario novoGrupo) {
		this.novoGrupo = novoGrupo;
	}

	public GrupoUsuario getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(GrupoUsuario grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public UsuarioGrupo getUsuarioGrupoSelecionado() {
		return usuarioGrupoSelecionado;
	}

	public void setUsuarioGrupoSelecionado(UsuarioGrupo usuarioGrupoSelecionado) {
		this.usuarioGrupoSelecionado = usuarioGrupoSelecionado;
	}

	public Collection<Sistema> getSistemas() {
		return sistemas;
	}

	public void setSistemas(Collection<Sistema> sistemas) {
		this.sistemas = sistemas;
	}

	public Collection<UsuarioGrupo> getUsuariosGrupo() {
		return usuariosGrupo;
	}

	public void setUsuariosGrupo(Collection<UsuarioGrupo> usuariosGrupo) {
		this.usuariosGrupo = usuariosGrupo;
	}

	public Collection<GrupoUsuario> getGruposSistema() {
		return gruposSistema;
	}

	public void setGruposSistema(Collection<GrupoUsuario> gruposSistema) {
		this.gruposSistema = gruposSistema;
	}

}
