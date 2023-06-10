package it.uniroma2.dndlk.dao;

import org.json.JSONArray;

import it.uniroma2.dndlk.entity.Campagna;

public interface CampagneDAO {

		public JSONArray loadCampagneByUsername(String username); 
		
		public Campagna loadCampagna(String nomeCampaign);
		
		public int deleteUtenteDaCampagna(int idCampagna, int idUtente);
		
		public int aggiungiUtenteACampagna(Campagna campagna, int idUtente);
		
		public int creaCampagna(Campagna campagna,int idUtente);
		
		public int lastIdCampagna();
		
		public void closeConnection(); 
			
}
