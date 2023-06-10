package it.uniroma2.dndlk.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import it.uniroma2.dndlk.dao.UtenteDAO;
import it.uniroma2.dndlk.dao.UtenteDAOJDBCImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UtenteDAO dao;

	public LoginServlet() {
		super(); 
	}

	@Override
	public void init() throws ServletException {
		String ip = getServletContext().getInitParameter("ip");
		String port = getServletContext().getInitParameter("port");
		String dbName = getServletContext().getInitParameter("dbName");
		String userName = getServletContext().getInitParameter("userName");
		String password = getServletContext().getInitParameter("password");

		System.out.print("LoginServlet. Opening DB connection...");

		dao = new UtenteDAOJDBCImpl(ip, port, dbName, userName, password);

		System.out.println("DONE.");
	}

	@Override
	public void destroy() {
		System.out.print("LoginServlet. Closing DB connection...");
		dao.closeConnection();
		System.out.println("DONE.");
	}	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	// Login utente
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("LoginServlet invoking doGet method.");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("LoginServlet invoking doPost method.");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		JSONObject resJsonObject = new JSONObject();
		PrintWriter out = response.getWriter();
		
		String email = request.getParameter("email"), password = request.getParameter("password");
		
		
		if(email == null || password == null) {
			response.setStatus(400);
			try {
				resJsonObject.put("result","Occorre specificare email e password!");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			}finally {
				out.close();
			}
			return;
		}
		
		if (email.equals("") || password.equals("")) {
			response.setStatus(400);
			try {
				resJsonObject.put("result","Errore: campi vuoti");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			}finally {
				out.close();
			}
			return;
		}
		
		
		password = DigestUtils.sha256Hex(password);
		email = dao.verificaUtente(email);
		
		if (email == null || email == "") {
			response.setStatus(404);
			try {
				resJsonObject.put("result","L'utente non è registrato nel database!");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {
				e.printStackTrace();
			}finally {
				out.close();
			}
			return;
		}

		int res = dao.login(email, password);
		
		if (res > 0 ) {
			response.setStatus(200);
			try {
				resJsonObject.put("result", "Login effettuato con successo!");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			}finally {
				out.close();
			}
		}else{
			response.setStatus(500);
			
			try {
				resJsonObject.put("result", "Login non effettuato, email e password sbagliate!");
				out.print(resJsonObject.toString());
				out.flush();
			} catch (JSONException e) {

				e.printStackTrace();
			}finally {
				out.close();
			}
		}
		
	}

}
