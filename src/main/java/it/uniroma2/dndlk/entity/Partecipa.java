package it.uniroma2.dndlk.entity;

public class Partecipa {
	
	private int idCampagna;
	private int idUtente;
	private boolean dungeonMaster;
	
	public Partecipa(int idCampagna, int idUtente, boolean dungeonMaster) {
		super();
		this.idCampagna = idCampagna;
		this.idUtente = idUtente;
		this.dungeonMaster = dungeonMaster;
	}

	@Override
	public String toString() {
		return "Partecipa [idCampagna=" + idCampagna + ", idUtente=" + idUtente + ", dungeonMaster=" + dungeonMaster
				+ "]";
	}

	public boolean isDungeonMaster() {
		return dungeonMaster;
	}

	public void setDungeonMaster(boolean dungeonMaster) {
		this.dungeonMaster = dungeonMaster;
	}

	public int getIdCampagna() {
		return idCampagna;
	}

	public int getIdUtente() {
		return idUtente;
	}
	
	
	
	

}
