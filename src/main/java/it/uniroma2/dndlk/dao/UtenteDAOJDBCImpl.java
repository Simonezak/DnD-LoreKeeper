package it.uniroma2.dndlk.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import it.uniroma2.dndlk.entity.Utente;


public class UtenteDAOJDBCImpl implements UtenteDAO {

	private Connection conn;
	
	public UtenteDAOJDBCImpl(String ip, String port, String dbName, String user, String pwd) {

		try {
			 
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(
					"jdbc:mysql://" + ip + ":" + port + "/" + dbName
							+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public int registrazione(Utente utente) {
		
		String query = "INSERT INTO Utente(username, email, password) VALUES(?,?,SHA2(?) )"; //uso sha2 per  trasformare la stringa della password in un hash SHA-256 prima di memorizzarla
	
		try {
			PreparedStatement ps = conn.prepareStatement(query);			
			
			ps.setString(1, utente.getUsername());
			ps.setString(2, utente.getEmail());
			ps.setString(3, utente.getPassword());
			
			int affectedRows = ps.executeUpdate();
			
			return affectedRows;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	
	}	
	
	public int login(String email, String password) {
		
		String query = "SELECT email, password FROM Utente WHERE email='"+ email + "' and password='"+ password +"'"; 
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			
			int result = 0;
			
			if (rset.next()) {
				
				if (email.equals(rset.getString(1)) && password.equals(rset.getString(2))) 
					//confronto la password ricevuta con quella del database (entrambe codificate con SHA-256)
					result = 1;
	
			}
			
			rset.close();
			stmt.close();
			
			return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			
			return -1;
		}
	}
	
	// TODO DA MODIFICARE NELLA SERVLET LOGIN!!
	public String verificaUtente(String email) {
		
		String query = "SELECT email FROM Utente WHERE username='"+ email + "'";
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet resset = stmt.executeQuery(query);
			
			String result = "";
			if (resset.next()) {
			    result = resset.getString("username");
			}
			
			resset.close();
			stmt.close();
			
			return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	//forse sti metodi non servono
	public int lastIdUtente() {
		String query = "SELECT max(idUtente) FROM Utente";
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			
			int res = -1;
			
			if (rset.next()) 
				res = rset.getInt(1);
		
			rset.close();
			stmt.close();

			return res;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public void closeConnection() {
	    try {
	        conn.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }

	    try {
	        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
	        while (driverEnumeration.hasMoreElements()) {
	        	// per assicurarsi che tutti i driver JDBC collegati siano deregistrati correttamente
	            Driver driver = driverEnumeration.nextElement();
	            DriverManager.deregisterDriver(driver);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}

	
	
	
}
