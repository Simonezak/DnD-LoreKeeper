package it.uniroma2.dndlk.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.uniroma2.dndlk.entity.Campagna;


public class CampagneDAOJDBCImpl implements CampagneDAO {
	
	private Connection conn;

	public CampagneDAOJDBCImpl(String ip, String port, String dbName, String username, String pwd) {
		try { 

			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + dbName
					+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					username, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// carica le campagne sul menu
	public JSONArray loadCampagneByUsername(String username) {

		String query = "SELECT idCampagna, nomeCampagna, descrizione, numMaxGiocatori FROM Campagna,Partecipa,Utente WHERE Campagna.idCampagna = Partecipa.idCampagna AND Partecipa.idUtente = Utente.idUtente AND Utente.username ='" + username + "';";

		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);

			JSONArray arrCampagne = new JSONArray();
			JSONObject campagna = new JSONObject();
			
			while (rset.next()) {
				campagna = new JSONObject();
				
				campagna.put("idCampagna", rset.getString(2));
				campagna.put("nomeCampagna", rset.getString(2));
				campagna.put("descrizione", rset.getString(3));
				campagna.put("numMaxGiocatori", rset.getInt(4));
				
				arrCampagne.put(campagna);
					
			}
		
			rset.close();
			stmt.close();

			return arrCampagne;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	public Campagna loadCampagna(String nomeCampaign){
		String query = "SELECT idCampagna, nomeCampagna, descrizione, numMaxGiocatori FROM Campagna WHERE nomeCampagna='" +nomeCampaign + "'";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);

			Campagna res = null;

			if (rset.next()) {
				int idCampagna = rset.getInt(1);
				String nomeCampagna = rset.getString(2);
				String descrizione = rset.getString(3);
				int numMaxGiocatori = rset.getInt(4);

				res = new Campagna(idCampagna, nomeCampagna, descrizione, numMaxGiocatori);

			}

			rset.close();
			stmt.close();

			return res;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public int deleteUtenteDaCampagna(int idCampagna, int idUtente) {
		
		String query = " DELETE FROM Partecipa WHERE idCampagna ="+ idCampagna +"AND idUtente ="+ idUtente +";";

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
	
	public int aggiungiUtenteACampagna(Campagna campagna, int idUtente) {
		
		String query = "INSERT INTO Partecipa (idCampagna, idUtente, dungeonMaster) VALUES (?, ?, ?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setInt(1, campagna.getIdCampagna());
			ps.setInt(2, idUtente);
			ps.setInt(4, 0);
			
			int affectedRows = ps.executeUpdate();
			
			return affectedRows;
			
		} catch (SQLException e) {
			e.printStackTrace();

			return -1;
		}
		
		
	}
	
	// inserire nuova campagna
	public int creaCampagna(Campagna campagna, int idUtente) {
		String query1 = "INSERT INTO Campagna (idCampagna, nomeCampagna, descrizione, numMaxGiocatori) VALUES (?, ?, ?, ?);";

		String query2 = "INSERT INTO Partecipa (idCampagna, idUtente, dungeonMaster) VALUES (?, ?, ?);";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query1);
			
			ps.setInt(1, campagna.getIdCampagna());
			ps.setString(2, campagna.getNomeCampagna());
			ps.setString(3, campagna.getDescrizione());
			ps.setInt(4, campagna.getNumMaxGiocatori());
			
			int affectedRows = ps.executeUpdate();
			
			System.out.println("affected rows in query1: " + affectedRows);
			
			PreparedStatement ps2 = conn.prepareStatement(query2);

			ps2.setInt(1, campagna.getIdCampagna());
			ps2.setInt(2, idUtente);
			ps2.setInt(4, 1);
			
			int affectedRows2 = ps2.executeUpdate();
			
			return affectedRows2;

		} catch (SQLException e) {
			e.printStackTrace();

			return -1;
		}
		
		
	}
	

	public int lastIdCampagna() {
		String query = "SELECT max(idCampagna) FROM Campagna";
		
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
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			Enumeration<Driver> enumDrivers = DriverManager.getDrivers();
			while (enumDrivers.hasMoreElements()) {
				Driver driver = enumDrivers.nextElement();
				DriverManager.deregisterDriver(driver);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
