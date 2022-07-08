package com.project.movie.main;

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
import java.util.HashMap;

@WebServlet("/main/add.do")
public class Add extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MainDAO dao = new MainDAO();

        ArrayList<String> taglist = dao.taglist();

        req.setAttribute("taglist", taglist);


        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/main/add.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        MainDAO dao = new MainDAO();

        //1. 인코딩하기
        req.setCharacterEncoding("UTF-8");

        //2. 데이터받아오기
        String title = req.getParameter("title");
        String type = req.getParameter("type");
        String content = req.getParameter("content");

        //3. 데이터 처리하기
        PostDTO dto = new PostDTO();

        dto.setTitle(title);
        dto.setContent(content);
        dto.setType(type);

        MemberDTO memberDto = (MemberDTO) session.getAttribute("auth");
        dto.setId(memberDto.getId());

        int result = dao.add(dto);
        int maxSeq = dao.getMaxSeq();

        //3.5 해시태그 처리하기
        String hashtag = req.getParameter("tags-disabled-user-input");
        System.out.println(hashtag);
        JSONParser parser = new JSONParser(); //json으로 받아온 데이터를 java로 바꿔주는 역할

        try {
            JSONArray list = (JSONArray) parser.parse(hashtag); //json데이터를 json 배열로 변환

            for(Object obj : list) {
                String tag = (String)((JSONObject)obj).get("value");
                System.out.println(tag);
                String hseq = dao.getHashTagSeq(tag);
                System.out.println(hseq);


                dao.addHashTag(hseq, maxSeq);
            }
        } catch(ParseException e) {
            e.printStackTrace();
        }



        if(result == 1){
            //성공
            resp.sendRedirect("/movie/main/view.do?seq=" + maxSeq);
        } else {
            //실패

        }
    }
}
