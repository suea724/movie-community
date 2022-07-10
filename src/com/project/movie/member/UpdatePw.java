package com.project.movie.member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/member/updatepw.do")
public class UpdatePw extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        req.setAttribute("id", id);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/updatepw.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String pw = req.getParameter("pw");
        String pwCheck = req.getParameter("pwCheck");

        System.out.println("id = " + id);
        System.out.println("pw = " + pw);

        if (pw.equals(pwCheck)) {
            MemberDAO dao = new MemberDAO();
            int result = dao.updatePw(id, pw);
            req.setAttribute("result", result);
        } else {
            req.setAttribute("error", "비밀번호가 일치하지 않습니다.");
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/updatepw.jsp");
        dispatcher.forward(req, resp);
    }
}
