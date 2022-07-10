package com.project.movie.group;

import com.project.movie.dto.CommentDTO;
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

@WebServlet("/group/groupaddcomment.do")
public class GroupAddComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/groupaddcomment.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        req.setCharacterEncoding("UTF-8");
        MemberDTO dtoId = (MemberDTO)session.getAttribute("auth");
        String id = dtoId.getId();
        String content = req.getParameter("content");
        String pseq = req.getParameter("pseq");


        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("content", content);
        map.put("pseq", pseq);

        GroupDAO dao = new GroupDAO();

        int result = dao.groupAddComment(map);

        CommentDTO temp = dao.groupGetComment();

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        //writer.printf("{ \"result\": %d }", result);

        writer.print("{");
        writer.printf("\"result\": %d,", 		result);
        writer.printf("\"seq\": %s,", 			temp.getSeq());
        writer.printf("\"id\": \"%s\",", 		temp.getId());
        writer.printf("\"nickname\": \"%s\",", 	temp.getNickname());
        writer.printf("\"regdate\": \"%s\",", 	temp.getRegdate());
        writer.printf("\"pseq\": %s", 			temp.getPseq());
        writer.print("}");

        writer.close();
        
    }
}
