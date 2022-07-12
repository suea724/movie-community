package com.project.movie.group.recruit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/recruit/editcomment.do")
public class EditComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/editcomment.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String seq = req.getParameter("seq");
        String content = req.getParameter("content");

        RecruitDAO dao = new RecruitDAO();
        int result = dao.EditComment(seq, content);

        System.out.println("edit.java의 result:" + result);

        //5.
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        writer.printf("{ \"result\": %d }", result);

        writer.close();


    }
}