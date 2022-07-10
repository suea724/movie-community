package com.project.movie.member;

import com.project.movie.dto.MemberDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/member/mypage/updatetel.do")
public class UpdateTel extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("auth");

        String id = dto.getId();
        String tel = req.getParameter("tel");

        MemberDAO dao = new MemberDAO();
        int result = dao.updateTel(tel, id);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        if (result == 1) { // 전화번호 변경 성공 시

            dto.setTel(tel);
            session.setAttribute("auth", dto);

            writer.printf("{\"tel\" : \"%s\"}", tel);
            writer.close();
        } else { // 변경 실패 시
            writer.printf("{\"tel\" : \"%s\"}", null);
            writer.close();
        }
    }
}
