package com.project.movie.group;

import com.project.movie.dto.GroupDTO;
import com.project.movie.dto.PostDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/group/add.do")
public class Add extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        GroupDAO dao = new GroupDAO();

        List<String> taglist = dao.taglist();

        req.setAttribute("taglist", taglist);


        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/add.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String group = req.getParameter("group");

        PostDTO dto = new PostDTO();
        dto.setId((String)session.getAttribute("auth"));
        dto.setTitle(title);
        dto.setType("3");
        dto.setContent(content);

        GroupDAO dao = new GroupDAO();

        int result = 0;
        if (session.getAttribute("auth") != null) {
            result = dao.add(dto);
        }





    }
}
