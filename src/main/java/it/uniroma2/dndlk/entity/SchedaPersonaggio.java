package it.uniroma2.dndlk.entity;

import java.sql.Blob;

public class SchedaPersonaggio {
	
	private int idSchedaPersonaggio;
	private String nomeScheda;
	private Blob contenutoScheda;
	private int idCampagna;
	private int idUtente;
	
	public SchedaPersonaggio(int idSchedaPersonaggio, String nomeScheda, Blob contenutoScheda, int idCampagna,
			int idUtente) {
		super();
		this.idSchedaPersonaggio = idSchedaPersonaggio;
		this.nomeScheda = nomeScheda;
		this.contenutoScheda = contenutoScheda;
		this.idCampagna = idCampagna;
		this.idUtente = idUtente;
	}

	@Override
	public String toString() {
		return "SchedaPersonaggio [idSchedaPersonaggio=" + idSchedaPersonaggio + ", nomeScheda=" + nomeScheda
				+ ", contenutoScheda=" + contenutoScheda + ", idCampagna=" + idCampagna + ", idUtente=" + idUtente
				+ "]";
	}

	public int getIdSchedaPersonaggio() {
		return idSchedaPersonaggio;
	}

	public void setIdSchedaPersonaggio(int idSchedaPersonaggio) {
		this.idSchedaPersonaggio = idSchedaPersonaggio;
	}

	public String getNomeScheda() {
		return nomeScheda;
	}

	public void setNomeScheda(String nomeScheda) {
		this.nomeScheda = nomeScheda;
	}

	public Blob getContenutoScheda() {
		return contenutoScheda;
	}

	public void setContenutoScheda(Blob contenutoScheda) {
		this.contenutoScheda = contenutoScheda;
	}

	public int getIdCampagna() {
		return idCampagna;
	}

	public int getIdUtente() {
		return idUtente;
	}
	

}
