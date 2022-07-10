package com.project.movie.group;

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
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/group/groupview.do")
public class GroupView extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String group = req.getParameter("group");
        String seq = req.getParameter("seq");

        HttpSession session = req.getSession();

        if (session.getAttribute("auth") == null) {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();

            writer.println("<html>");
            writer.println("<body>");
            writer.println("<script>");
            writer.println("alert('접근 권한이 없습니다.');");
            writer.println("history.back();");
            writer.println("</script>");
            writer.println("</body>");
            writer.println("</html>");

            writer.close();
            return;
        }
        MemberDTO dtoId = (MemberDTO) session.getAttribute("auth");
        String id = dtoId.getId();
        GroupDAO dao = new GroupDAO();
        int check = dao.idCheck(group, id);


        if (check == 0) {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = resp.getWriter();

            writer.println("<html>");
            writer.println("<body>");
            writer.println("<script>");
            writer.println("alert('접근 권한이 없습니다.');");
            writer.println("history.back();");
            writer.println("</script>");
            writer.println("</body>");
            writer.println("</html>");

            writer.close();
            return;
        }
        
        //조회수 증가
        if (session.getAttribute("read") == null || session.getAttribute("read").toString().equals("n")) {
            dao.updateReadcount(seq);
            session.setAttribute("read", "y");
        }

        PostDTO dto = dao.GroupView(seq);

        List<String> list = dao.GroupViewTag(seq);

        //- 태그 비활성화
        dto.setTitle(dto.getTitle().replace("<", "&lt;").replace(">", "&gt;"));
        dto.setContent(dto.getContent().replace("<", "&lt;").replace(">", "&gt;"));

        //- 출력 데이터 조작하기
        dto.setContent(dto.getContent().replace("\r\n", "<br>"));

        List<CommentDTO> clist = dao.getComment(seq);
        for (CommentDTO cd : clist) {
            cd.setContent(cd.getContent().replace("\r\n", "<br>"));
        }

        List<Integer> glist = dao.getGoodBad(seq);

        String groupId = dao.getGroupId(group);

        req.setAttribute("groupId", groupId);
        req.setAttribute("glist", glist);
        req.setAttribute("dto", dto);
        req.setAttribute("clist", clist);
        req.setAttribute("list", list);
        req.setAttribute("group", group);


        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/groupview.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
}