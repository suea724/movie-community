package com.project.movie.group.recruit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/recruit/delcomment.do")
public class DelComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/delcomment.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String seq = req.getParameter("seq");
        RecruitDAO dao = new RecruitDAO();

        int result = dao.DelComment(seq);

        //5.
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        writer.printf("{ \"result\": %d }", result);

        writer.close();

    }
}