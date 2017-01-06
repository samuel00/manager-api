package sml.manager.api.aspect.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tab_requisicao_exception")
public class Erro implements Serializable{
	
	private static final long serialVersionUID = -4609689208810205727L;
	
	@Id
	@OneToOne
	@JoinColumn(name="requisicao_id", unique=true, nullable=false, updatable=false)
	private Requisicao requisicao;
	
	@Column(name= "motivo_ocorrencia", nullable=true, updatable = false)
	private String motivo;
	
	@Column(name= "classe_ocorrencia", nullable=true, updatable = false)
	private String classe;

	public Requisicao getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(Requisicao requisicao) {
		this.requisicao = requisicao;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}
	

}
