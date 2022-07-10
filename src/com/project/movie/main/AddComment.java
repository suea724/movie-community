package com.project.movie.main;

import com.project.movie.dto.CommentDTO;
import com.project.movie.dto.MemberDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/main/addcomment.do")
public class AddComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        //인코딩
        req.setCharacterEncoding("UTF-8");

        //json으로 데이터 넘긴 거 받기기
        String comment = req.getParameter("comment"); //댓글 내용
        String pseq = req.getParameter("pseq"); //글번호

        //처리하기 전 포장하기
        CommentDTO dto = new CommentDTO();
        dto.setContent(comment);
        dto.setPseq(pseq);

        //아이디도 포장하기
        MemberDTO memberDto = (MemberDTO) session.getAttribute("auth");
        dto.setId(memberDto.getId());

        MainDAO dao = new MainDAO();

        int result = dao.addComment(dto);

        CommentDTO temp = dao.getComment();

        System.out.println("temp = " + temp);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        writer.print("{");
        writer.printf("\"result\": %d,", result);
        writer.printf("\"seq\": %s,", temp.getSeq());
        writer.printf("\"id\": \"%s\",", temp.getId());
        writer.printf("\"nickname\": \"%s\",", temp.getNickname());
        writer.printf("\"regdate\": \"%s\",", temp.getRegdate());
        writer.printf("\"content\": \"%s\",", temp.getContent());
        writer.printf("\"pseq\": %s", temp.getPseq());
        writer.print("}");
        writer.close();

        /*
        {
            "result": %d,
            "seq": %s,
            "id": %s,
            "nickname": %s,
            "regdate": %s,
            "pseq": $s
        }
        */
    }
}


