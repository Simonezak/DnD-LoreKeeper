package it.uniroma2.dndlk.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import it.uniroma2.dndlk.dao.UtenteDAO;
import it.uniroma2.dndlk.dao.UtenteDAOJDBCImpl;
import it.uniroma2.dndlk.entity.Utente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtenteDAO dao;
	

    public RegisterServlet() {
    	super();
    }

	@Override
	public void init() throws ServletException {
		String ip = getServletContext().getInitParameter("ip");
		String port = getServletContext().getInitParameter("port");
		String dbName = getServletContext().getInitParameter("dbName");
		String userName = getServletContext().getInitParameter("userName");
		String password = getServletContext().getInitParameter("password");

		System.out.print("RegistrationServlet. Opening DB connection...");

		dao = new UtenteDAOJDBCImpl(ip, port, dbName, userName, password);

		System.out.println("DONE.");
	}

	@Override
	public void destroy() {
		System.out.print("RegistrationServlet. Closing DB connection...");
		dao.closeConnection();
		System.out.println("DONE.");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("RegistrationServlet invoking a doPost method.");

		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		JSONObject resJsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		
		int idUtente = Integer.valueOf(request.getParameter("idUtente"));
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if(Integer.toString(idUtente) == null || username == null || email == null || password == null) {
			response.setStatus(400);
			try {
				resJsonObject.put("result", "Errore: riempire i campi per la registrazione");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {
				e.printStackTrace();
			}finally {
				out.close();
			}
			return;
		}

		if ( Integer.toString(idUtente).equals("") || username.equals("") || email.equals("") || password.equals("") ) {
			response.setStatus(400);
			try {
				resJsonObject.put("result", "Errore: uno o più campi vuoti!");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {
				e.printStackTrace();
			}finally {
				out.close();
			}
			return;
		}
		
		//usiamo la classe InternetAddress dalla libreria javax.mail per controllare se la mail fornita
		
		try {
            InternetAddress internetAddress = new InternetAddress(email);  
            internetAddress.validate();
        } catch (AddressException e) {
        	response.setStatus(400);
        	try {
				resJsonObject.put("result", "Errore: la mail inserita non è valida");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException ex) {
				e.printStackTrace();
			}finally {
				out.close();
			}
        }
		
		if ( password.length()<8 ) {
			response.setStatus(400);
			try {
				resJsonObject.put("result", "Errore: la password inserita è troppo corta");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {
				e.printStackTrace();
			}finally {
				out.close();
			}
			return;
		}
		
	
		Utente user = new Utente(idUtente,username, email, password);
		int res = dao.registrazione(user);

		if (res > 0) {
			response.setStatus(200);
			try {
				resJsonObject.put("result", "Registrazione effettuata con successo!");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {
				e.printStackTrace();
			}finally {
				out.close();
			}
		} else {
			response.setStatus(500);
			try {
				resJsonObject.put("result", "Errore: utente già esistente!");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			}finally {
				out.close();
			}
		}

	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("RegistrationServlet invoking a doGet method.");
	}

}
