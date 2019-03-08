package br.gov.ma.tce.seguranca.pages;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.Scope;
import org.zkoss.bind.annotation.ScopeParam;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import br.gov.ma.tce.seguranca.interno.server.beans.usuario.Usuario;

public class MenubarVM {

	@Init
	public void init(@ScopeParam(scopes = Scope.SESSION, value = "usuario") Usuario usuario) {
		if (usuario == null) {
			Executions.sendRedirect("/login.zul");
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

}
