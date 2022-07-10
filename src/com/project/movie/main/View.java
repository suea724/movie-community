package com.project.movie.main;

import com.project.movie.dto.CommentDTO;
import com.project.movie.dto.MemberDTO;
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

@WebServlet("/main/view.do")
public class View extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        //1. seq 받아오기
        String seq = req.getParameter("seq");
        MemberDTO memberDto = (MemberDTO) session.getAttribute("auth");
        //System.out.println(memberDto.getId());
        if(memberDto == null) {
            memberDto = new MemberDTO();
            memberDto.setId("");
        }
        //2. 데이터 처리하기
        MainDAO dao = new MainDAO();

        //조회수
        dao.updateReadcount(seq);

        //포장하기
        PostDTO tempdto = new PostDTO();
        tempdto.setSeq(seq);
        tempdto.setId(memberDto.getId());

        PostDTO dto = dao.getView(tempdto);

        //해시태그 가져오기
        ArrayList<String> list = new ArrayList<>();
        list = dao.getHashTag(seq);

        System.out.println(list);

        //- 태그 비활성화
        dto.setTitle(dto.getTitle().replace("<", "&lt;").replace(">", "&gt;"));
        dto.setContent(dto.getContent().replace("<", "&lt;").replace(">", "&gt;"));

        // 댓글 목록 가져오기
        ArrayList<CommentDTO> clist = dao.listComment(seq);

        //System.out.println(clist);

        for(CommentDTO cdto : clist) {
            cdto.setContent(cdto.getContent().replace("\r\n", "<br>"));
        }


        //- 출력 데이터 조작하기
        dto.setContent(dto.getContent().replace("\r\n", "<br>"));

        req.setAttribute("dto", dto);
        req.setAttribute("list", list);
        req.setAttribute("clist", clist);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/main/view.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
