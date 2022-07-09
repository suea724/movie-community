package com.project.movie.group;

import com.project.movie.dto.PostDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
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
        int pageSize = 10;	//한페이지 당 출력할 게시물 수

        int totalCount = 0; //총 게시물 수
        int totalPage = 0;	//총 페이지 수

        String page = req.getParameter("page");

        if (page == null || page.equals("")) nowPage = 1;
        else nowPage = Integer.parseInt(page);

        begin = ((nowPage - 1) * pageSize) + 1;
        end = begin + pageSize - 1;

        map.put("begin", begin + "");
        map.put("end", end + "");


        HttpSession session = req.getSession();

        GroupDAO dao = new GroupDAO();

        List<PostDTO> list = dao.groupList(map);






        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/grouplist.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}