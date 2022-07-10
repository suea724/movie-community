package com.project.movie.group.recruit;


import com.project.movie.dto.GroupRequestDTO;
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
import java.util.HashMap;

@WebServlet("/recruit/request.do")
public class Request extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //HttpSession session = req.getSession();


        //글번호
        String seq = req.getParameter("seq");
        //아이디
        String id = req.getParameter("id");
        //그룹번호
        String gseq = req.getParameter("gseq");

        RecruitDAO dao = new RecruitDAO();

        HashMap<String,String> map = new HashMap<String,String>();
        map.put("id", id);
        map.put("gseq", gseq);


        String apply = dao.checkApply(map);



        //temp가 y(이미신청)면 request X n이면 request O

        int result = 0;

        if (apply.equals("n")) {


            result = dao.request(map);

        }






        //4.
        req.setAttribute("result", result);

        if (result == 1) {
            resp.sendRedirect(String.format("/movie/recruit/view.do?seq=%s", seq));
        } else {

            resp.setContentType("text/html;charset=UTF-8");

            PrintWriter writer = resp.getWriter();



            writer.println("<html>");
            writer.println("<body>");
            writer.println("<script>");
            writer.println("alert('이미 신청하셨습니다.');");
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

        //아이디
        MemberDTO mdto = (MemberDTO) session.getAttribute("auth");


        //글번호
        String seq = req.getParameter("seq");

        RecruitDAO dao = new RecruitDAO();

        //그룹번호
        RecruitmentPostDTO dto = dao.get(seq);





        req.setAttribute("dto", dto);

        req.setAttribute("mdto", mdto);

        req.setAttribute("seq", seq);



        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/recruit/request.jsp");
        dispatcher.forward(req, resp);
    }


}



