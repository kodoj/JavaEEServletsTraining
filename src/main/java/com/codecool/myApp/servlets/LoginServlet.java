package com.codecool.myApp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    protected void doGet( HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        response.getWriter().write("<html><body><form name=\"loginForm\" method=\"post\">\n" +
                "    Username: <input type=\"text\" name=\"username\"/> <br/>\n" +
                "    Password: <input type=\"password\" name=\"password\"/> <br/>\n" +
                "    <input type=\"submit\" value=\"Login\" />\n" +
                "</form></body></html>");
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.toUpperCase().equals(password.toUpperCase())) {
            response.sendRedirect("/web");
        }

    }
}
