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
import java.util.ArrayList;

@WebServlet("/member/mypage.do")
public class Mypage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("auth");

        MemberDAO dao = new MemberDAO();

        ArrayList<String> tags = dao.getHashTagsById(dto.getId());
        String tagsValue = "";

        for (int i = 0 ; i < tags.size() ; i ++) {

            if (i == tags.size() - 1) {
                tagsValue += tags.get(i);
            } else {
                tagsValue += tags.get(i) + ",";
            }
        }

        req.setAttribute("tagsValue", tagsValue);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/mypage.jsp");
        dispatcher.forward(req, resp);
    }

}
