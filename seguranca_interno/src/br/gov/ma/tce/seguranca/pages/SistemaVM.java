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
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

import br.gov.ma.tce.seguranca.interno.server.beans.sistema.Sistema;
import br.gov.ma.tce.seguranca.interno.server.beans.sistema.SistemaFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupo;
import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupoFacadeBean;
import br.gov.ma.tce.seguranca.interno.server.exception.BusinessException;
import br.gov.ma.tce.seguranca.util.Report;
import br.gov.ma.tce.seguranca.vo.UsuariosSistemaVO;

public class SistemaVM {
	private Sistema novoSistema, sistemaSelecionado;
	private UsuarioGrupo usuarioGrupoSelecionado;

	private Collection<Sistema> sistemas;
	private Collection<UsuarioGrupo> usuariosSistema;
	private File arquivo;

	private SistemaFacadeBean sistemaFacade;
	private UsuarioGrupoFacadeBean usuarioGrupoFacade;	

	@Wire("#modalNovoSistema")
	private Window modalNovoSistema;

	@Wire("#modalVisualizarUsuarios")
	private Window modalVisualizarUsuarios;

	@Wire("#modalVisualizaRelatorioUsuarios #iframeRelatorio")
	private Iframe iframeRelatorio;

	@Wire("#modalVisualizaRelatorioUsuarios")
	private Window modalVisualizaRelatorioUsuarios;
	
	@Wire("#modalConfirmaRemoverDoGrupo")
	private Window modalConfirmaRemoverDoGrupo;

	public SistemaVM() {
		try {
			InitialContext ctx = new InitialContext();
			sistemaFacade = (SistemaFacadeBean) ctx.lookup(SistemaFacadeBean.URI);
			usuarioGrupoFacade = (UsuarioGrupoFacadeBean) ctx.lookup(UsuarioGrupoFacadeBean.URI);
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
	public void abrirModalNovoSistema(@BindingParam("visible") boolean visible) {
		try {
			if (visible) {
				novoSistema = new Sistema();
			}
			modalNovoSistema.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange(".")
	public void incluirSistema() {
		try {
			if ((novoSistema.getNome() == null || novoSistema.getNome().trim().isEmpty())
					|| (novoSistema.getSigla() == null || novoSistema.getSigla().trim().isEmpty())) {
				throw new BusinessException("Preencha todos os campos exibidos.");
			}

			sistemaFacade.insert(novoSistema);
			modalNovoSistema.setVisible(false);
			sistemas.add(novoSistema);
			Clients.showNotification("Sistema incluído com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null, null, 3000,
					true);
		} catch (BusinessException e) {
			Clients.showNotification(e.getMessage(), Clients.NOTIFICATION_TYPE_WARNING, null, null, 3000, true);
		}
	}

	@Command
	@NotifyChange(".")
	public void abrirModalVisualizarUsuarios(@BindingParam("visible") boolean visible,
			@BindingParam("sistema") Sistema sistema) {
		try {
			sistemaSelecionado = sistema;
			if (visible) {
				usuariosSistema = usuarioGrupoFacade.findUsuariosBySistema(sistema);
			}

			modalVisualizarUsuarios.setVisible(visible);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	@NotifyChange(".")
	public void abrirModalRelatorioUsuariosPorSistema(@BindingParam("visible") Boolean visible) {
		try {
			if (visible) {
				String path = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
				List<UsuariosSistemaVO> usuariosSistemaVO = UsuariosSistemaVO.preencheLista(usuariosSistema);
				Map<String, Object> parameters = new HashMap<String, Object>();
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				parameters.put("DATA_CRIACAO", df.format(new Date()));
				parameters.put("NOME_SISTEMA", sistemaSelecionado.getNome().toUpperCase());
				parameters.put("IMAGE_DIR", path + "/imagens");

				String filename = "usuarios_sistema.jasper";
				arquivo = Report.getReportUsuariosSistema(path + "/jasper", usuariosSistemaVO, filename, parameters);
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
			usuariosSistema.remove(usuarioGrupoSelecionado);
			modalConfirmaRemoverDoGrupo.setVisible(false);
			Clients.showNotification("Usuário removido do grupo com sucesso!", Clients.NOTIFICATION_TYPE_INFO, null,
					null, 3000, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Sistema getNovoSistema() {
		return novoSistema;
	}

	public void setNovoSistema(Sistema novoSistema) {
		this.novoSistema = novoSistema;
	}

	public Sistema getSistemaSelecionado() {
		return sistemaSelecionado;
	}

	public void setSistemaSelecionado(Sistema sistemaSelecionado) {
		this.sistemaSelecionado = sistemaSelecionado;
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

	public Collection<UsuarioGrupo> getUsuariosSistema() {
		return usuariosSistema;
	}

	public void setUsuariosSistema(Collection<UsuarioGrupo> usuariosSistema) {
		this.usuariosSistema = usuariosSistema;
	}	

}
