package com.project.movie.member;

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

@WebServlet("/member/mypage/updatepw.do")
public class MypageUpdatePw extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/mypageUpdatePw.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("auth");

        String newPw = req.getParameter("newPw");

        MemberDAO dao = new MemberDAO();
        int result = dao.updatePw(dto.getId(), newPw);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");

        PrintWriter writer = resp.getWriter();

        if (result == 1) {
            writer.println("<html>");
            writer.println("<body>");
            writer.println("<script>");
            writer.println("alert('비밀번호 수정이 완료되었습니다.');");
            writer.println("location.href='/movie/member/mypage.do'");
            writer.println("</script>");
            writer.println("</body>");
            writer.println("</html>");
        } else {
            writer.println("<html>");
            writer.println("<body>");
            writer.println("<script>");
            writer.println("alert('비밀번호 수정 실패');");
            writer.println("history.back();");
            writer.println("</script>");
            writer.println("</body>");
            writer.println("</html>");
        }

    }
}