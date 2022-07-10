package com.project.movie.group;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/group/grouplist.do")
public class GroupList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String column = req.getParameter("column");
        String word = req.getParameter("word");
        String isSearch = "n";
        String group = req.getParameter("group");

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






        if ((column == null || word == null)
                || (column == "" || word == "")) {
            isSearch = "n";
        } else {
            isSearch = "y";
        }

        Map<String, String> map = new HashMap<>();

        map.put("group", group);

        map.put("column", column);
        map.put("word", word);
        map.put("isSearch", isSearch);

        String tag = req.getParameter("tag");
        map.put("tag", tag);

        //페이징
        int nowPage = 0; 	//현재 페이지 번호(= page)
        int begin = 0;
        int end = 0;
        int pageSize = 20;	//한페이지 당 출력할 게시물 수

        int totalCount = 0; //총 게시물 수
        int totalPage = 0;	//총 페이지 수

        String page = req.getParameter("page");

        if (page == null || page.equals("")) nowPage = 1;
        else nowPage = Integer.parseInt(page);

        begin = ((nowPage - 1) * pageSize) + 1;
        end = begin + pageSize - 1;

        map.put("begin", begin + "");
        map.put("end", end + "");

        List<PostDTO> list = dao.groupList(map);

        Calendar now = Calendar.getInstance();
        String strNow = String.format("%tF", now);

        for (PostDTO dto : list) {

            if (dto.getRegdate().startsWith(strNow)) {
                dto.setRegdate(dto.getRegdate().substring(11));
            } else {
                dto.setRegdate(dto.getRegdate().substring(0, 10));
            }

            if (dto.getTitle().length() > 15) {
                dto.setTitle(dto.getTitle().substring(0, 15) + "..");
            }

            dto.setTitle(dto.getTitle().replace("<", "&lt;").replace(">", "$gt;"));
        }

        totalCount = dao.getTotalCount(map);
        totalPage = (int)Math.ceil((double)totalCount / pageSize);

        String pagebar = "";

        int blockSize = 20;
        int n = 0;
        int loop = 0;

        loop = 1;
        n = ((nowPage - 1) / blockSize) * blockSize + 1;

        pagebar += "<ul class=\"pagination\">";

        if (n == 1) {
            pagebar += String.format(" <li class=\"page-item\">\r\n"
                    + "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Previous\">\r\n"
                    + "		        <span aria-hidden=\"true\">&laquo;</span>\r\n"
                    + "		      </a>\r\n"
                    + "		    </li> "
            );
        } else {
            pagebar += String.format(" <li class=\"page-item\">\r\n"
                            + "		      <a class=\"page-link\" href=\"/movie/group/grouplist.do?group=%s&page=%d\" aria-label=\"Previous\">\r\n"
                            + "		        <span aria-hidden=\"true\">&laquo;</span>\r\n"
                            + "		      </a>\r\n"
                            + "		    </li> "
                    , group
                    , n - 1
            );
        }

        while (!(loop > blockSize || n > totalPage)) {

            if (n == nowPage) {
                pagebar += String.format(" <li class=\"page-item active\"><a class=\"page-link\" href=\"#!\">%d</a></li> "
                        , n);
            } else {
                pagebar += String.format(" <li class=\"page-item\"><a class=\"page-link\" href=\"/movie/group/grouplist.do?group=%s&page=%d\">%d</a></li> "
                        , group
                        , n
                        , n);
            }

            loop++;
            n++;
        }

        if (n > totalPage) {
            pagebar += String.format(" <li class=\"page-item\">\r\n"
                    + "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Next\">\r\n"
                    + "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
                    + "		      </a>\r\n"
                    + "		    </li> "
            );
        } else {
            pagebar += String.format(" <li class=\"page-item\">\r\n"
                            + "		      <a class=\"page-link\" href=\"/movie/group/grouplist.do?group=%s&page=%d\" aria-label=\"Next\">\r\n"
                            + "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
                            + "		      </a>\r\n"
                            + "		    </li> "
                    , group
                    , n
            );
        }


        pagebar += "</ul>";

        String groupId = dao.getGroupId(group);

        session.setAttribute("read", "n");

        req.setAttribute("groupId", groupId);
        req.setAttribute("list", list);
        req.setAttribute("map", map);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("nowPage", nowPage);
        req.setAttribute("pagebar", pagebar);
        req.setAttribute("group", group);




        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/grouplist.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}