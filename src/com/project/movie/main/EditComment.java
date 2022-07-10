package com.project.movie.main;

import com.project.movie.dto.CommentDTO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/main/editcomment.do")
public class EditComment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //1. 인코딩
        req.setCharacterEncoding("UTF-8");

        //2. ajax 로 보낸 데이터 받기
        String seq = req.getParameter("seq");
        String content = req.getParameter("content");

        //데이터 처리하기

        //포장하기
        CommentDTO dto = new CommentDTO();

        dto.setSeq(seq);
        dto.setContent(content);

        MainDAO dao = new MainDAO();

        int result = dao.editComment(dto);

        //넘기기

        //인코딩
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        /*{"result" : %d}*/
        writer.printf("{\"result\" : %d}", result);

        writer.close();
    }
}
