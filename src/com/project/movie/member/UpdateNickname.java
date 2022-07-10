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

@WebServlet("/member/mypage/updatenickname.do")
public class UpdateNickname extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("auth");

        String id = dto.getId();
        String nickname = req.getParameter("nickname");

        MemberDAO dao = new MemberDAO();
        int result = dao.updateNickname(nickname, id);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        if (result == 1) { // 닉네임 변경 성공 시

            dto.setNickname(nickname);
            session.setAttribute("auth", dto);

            writer.printf("{\"nickname\" : \"%s\"}", nickname);
            writer.close();
        } else { // 변경 실패 시
            writer.printf("{\"nickname\" : \"%s\"}", null);
            writer.close();
        }

    }
}
