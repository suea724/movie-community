package com.project.movie.group.recruit;

import com.project.movie.dto.MemberDTO;
import com.project.movie.dto.RecruitmentPostDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/recruit/add.do")
public class Add  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        HttpSession session = req.getSession();

        //1. 인코딩
        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String gseq = req.getParameter("gseq");


        //3. 데이터 처리하기
        RecruitmentPostDTO dto = new RecruitmentPostDTO();

        dto.setTitle(title);
        dto.setContent(content);
        dto.setGseq(gseq);

        MemberDTO mdto = (MemberDTO) session.getAttribute("auth");
        dto.setId(mdto.getId());


        RecruitDAO dao = new RecruitDAO();

        int result = 0;

        if (session.getAttribute("auth") != null) {
            result = dao.add(dto);
        }

        //4.
        req.setAttribute("result", result);


        if (result == 1) {
            resp.sendRedirect("/movie/recruit/recruitlist.do");
        } else {

            PrintWriter writer = resp.getWriter();

            writer.println("<html>");
            writer.println("<body>");
            writer.println("<script>");
            writer.println("alert('failed');");
            writer.println("history.back();");
            writer.println("</script>");
            writer.println("</body>");
            writer.println("</html>");

            writer.close();

        }





    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        HttpSession session = req.getSession();


        RecruitmentPostDTO dto = new RecruitmentPostDTO();
        MemberDTO mdto = (MemberDTO) session.getAttribute("auth");


        RecruitDAO dao = new RecruitDAO();

        //사용자가 그룹장인 그룹 리스트 가져오기

        ArrayList<GroupDTO> glist = dao.grouplist(mdto.getId());


        req.setAttribute("glist", glist);




        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/recruit/add.jsp");
        dispatcher.forward(req, resp);
    }




}




