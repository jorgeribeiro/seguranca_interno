package br.gov.ma.tce.seguranca.interno.server.beans.grupousuario;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.ma.tce.seguranca.interno.server.beans.sistema.Sistema;
import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupo;

/**
 * 
 * @author acbras
 * 
 *         Schema: Seguranca Tabela: GRUPO_USUARIO
 *
 */

@Entity(name = GrupoUsuario.NAME)
@Table(name = "seguranca_interno.grupo_usuario")
public class GrupoUsuario {

	public static final String NAME = "SEGURANCA_INTERNO_GRUPO_USUARIO";

	@Id
	@SequenceGenerator(name = "seguranca_interno.seq_grupo_usuario", sequenceName = "seguranca_interno.seq_grupo_usuario", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seguranca_interno.seq_grupo_usuario")
	@Column(name = "grupo_usuario_id")
	private Integer grupoUsuarioId;

	@ManyToOne
	@JoinColumn(name = "sistema_id")
	private Sistema sistema;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "grupoUsuario")
	private Set<UsuarioGrupo> usuarioGrupos;

	@Column(name = "nome")
	private String nome;

	@Transient
	public String getNomeSistema() {

		try {

			return this.sistema.getNome();

		} catch (RuntimeException e) {

			e.printStackTrace();
			return "";
		}
	}

	@Transient
	public String getNomeGrupoSistema() {
		try {
			return this.getNome() + " - " + this.getNomeSistema();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return "";
		}
	}

	public Integer getGrupoUsuarioId() {
		return grupoUsuarioId;
	}

	public void setGrupoUsuarioId(Integer grupoUsuarioId) {
		this.grupoUsuarioId = grupoUsuarioId;
	}

	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	public Set<UsuarioGrupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}

	public void setUsuarioGrupos(Set<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}