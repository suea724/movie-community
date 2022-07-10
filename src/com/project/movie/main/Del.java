package com.project.movie.main;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/main/del.do")
public class Del extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //인코딩
        //resp.setCharacterEncoding("UTF-8");
        //resp.setContentType("text/html;charset=UTF-8");

        //seq 번호 가져오기
        String seq = req.getParameter("seq");

        MainDAO dao = new MainDAO();

        //해시태그 삭제
        dao.delHashTag(seq);

        //댓글 삭제
        dao.delComment(seq);

        //글 삭제
        int result = dao.delContent(seq);

        if(result == 1) {
            resp.sendRedirect("/movie/main/mainlist.do");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
