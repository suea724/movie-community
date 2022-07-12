package com.project.movie.group.recruit;


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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/recruit/addcomment.do")
public class AddComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/recruit/addcomment.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        req.setCharacterEncoding("UTF-8");

        MemberDTO dtoId = (MemberDTO)session.getAttribute("auth");
        String id = dtoId.getId();
        String content = req.getParameter("content");
        String rseq = req.getParameter("rseq");


        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("content", content);
        map.put("rseq", rseq);


        RecruitDAO dao = new RecruitDAO();

        int result = dao.AddComment(map);

        RecruitCommentDTO temp = dao.GetComment();

        System.out.println("AddComment.java의 temp: " + temp);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        writer.print("{");
        writer.printf("\"result\": %d,", 		result);
        writer.printf("\"seq\": %s,", 			temp.getSeq());
        writer.printf("\"id\": \"%s\",", 		temp.getId());
        writer.printf("\"nickname\": \"%s\",", 	temp.getNickname());
        writer.printf("\"regdate\": \"%s\",", 	temp.getRegdate());
        writer.printf("\"rseq\": %s", 			temp.getRseq());
        writer.print("}");

        writer.close();








    }
}



