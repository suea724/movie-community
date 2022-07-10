package com.project.movie.member;

import com.project.movie.dto.LoginDTO;
import com.project.movie.dto.MemberDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/member/login.do")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        LoginDTO dto = new LoginDTO();
        dto.setId(id);
        dto.setPw(pw);

        MemberDAO dao = new MemberDAO();
        MemberDTO mdto = dao.login(dto);

        if (mdto != null) { // 로그인 성공

            int postsCnt = dao.getPostsCount(mdto);
            int commentCnt = dao.getCommentCount(mdto);
            int grade = (postsCnt + commentCnt) / 10;

            mdto.setPostCnt(postsCnt);
            mdto.setCommentCnt(commentCnt);

            mdto.setGrade(grade);

            HttpSession session = req.getSession();
            session.setAttribute("auth", mdto);

            resp.sendRedirect("/movie/index.do");

        } else { // 로그인 실패
            req.setAttribute("loginError", "y");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/login.jsp");
            dispatcher.forward(req, resp);
        }

    }
}