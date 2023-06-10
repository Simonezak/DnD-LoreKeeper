package it.uniroma2.dndlk.entity;

public class Utente {

	private int idUtente;
	private String username, password, email;

	
	public Utente(int idUtente, String username, String email, String password) {
		this.idUtente = idUtente;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "User [idUtente= "+ idUtente + "username= " + username + ", email= " + email
				+ ", password= " + password + "]";
	}

	public int getIdUtente() {
		return idUtente;
	}
	
	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

}
