package com.project.movie.group.recruit;


import com.project.movie.dto.GroupRequestDTO;
import com.project.movie.dto.RecruitmentPostDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/recruit/view.do")
public class View extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession();

        String seq = req.getParameter("seq");

        RecruitDAO dao = new RecruitDAO();

        //조회수 증가
        if (session.getAttribute("read") == null || session.getAttribute("read").toString().equals("n")) {
            dao.updateReadcount(seq);
            session.setAttribute("read", "y");
        }


        RecruitmentPostDTO dto = dao.get(seq);

        //- 태그 비활성화
        dto.setTitle(dto.getTitle().replace("<", "&lt;").replace(">", "&gt;"));
        dto.setContent(dto.getContent().replace("<", "&lt;").replace(">", "&gt;"));

        //- 출력 데이터 조작하기
        dto.setContent(dto.getContent().replace("\r\n", "<br>"));


        req.setAttribute("dto", dto);







        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/recruit/view.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}



