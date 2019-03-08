package br.gov.ma.tce.seguranca.pages;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.annotation.Init;

import br.gov.ma.tce.seguranca.interno.server.beans.servidor.Servidor;
import br.gov.ma.tce.seguranca.interno.server.beans.servidor.ServidorFacadeBean;




public class ListaOrganogramaPlenoVM {
	
	private Servidor servidor;
	private ServidorFacadeBean servidorFacadeBean;
	
	public ListaOrganogramaPlenoVM() {
		try {
			InitialContext ctx = new InitialContext();
			servidorFacadeBean = (ServidorFacadeBean) ctx.lookup(ServidorFacadeBean.URI);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Init
	public void init() {
	
		servidor = servidorFacadeBean.findByCargoFuncao(831);
		if(servidor == null){
			servidor = new Servidor();
			servidor.setNome("NÃO CADASTRADO");
		}
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}


}
