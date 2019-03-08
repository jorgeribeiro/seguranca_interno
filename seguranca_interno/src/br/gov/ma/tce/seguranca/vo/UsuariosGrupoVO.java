package br.gov.ma.tce.seguranca.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupo;

public class UsuariosGrupoVO {

	private String nome, matricula, email, lotacao;

	public UsuariosGrupoVO(String nome, String matricula, String email, String lotacao) {
		this.nome = nome;
		this.matricula = matricula;
		this.email = email;
		this.lotacao = lotacao;
	}

	public static List<UsuariosGrupoVO> preencheLista(Collection<UsuarioGrupo> usuariosGrupo) {
		List<UsuariosGrupoVO> us = new ArrayList<UsuariosGrupoVO>();

		for (UsuarioGrupo ug : usuariosGrupo) {
			UsuariosGrupoVO object = new UsuariosGrupoVO(ug.getUsuario().getNome(),
					ug.getUsuario().getMatricula().toString(), ug.getUsuario().getEmail(), ug.getUsuario().getSetor());
			us.add(object);
		}

		return us;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLotacao() {
		return lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

}
