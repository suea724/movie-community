package com.project.movie.group;

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

@WebServlet("/group/groupdel.do")
public class GroupDel extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String seq = req.getParameter("seq");
        String group = req.getParameter("group");

        GroupDAO dao = new GroupDAO();

        String id = dao.getId(seq);

        MemberDTO idDto = (MemberDTO) session.getAttribute("auth");

        int result = 0;
        
        

        if (id.equals(idDto.getId())) {
            dao.groupDelete1(seq);
            dao.groupDelete2(seq);
            dao.groupDelete3(seq);
            dao.groupDelete4(seq);

            result = dao.groupDelete(seq);
        } else {
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

        if (result != 0) {
            resp.sendRedirect(String.format("/movie/group/grouplist.do?group=%s", group));
        }


    }
}
