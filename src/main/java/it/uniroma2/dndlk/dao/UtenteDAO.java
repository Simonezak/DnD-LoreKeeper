package it.uniroma2.dndlk.dao;

import it.uniroma2.dndlk.entity.Utente;

public interface UtenteDAO {
	
	public int registrazione(Utente utente);
	
	public int login(String username, String password);
	
	public String verificaUtente(String username);
	
	//da mettere nella servlet apposita
	public int lastIdUtente();
	
	public void closeConnection();

}
