package com.project.movie.group;

import com.project.movie.dto.MemberDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/group/groupgoodbad.do")
public class GroupGoodBad extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/groupgoodbad.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        MemberDTO dtoId = (MemberDTO)session.getAttribute("auth");

        String seq = req.getParameter("seq");
        String goodbad = req.getParameter("goodbad");

        GroupDAO dao = new GroupDAO();

        dao.deleteGoodBad(seq, dtoId.getId());

        int result = 0;

        if (goodbad.equals("0")) {
            result = dao.goodCount(seq, dtoId.getId());
        } else {
            result = dao.badCount(seq, dtoId.getId());
        }

        List<Integer> glist = dao.getGoodBad(seq);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        writer.print("{");
        writer.printf("\"result\": %d,", result);
        writer.printf("\"good\": %d,", glist.get(0));
        writer.printf("\"bad\": %d", glist.get(1));
        writer.print("}");

        writer.close();



    }
}
