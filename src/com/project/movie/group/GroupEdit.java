package com.project.movie.group;

import com.project.movie.dto.MemberDTO;
import com.project.movie.dto.PostDTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

@WebServlet("/group/groupedit.do")
public class GroupEdit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

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
        int check = dao.editCheck(seq, id);
        System.out.println("check = " + check);

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

        PostDTO dto = dao.GroupView(seq);

        List<String> list = dao.GroupViewTag(seq);

        //- 태그 비활성화
        dto.setTitle(dto.getTitle().replace("<", "&lt;").replace(">", "&gt;"));
        dto.setContent(dto.getContent().replace("<", "&lt;").replace(">", "&gt;"));

        //- 출력 데이터 조작하기
        dto.setContent(dto.getContent().replace("\r\n", "<br>"));

        List<String> taglist = dao.taglist();

        req.setAttribute("taglist", taglist);
        req.setAttribute("dto", dto);
        req.setAttribute("list", list);







        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/groupedit.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        req.setCharacterEncoding("UTF-8");

        String seq = req.getParameter("seq");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String group = req.getParameter("group");

        MemberDTO dtoId = (MemberDTO) session.getAttribute("auth");

        PostDTO dto = new PostDTO();
        dto.setId(dtoId.getId());
        dto.setTitle(title);
        dto.setType("3");
        dto.setContent(content);
        dto.setSeq(seq);

        GroupDAO dao = new GroupDAO();

        int result = 0;
        if (dtoId.getId() != null) {
            result = dao.groupEdit(dto);

        }

        dao.deleteHashTag(seq);

        String hashtag = req.getParameter("tags-disabled-user-input");

        JSONParser parser = new JSONParser();

        try {

            JSONArray list = (JSONArray)parser.parse(hashtag);

            for (Object obj : list) {
                String tag = (String)((JSONObject)obj).get("value");

                String hseq = dao.getHashTagSeq(tag);

                dao.addTagging(seq, hseq);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (result == 1){
            //성공
            resp.sendRedirect(String.format("/movie/group/groupview.do?group=%s&seq=%s", group, seq));
        } else {
            //실패
        }

    }
} 
