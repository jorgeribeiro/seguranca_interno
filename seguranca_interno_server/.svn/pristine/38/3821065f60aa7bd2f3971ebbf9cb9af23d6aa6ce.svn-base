package br.gov.ma.tce.seguranca.interno.server.beans.usuario;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.ma.tce.seguranca.interno.server.beans.servidor.Servidor;
import br.gov.ma.tce.seguranca.interno.server.beans.usuariogrupo.UsuarioGrupo;

/**
 * 
 * @author acbras
 * 
 *         Schema: Seguranca Tabela: USUARIO
 *
 */

@Entity(name = Usuario.NAME)
@Table(name = "seguranca_interno.usuario")
public class Usuario {

	public static final String NAME = "SEGURANCA_INTERNO_USUARIO";

	public Usuario() {

	}

	public Usuario(Integer usuarioId, Servidor servidor, Integer matricula, String senha) {
		super();
		this.usuarioId = usuarioId;
		this.servidor = servidor;
		this.matricula = matricula;
		this.senha = senha;
	}

	@Id
	@Column(name = "usuario_id")
	private Integer usuarioId;

	@ManyToOne
	@JoinColumn(name = "servidor_id")
	private Servidor servidor;

	@Column(name = "matricula")
	private Integer matricula;

	@Column(name = "senha")
	private String senha;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "usuario")
	private Set<UsuarioGrupo> usuarioGrupos;

	@Transient
	public String getNomeMatricula() {
		try {
			return servidor.getNome() + " (" + matricula + ")";
		} catch (Exception e) {
			return "-";
		}
	}

	@Transient
	public String getNome() {
		try {
			return servidor.getNome().trim();
		} catch (Exception e) {
			return "-";
		}
	}

	@Transient
	public String getEmail() {
		try {
			return servidor.getEmail().toLowerCase();
		} catch (Exception e) {
			return "-";
		}
	}

	@Transient
	public String getSetor() {
		try {
			if (servidor.getLotacaoExercicio() != null) {
				return servidor.getLotacaoExercicio();
			} else {
				return "-";
			}
		} catch (Exception e) {
			return "-";
		}
	}

	@Transient
	public String getTelefone() {
		try {
			if (servidor.getTelefone() != null) {
				return servidor.getTelefone();
			} else {
				return "-";
			}
		} catch (Exception e) {
			return "-";
		}
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<UsuarioGrupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}

	public void setUsuarioGrupos(Set<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

}
