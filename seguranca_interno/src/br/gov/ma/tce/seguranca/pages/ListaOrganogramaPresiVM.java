package br.gov.ma.tce.seguranca.pages;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.zkoss.bind.annotation.Init;

import br.gov.ma.tce.seguranca.interno.server.beans.servidor.Servidor;
import br.gov.ma.tce.seguranca.interno.server.beans.servidor.ServidorFacadeBean;

public class ListaOrganogramaPresiVM {
	
	private Servidor servidor;
	private Servidor servidor2;
	private ServidorFacadeBean servidorFacadeBean;
	
	public ListaOrganogramaPresiVM() {
		try {
			InitialContext ctx = new InitialContext();
			servidorFacadeBean = (ServidorFacadeBean) ctx.lookup(ServidorFacadeBean.URI);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Init
	public void init() {
	
		servidor = servidorFacadeBean.findByCargoFuncao(710);
		if(servidor == null){
			servidor = new Servidor();
			servidor.setNome("NÃO CADASTRADO");
		}
		servidor2 = servidorFacadeBean.findByCargoFuncao(711);
		if(servidor2 == null){
			servidor2 = new Servidor();
			servidor2.setNome("NÃO CADASTRADO");
		}
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public Servidor getServidor2() {
		return servidor2;
	}

	public void setServidor2(Servidor servidor2) {
		this.servidor2 = servidor2;
	}


}
