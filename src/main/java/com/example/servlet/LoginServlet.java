package com.example.servlet;

import com.example.Users;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet
public class LoginServlet extends HttpServlet {
    //write your code here!

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
        HttpSession session = request.getSession();
        RequestDispatcher requestDispatcher;
        String user = (String) session.getAttribute("user");

        if (user != null) {
            requestDispatcher = request.getRequestDispatcher("/login.jsp");
        } else {
            requestDispatcher = request.getRequestDispatcher("/user/hello.jsp");


        }
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp");
        String name = request.getParameter("login");
        String password = request.getParameter("password");
        RequestDispatcher requestDispatcher;
        requestDispatcher = request.getRequestDispatcher("/login.jsp");
        if (name != null && password != null && password.trim().length() > 0) {
            Users users = Users.getInstance();
            Optional<String> stringOptional = users.getUsers().stream().filter(s -> s.equals(name)).findAny();
            request.getSession().setAttribute("user", name);
            if (stringOptional.isPresent()) {
//                response.sendRedirect(request.getContextPath() + "/user/hello");
                requestDispatcher = request.getRequestDispatcher("/user/hello.jsp");
            }
        }
//        response.sendRedirect(request.getContextPath() +"/user/hello.jsp");
        requestDispatcher.forward(request, response);

    }
}
