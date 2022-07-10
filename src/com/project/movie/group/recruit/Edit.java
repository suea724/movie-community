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

@WebServlet("/recruit/edit.do")
public class Edit extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession();



        //1. 인코딩
        req.setCharacterEncoding("UTF-8");

        String seq = req.getParameter("seq");

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String gseq = req.getParameter("gseq");


        //3. 데이터 처리하기
        RecruitmentPostDTO dto = new RecruitmentPostDTO();

        dto.setSeq(seq);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setGseq(gseq);

        MemberDTO mdto = (MemberDTO) session.getAttribute("auth");
        dto.setId(mdto.getId());

        System.out.println(dto);

        RecruitDAO dao = new RecruitDAO();

        int result = 0;


        result = dao.edit(dto);

       // int temp = 0;

//        if (session.getAttribute("auth") == null) {
//            temp = 1; //익명 사용자
//        } else if (session.getAttribute("auth") != null) {
//            //temp = 1; //실명 사용자
//
//            if (session.getAttribute("auth").equals(dao.get(seq).getId())) {
//                temp = 2; //글쓴 본인(***)
//            } else {
//
//                if (session.getAttribute("auth").toString().equals("admin")) {
//                    temp = 3; //관리자(***)
//                } else {
//                    temp = 4; //타인
//                }
//
//            }
//
//        }

//        if (temp == 2 || temp == 3) {
//            result = dao.edit(dto);
//        }


        //4.
        req.setAttribute("result", result);
        req.setAttribute("seq", seq);


        if (result == 1) {
            resp.sendRedirect(String.format("/movie/recruit/view.do?seq=%s", seq));
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


        //RecruitmentPostDTO dto = new RecruitmentPostDTO();


        MemberDTO mdto = (MemberDTO) session.getAttribute("auth");


        RecruitDAO dao = new RecruitDAO();

        String seq = req.getParameter("seq");
        RecruitmentPostDTO dto = dao.get(seq);

        System.out.println(dto);

        //사용자가 그룹장인 그룹 리스트 가져오기

        ArrayList<GroupDTO> glist = dao.grouplist(mdto.getId());


        req.setAttribute("glist", glist);

        req.setAttribute("dto", dto);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/recruit/edit.jsp");
        dispatcher.forward(req, resp);



    }
}



