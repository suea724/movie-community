package com.project.movie.group.mygroup;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/group/mygroup/groupnamecheck.do")
public class GroupNameCheck extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String gname = request.getParameter("gname");

        //System.out.println(gname);

        MyGroupDAO dao = new MyGroupDAO();

        String result = dao.checkGroupName(gname);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();

        writer.print("{");
        writer.printf("\"result\":\"%s\"",result);
        writer.print("}");

        writer.close();

        /*RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/group/mygroup/mygrouplist.jsp");
        dispatcher.forward(request, response);*/
    }

    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }*/
}
