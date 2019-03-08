package br.gov.ma.tce.seguranca.interno.server.beans.dependente;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.gov.ma.tce.seguranca.interno.server.beans.servidor.Servidor;
import br.gov.ma.tce.seguranca.interno.server.beans.util.StringUtil;

/**
 * 
 * @author acbras
 * 
 *         Schema: SQLUser Tabela: RHCadDependente
 *
 */

@Entity(name = Dependente.NAME)
@Table(name = "seguranca_interno.dependente")
public class Dependente implements Serializable {

	public static final String NAME = "SEGURANCA_INTERNO_DEPENDENTE";
	private static final long serialVersionUID = 1L;
	
	public Dependente () {
		
	}

	public Dependente(Integer dependenteId, Servidor servidor, String nome, Date dataNascimento, String sexo,
			String parentesco, String tipoSanguineo, String telefone, Date dataExclusao) {
		super();
		this.dependenteId = dependenteId;
		this.servidor = servidor;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.parentesco = parentesco;
		this.tipoSanguineo = tipoSanguineo;
		this.telefone = telefone;
		this.dataExclusao = dataExclusao;
	}

	@Id
	@Column(name = "dependente_id")
	private Integer dependenteId;

	@ManyToOne
	@JoinColumn(name = "servidor_id")
	private Servidor servidor;

	@Column(name = "nome")
	private String nome;

	@Column(name = "data_nascimento")
	private Date dataNascimento;

	@Column(name = "sexo")
	private String sexo;

	@Column(name = "parentesco")
	private String parentesco;

	@Column(name = "tipo_sanguineo")
	private String tipoSanguineo;

	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "data_exclusao")
	private Date dataExclusao;

	@Transient
	public String getNomeDependente() {
		if (this.nome == null) {
			return "";
		} else {
			return StringUtil.capitalizeEveryWord(this.nome).replace(" Da ", " da ").replace(" De ", " de ")
					.replace(" Das ", " das ").replace(" Dos ", " dos ").replace(" Do ", " do ");
		}
	}

	public Integer getDependenteId() {
		return dependenteId;
	}

	public void setDependenteId(Integer dependenteId) {
		this.dependenteId = dependenteId;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Servidor getServidor() {
		return servidor;
	}

	public void setServidor(Servidor servidor) {
		this.servidor = servidor;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}

	public String getTipoSanguineo() {
		return tipoSanguineo;
	}

	public void setTipoSanguineo(String tipoSanguineo) {
		this.tipoSanguineo = tipoSanguineo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dependente other = (Dependente) obj;
		if (dataExclusao == null) {
			if (other.dataExclusao != null)
				return false;
		} else if (!dataExclusao.equals(other.dataExclusao))
			return false;
		if (dataNascimento == null) {
			if (other.dataNascimento != null)
				return false;
		} else if (!dataNascimento.equals(other.dataNascimento))
			return false;
		if (dependenteId == null) {
			if (other.dependenteId != null)
				return false;
		} else if (!dependenteId.equals(other.dependenteId))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (parentesco == null) {
			if (other.parentesco != null)
				return false;
		} else if (!parentesco.equals(other.parentesco))
			return false;
		if (servidor == null) {
			if (other.servidor != null)
				return false;
		} else if (!servidor.equals(other.servidor))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (telefone == null) {
			if (other.telefone != null)
				return false;
		} else if (!telefone.equals(other.telefone))
			return false;
		if (tipoSanguineo == null) {
			if (other.tipoSanguineo != null)
				return false;
		} else if (!tipoSanguineo.equals(other.tipoSanguineo))
			return false;
		return true;
	}

}
