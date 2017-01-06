package sml.manager.api.aspect.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.engine.internal.Cascade;

import aj.org.objectweb.asm.Type;

@Entity
@Table(name="tab_requisicao", schema="testando")
public class Requisicao implements Serializable{

	private static final long serialVersionUID = -7700060984818848442L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="data", nullable=false)
	private Calendar data;
	
	@Column(name="tempo_execucao", nullable=false)
	private long tempoExecucao;
	
	@Column(name="ip_origem", nullable=false, length = 15)
	private String ipOrigem;
	
	@Column(name="tipo", nullable=false, length = 6)
	private String tipo;
	
	@OneToOne(mappedBy="requisicao", cascade = CascadeType.ALL)
	private Parametro parametro;
	
	/*@OneToOne(optional=true, mappedBy="requisicao")
	private Erro exception;*/

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public long getTempoExecucao() {
		return tempoExecucao;
	}

	public void setTempoExecucao(long tempoExecucao) {
		this.tempoExecucao = tempoExecucao;
	}

	public String getIpOrigem() {
		return ipOrigem;
	}

	public void setIpOrigem(String ipOrigem) {
		this.ipOrigem = ipOrigem;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	/*public Erro getException() {
		return exception;
	}

	public void setException(Erro exception) {
		this.exception = exception;
	}*/
	
}
