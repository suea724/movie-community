package com.project.movie.member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/member/findpw.do")
public class FindPw extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/findpw.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String tel = req.getParameter("tel");

        MemberDAO dao = new MemberDAO();
        int result = dao.findMemberByPw(id, name, tel);

        if (result == 1) { // 회원 정보가 존재할 때

            // req.setAttribute("result", result);
            req.setAttribute("id", id);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/updatepw.jsp");
            dispatcher.forward(req, resp);

        } else { // 회원 정보가 존재하지 않을 때

            req.setAttribute("noMember", "y");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/findpw.jsp");
            dispatcher.forward(req, resp);
        }

    }
}
