package it.uniroma2.dndlk.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.uniroma2.dndlk.entity.Campagna;
import it.uniroma2.dndlk.dao.CampagneDAOJDBCImpl;
import it.uniroma2.dndlk.dao.CampagneDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@WebServlet("/CampagneServlet")
public class CampagneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CampagneDAO dao;
	
	@Override
	public void init() throws ServletException {
		String ip = getServletContext().getInitParameter("ip");
		String port = getServletContext().getInitParameter("port");
		String dbName = getServletContext().getInitParameter("dbName");
		String userName = getServletContext().getInitParameter("userName");
		String password = getServletContext().getInitParameter("password");

		System.out.print("CampagneServlet. Opening DB connection...");

		dao = new CampagneDAOJDBCImpl(ip, port, dbName, userName, password);

		System.out.println("DONE.");
	}

	@Override
	public void destroy() {
		System.out.print("CampagneServlet. Closing DB connection...");
		dao.closeConnection();
		System.out.println("DONE.");
	}

	//restituisce i gruppi di un utente
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CampagneServlet. Invoking a doGet method.");

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		JSONObject resJsonObject = new JSONObject();

		String username = request.getParameter("username");

		if (username == null) {
			response.setStatus(400);
			try {
				resJsonObject.put("result", "Errore: specificare un username");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			} finally {
				out.close();
			}
			return;
		}

		if (username.equals("")) {
			response.setStatus(400);
			try {
				resJsonObject.put("result", "Errore: l'username è un campo vuoto");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			} finally {
				out.close();
			}
			return;
		}

		JSONArray arrCampagne = dao.loadCampagneByUsername(username);

		if (arrCampagne != null) {
			response.setStatus(200);
			// Mando in risposta direttamente un jsonarray
			out.print(arrCampagne.toString());
			out.flush();
			out.close();
		} else {
			response.setStatus(500);
			try {
				resJsonObject.put("result", "Errore nel caricamento dei gruppi");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				out.close();
			}
		}

	}


	//crea campagna
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("CampagneServlet. Invoking a doPost method...");
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		JSONObject resJsonObject = new JSONObject();

		int idUtente = Integer.valueOf(request.getParameter("idUtente"));
		int idCampagna = Integer.valueOf(request.getParameter("idCampagna"));
		String nomeCampagna = request.getParameter("nomeCampagna");
		String descrizione = request.getParameter("descrizione");
		int numMaxGiocatori = Integer.valueOf(request.getParameter("numMaxGiocatori"));

		if (nomeCampagna == null || descrizione == null || Integer.toString(numMaxGiocatori) == null
				|| Integer.toString(idCampagna) == null) {
			response.setStatus(400);
			try {
				resJsonObject.put("result", "Errore: fornisci i dati per la creazione della campagna");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			} finally {
				out.close();
			}
			return;
		}

		if (nomeCampagna.equals("") || descrizione.equals("") || Integer.toString(numMaxGiocatori).equals("")
				|| Integer.toString(idCampagna).equals("")) {
			response.setStatus(400);
			try {
				resJsonObject.put("result", "Errore uno o più campi vuoti");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			} finally {
				out.close();
			}
			return;
		}

		if (nomeCampagna.length() > 50 || descrizione.length() > 200 ) {
			response.setStatus(400);
			try {
				resJsonObject.put("result", "Errore: nome della campagna o descrizione troppo lunghe!");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			} finally {
				out.close();
			}
			return;
		}
		
		// Verifico se l'utente che sta creando il StudenGroup è già capo di un altro
		// StudenGroup con lo stesso nome
		Campagna campaignNameCheck = dao.loadCampagna(nomeCampagna);
		
		if (campaignNameCheck != null ) {
			response.setStatus(400);
			try {
				resJsonObject.put("result", "Errore: esiste già una campagna con lo stesso nome");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				out.close();
			}
			return;
		}

		Campagna newCampaign = new Campagna(idCampagna, nomeCampagna, descrizione, numMaxGiocatori);
		//inserisco la nuova campagna
		int res = dao.creaCampagna(newCampaign,idUtente);

		if (res > 0) {
			response.setStatus(200);
			try {
				resJsonObject.put("result", "Gruppo creato");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			} finally {
				out.close();
			}
		} else {
			response.setStatus(500);

			try {
				resJsonObject.put("result", "Errore nella creazione del gruppo");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				out.close();
			}
		}

	}

}
