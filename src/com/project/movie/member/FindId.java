package com.project.movie.member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/member/findid.do")
public class FindId extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/findid.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String tel = req.getParameter("tel");

        MemberDAO dao = new MemberDAO();
        String id = dao.findId(name, tel);

        if (id == null) {
            req.setAttribute("noMember", "y");
        } else {
            req.setAttribute("id", id);
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/findid.jsp");
        dispatcher.forward(req, resp);
    }
}
