package com.project.movie.member;

import com.project.movie.dto.MemberDTO;
import com.project.movie.dto.MyCommentsDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet("/member/mypage/comments.do")
public class MyComments extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        MemberDTO dto = (MemberDTO) session.getAttribute("auth");

        int page = 1;

        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        MemberDAO dao = new MemberDAO();

        Pagination pagination = new Pagination(page, dao.getTotalComments(dto.getId()), 20, 10);
        ArrayList<MyCommentsDTO> list = dao.getMyComments(dto.getId(), page);
        setList(list);

        req.setAttribute("pagination", pagination);
        req.setAttribute("list", list);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/comments.jsp");
        dispatcher.forward(req, resp);
    }

    private ArrayList<MyCommentsDTO> setList(ArrayList<MyCommentsDTO> list) {

        LocalDate today = LocalDate.now();

        for (MyCommentsDTO dto : list) {

            // 오늘 작성된 글일 경우 시간 출력, 오늘 이전에 작성된 글일 경우 날짜 출력
            if (dto.getRegdate().substring(0, 10).equals(today.toString())) {
                dto.setRegdate(dto.getRegdate().substring(11));
            } else {
                dto.setRegdate(dto.getRegdate().substring(0, 10));
            }

            // 제목의 길이가 15글자가 넘어가면 말줄임 표시
            if (dto.getTitle().length() > 15) {
                dto.setTitle(dto.getTitle().substring(0, 15) + "...");
            }

            // 댓글 길이가 10글자가 넘어가면 말줄임 표시
            if (dto.getContent().length() > 10) {
                dto.setContent(dto.getContent().substring(0, 10) + "...");
            }

            // type 문자열로 설정 (1: 리뷰, 2: 자유, 3 : 그룹)
            if (dto.getType().equals("1")) {
                dto.setType("리뷰");
            } else if (dto.getType().equals("2")) {
                dto.setType("자유");
            } else {
                dto.setType("그룹");
            }
        }
        return list;
    }

}