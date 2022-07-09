package com.project.movie.member;

import com.project.movie.dto.MemberDTO;
import com.project.movie.main.MainList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/member/unregister.do")
public class Unregister extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/unregister.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String name = req.getParameter("name");
        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO)session.getAttribute("auth");

        if (dto.getId().equals(id) && dto.getPassword().equals(pw) && dto.getName().equals(name)) {

            MemberDAO dao = new MemberDAO();

            int result = dao.del(id);

            session.invalidate();

            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/html");

            PrintWriter writer = resp.getWriter();

            if (result == 1) {
                writer.println("<html>");
                writer.println("<body>");
                writer.println("<script>");
                writer.println("alert('탈퇴가 완료되었습니다.');");
                writer.println("location.href='/movie/index.do'");
                writer.println("</script>");
                writer.println("</body>");
                writer.println("</html>");
            } else {
                writer.println("<html>");
                writer.println("<body>");
                writer.println("<script>");
                writer.println("alert('탈퇴 실패');");
                writer.println("history.back();");
                writer.println("</script>");
                writer.println("</body>");
                writer.println("</html>");
            }

        } else {
            req.setAttribute("error", "회원정보가 일치하지 않습니다.");

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/unregister.jsp");
            dispatcher.forward(req, resp);
        }
    }
}

