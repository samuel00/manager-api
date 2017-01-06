package sml.manager.api.aspect.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="tab_requisicao_parametro", schema="testando")
public class Parametro implements Serializable{
	
	private static final long serialVersionUID = -221708863259338535L;
	
	@Id
	@GeneratedValue( strategy= GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	@JoinColumn(name="requisicao_id")
	private Requisicao requisicao;
	
	@Column(name= "entrada", nullable=false, updatable = false)
	private String entrada;
	
	@Column(name= "saida", nullable=true, updatable = false)
	private String saida;
	
	@Column(name= "metodo_invocado", nullable=false, updatable = false)
	private String metodoInvocado;
	
	@Column(name= "classe_invocada", nullable=false, updatable = false)
	private String classeInvocada;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Requisicao getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(Requisicao requisicao) {
		this.requisicao = requisicao;
	}

	public String getEntrada() {
		return entrada;
	}

	public void setEntrada(String entrada) {
		this.entrada = entrada;
	}

	public String getSaida() {
		return saida;
	}

	public void setSaida(String saida) {
		this.saida = saida;
	}

	public String getMetodoInvocado() {
		return metodoInvocado;
	}

	public void setMetodoInvocado(String metodoInvocado) {
		this.metodoInvocado = metodoInvocado;
	}

	public String getClasseInvocada() {
		return classeInvocada;
	}

	public void setClasseInvocada(String classeInvocada) {
		this.classeInvocada = classeInvocada;
	}
	
	

}
