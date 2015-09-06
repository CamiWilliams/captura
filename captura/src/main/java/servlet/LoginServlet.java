package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import camiwilliams.captura.Authenticator;
import camiwilliams.captura.objects.LanguageDictionary;
import camiwilliams.captura.objects.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
    
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String startingLang = request.getParameter("initLang");
		
		Authenticator authenticator = new Authenticator();
		RequestDispatcher rd = null;

		//Login
		if(startingLang == null) {
			String result = authenticator.authenticate(username, password);
			if(result.equals("success")) {
				authenticator.setCurrentUser(username);
				rd = request.getRequestDispatcher("/home.html");
				//request.setAttribute("user", user.getUsername());
			} else {
				rd = request.getRequestDispatcher("/login_error.html");
			}
		} else { //New User
			String result = authenticator.newUser(name, email, username, password, startingLang);
			if(result.equals("success")) {
				authenticator.setCurrentUser(username);
				User user = authenticator.getCurrentUser();
				LanguageDictionary startingDict = new LanguageDictionary(startingLang);
				user.addDictionary(startingDict);
				rd = request.getRequestDispatcher("/home.html");
				//request.setAttribute("user", user.getUsername());
			} else {
				rd = request.getRequestDispatcher("/login_error.html");
			}
		}
		
		rd.forward(request, response);
	}
}
