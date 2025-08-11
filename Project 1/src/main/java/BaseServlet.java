
package comA;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comHibernate.*;

@WebServlet("/login")
public class BaseServlet extends HttpServlet {
	
		@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

			String user=req.getParameter("username");
			String pass=req.getParameter("password");
//			
			PrintWriter out = resp.getWriter();
//				out.print(user);
//				out.print(pass);
				
			EntityManager em = CreateConnection.getConnection();
			EntityTransaction et = em.getTransaction();
			
			for(int i=1;i<7;i++) {
				User user1 = em.find(User.class, i);

				if(user1.getUsername().equals(user) && user1.getPassword().equals(pass)) {
					resp.sendRedirect("access-granted.html");
					//out.print("Access Granted");
					System.out.println("Access Granted");
					break;
				}
				
			}
			
			//resp.sendRedirect("access-denied.html");
			
		}
	
}
