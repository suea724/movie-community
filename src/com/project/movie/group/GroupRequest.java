package com.project.movie.group;

import com.project.movie.dto.GroupRequestDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/group/grouprequest.do")
public class GroupRequest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        GroupDAO dao = new GroupDAO();

        String group = req.getParameter("group");
        String groupId = dao.getGroupId(group);

        int nowPage = 0; 	//현재 페이지 번호(= page)
        int begin = 0;
        int end = 0;
        int pageSize = 20;	//한페이지 당 출력할 게시물 수

        int totalCount = 0; //총 게시물 수
        int totalPage = 0;	//총 페이지 수

        Map<String, String> map = new HashMap<>();

        String page = req.getParameter("page");

        if (page == null || page.equals("")) nowPage = 1;
        else nowPage = Integer.parseInt(page);

        begin = ((nowPage - 1) * pageSize) + 1;
        end = begin + pageSize - 1;

        map.put("group", group);
        map.put("begin", begin + "");
        map.put("end", end + "");

        System.out.println("begin = " + begin);
        System.out.println("end = " + end);
        
        List<GroupRequestDTO> list = dao.getRequest(map);
        
        

        totalCount = dao.getRequestCount(map);
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
                            + "		      <a class=\"page-link\" href=\"/movie/group/grouprequest.do?group=%s&page=%d\" aria-label=\"Previous\">\r\n"
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
                pagebar += String.format(" <li class=\"page-item\"><a class=\"page-link\" href=\"/movie/group/grouprequest.do?group=%s&page=%d\">%d</a></li> "
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
                            + "		      <a class=\"page-link\" href=\"/movie/group/grouprequest.do?group=%s&page=%d\" aria-label=\"Next\">\r\n"
                            + "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
                            + "		      </a>\r\n"
                            + "		    </li> "
                    , group
                    , n
            );
        }


        pagebar += "</ul>";

        System.out.println("list = " + list);

        req.setAttribute("list", list);
        req.setAttribute("pagebar", pagebar);
        req.setAttribute("groupId", groupId);
        req.setAttribute("group", group);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/grouprequest.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.setCharacterEncoding("UTF-8");
        String yesNo = req.getParameter("yesNo");
        String group = req.getParameter("group");
        String seq = req.getParameter("seq");
        String id = req.getParameter("id");

        System.out.println("id = " + id);
        System.out.println("seq = " + seq);
        System.out.println("group = " + group);
        System.out.println("yesNo = " + yesNo);

        int result = 0;

        GroupDAO dao = new GroupDAO();

        if (yesNo.equals("0")) {
            dao.deleteRequest(seq);
            result = dao.addRequest(group, id);

        } else if (yesNo.equals("1")) {
            result = dao.deleteRequest(seq);
        }

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();

        writer.printf("{ \"result\": %d }", result);

        writer.close();

        
    }
}
