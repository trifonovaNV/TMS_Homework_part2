package by.tms.lesson2.servlets;

import by.tms.lesson2.classes.User;
import by.tms.lesson2.classes.UserBase;
import by.tms.lesson2.xml.ParserXML;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ParserXML.start();
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");

        User user = UserBase.getUser(req.getParameter("username"));
        String password = req.getParameter("password");

        if (user != null) {
            if (password.equals(user.getPassword())) {
                RequestDispatcher dispatcher = req.getRequestDispatcher("/welcome");
                dispatcher.forward(req, resp);
            } else {
                resp.sendRedirect("passwordError.jsp");
            }
        } else {
            resp.sendRedirect("register.jsp");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
