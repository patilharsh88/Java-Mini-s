package comA;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import comHibernate.CreateConnection;
import comHibernate.User;

@WebServlet("/Login2")
public class BaseServlet2 extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        EntityManager em = CreateConnection.getConnection();

        try {
            User foundUser = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                               .setParameter("username", user)
                               .getSingleResult();

            if (foundUser.getPassword().equals(pass)) {
                // Login success: set session and redirect
                HttpSession session = req.getSession();
                session.setAttribute("username", user);
                resp.sendRedirect("access-granted.html");
                return;
            } else {
                // Wrong password
                resp.sendRedirect("access-denied.html");
                return;
            }
        } catch (NoResultException e) {
            // User not found
            resp.sendRedirect("access-denied.html");
            return;
        } finally {
            em.close();
        }
    }
}
