package br.gov.ma.tce.seguranca.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupo;

public class UsuariosSistemaVO {

	private String nome, matricula, email, lotacao, grupo;

	public UsuariosSistemaVO(String nome, String matricula, String email, String lotacao, String grupo) {
		this.nome = nome;
		this.matricula = matricula;
		this.email = email;
		this.lotacao = lotacao;
		this.grupo = grupo;
	}

	public static List<UsuariosSistemaVO> preencheLista(Collection<UsuarioGrupo> usuariosSistema) {
		List<UsuariosSistemaVO> us = new ArrayList<UsuariosSistemaVO>();

		for (UsuarioGrupo ug : usuariosSistema) {
			UsuariosSistemaVO object = new UsuariosSistemaVO(ug.getUsuario().getNome(),
					ug.getUsuario().getMatricula().toString(), ug.getUsuario().getEmail(), ug.getUsuario().getSetor(),
					ug.getNomeGrupo());
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

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

}
