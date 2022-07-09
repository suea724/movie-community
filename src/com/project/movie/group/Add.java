package com.project.movie.group;

import com.project.movie.dto.GroupDTO;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/group/add.do")
public class Add extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        GroupDAO dao = new GroupDAO();

        List<String> taglist = dao.taglist();

        req.setAttribute("taglist", taglist);


        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/add.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String group = req.getParameter("group");

        MemberDTO dtoId = (MemberDTO) session.getAttribute("auth");

        PostDTO dto = new PostDTO();
        dto.setId(dtoId.getId());
        dto.setTitle(title);
        dto.setType("3");
        dto.setContent(content);

        GroupDAO dao = new GroupDAO();

        String maxSeq = null;

        int result = 0;
        if (dtoId.getId() != null) {
            result = dao.add(dto);

            maxSeq = dao.maxSeq();

            result = dao.addPostGroup(maxSeq, group);
        }

        String hashtag = req.getParameter("tags-disabled-user-input");

        JSONParser parser = new JSONParser();

        try {

            JSONArray list = (JSONArray)parser.parse(hashtag);

            for (Object obj : list) {
                String tag = (String)((JSONObject)obj).get("value");

                String hseq = dao.getHashTagSeq(tag);

                dao.addTagging(maxSeq, hseq);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (result == 1){
            //성공
            resp.sendRedirect(String.format("/movie/group/groupview.do?group=%s&seq=%s", group, maxSeq));
        } else {
            //실패
        }

    }
}
