package br.gov.ma.tce.seguranca.util;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

import br.gov.ma.tce.seguranca.interno.server.beans.usuario.Usuario;

public class InitPage implements Initiator {

	@Override
	public void doInit(Page arg0, Map<String, Object> arg1) throws Exception {
		Usuario usuario = (Usuario) Sessions.getCurrent().getAttribute("usuario");
		if (usuario == null) {
			Executions.sendRedirect("/login.zul");
		}
	}

}
