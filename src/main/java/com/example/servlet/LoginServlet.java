package com.example.servlet;

import com.example.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@WebServlet
public class LoginServlet extends HttpServlet {
    //write your code here!

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request != null && response != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String user = Optional.ofNullable((String) session.getAttribute("user")).orElse("");
                if (user.isEmpty()) {
                    ServletContext servletContext = getServletContext();
                    InputStream resourceStream1 = servletContext.getResourceAsStream("/login.jsp");
                    InputStream resourceStream2 = servletContext.getResourceAsStream("/user/hello.jsp");
                    if (resourceStream1 != null && resourceStream2 != null) {
                        request.getRequestDispatcher("/login.jsp").forward(request, response);
                    } else {
                        request.getRequestDispatcher("/user/hello.jsp").forward(request, response);
                    }
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request != null && response != null) {
            request.getRequestDispatcher("/login.jsp");
            String name = request.getParameter("login");
            String password = request.getParameter("password");
            RequestDispatcher requestDispatcher;
            ServletContext servletContext = getServletContext();
            InputStream resourceStream1 = servletContext.getResourceAsStream("/login.jsp");
            InputStream resourceStream2 = servletContext.getResourceAsStream("/user/hello.jsp");
            if (resourceStream1 != null && resourceStream2 != null) {
                requestDispatcher = request.getRequestDispatcher("/login.jsp");
                if (name != null && password != null && password.length() > 0) {
                    Users users = Users.getInstance();
                    Optional<String> stringOptional = users.getUsers().stream().filter(s -> s.equals(name)).findAny();
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.setAttribute("user", name);
                        if (stringOptional.isPresent()) {
//                response.sendRedirect(request.getContextPath() + "/user/hello");
                            requestDispatcher = request.getRequestDispatcher("/user/hello.jsp");
                        }
                    }
                }
//        response.sendRedirect(request.getContextPath() +"/user/hello.jsp");
                requestDispatcher.forward(request, response);

            }
        }
    }
}
