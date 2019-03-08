package br.gov.ma.tce.seguranca.interno.server.beans.sistema;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.gov.ma.tce.seguranca.interno.server.beans.grupousuario.GrupoUsuario;

/**
 * 
 * @author acbras
 * 
 *         Schema: Seguranca Tabela: SISTEMA
 *
 */

@Entity(name = Sistema.NAME)
@Table(name = "seguranca_interno.sistema")
public class Sistema {

	public static final String NAME = "SEGURANCA_INTERNO_SISTEMA";

	@Id
	@SequenceGenerator(name = "seguranca_interno.seq_sistema", sequenceName = "seguranca_interno.seq_sistema", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seguranca_interno.seq_sistema")
	@Column(name = "sistema_id")
	private Integer sistemaId;

	@Column(name = "nome")
	private String nome;

	@Column(name = "sigla")
	private String sigla;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sistema")
	private Set<GrupoUsuario> gruposUsuario;

	public Integer getSistemaId() {
		return sistemaId;
	}

	public void setSistemaId(Integer sistemaId) {
		this.sistemaId = sistemaId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Set<GrupoUsuario> getGruposUsuario() {
		return gruposUsuario;
	}

	public void setGruposUsuario(Set<GrupoUsuario> gruposUsuario) {
		this.gruposUsuario = gruposUsuario;
	}

}
