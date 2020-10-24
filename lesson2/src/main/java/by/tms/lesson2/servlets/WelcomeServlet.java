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

@WebServlet(name = "WelcomeServlet", urlPatterns = "/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
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

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setCharacterEncoding("UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            User user = UserBase.getUser(req.getParameter("username"));

            out.write("<html>\n" +
                    "<body>\n" +
                    "<h1 align=\"center\">Hello, " + user.getName() + "!</h1>\n" +
                    "</body>\n" +
                    "</html>");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
