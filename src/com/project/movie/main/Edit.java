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

@WebServlet("/main/edit.do")
public class Edit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //seq 가져오기
        String seq = req.getParameter("seq");

        MainDAO dao = new MainDAO();

        ArrayList<String> taglist = dao.taglist();

        req.setAttribute("taglist", taglist);

        PostDTO tempdto = new PostDTO();
        tempdto.setSeq(seq);

        PostDTO dto = dao.getView(tempdto);

        req.setAttribute("dto", dto);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/main/edit.jsp");
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
        String seq = req.getParameter("seq");

        //3. 데이터 처리하기
        PostDTO dto = new PostDTO();

        dto.setSeq(seq);
        dto.setTitle(title);
        dto.setContent(content);
        dto.setType(type);

        //3.5 해시태그 처리하기
        String hashtag = req.getParameter("tags-disabled-user-input");
        //System.out.println(hashtag);
        JSONParser parser = new JSONParser(); //json으로 받아온 데이터를 java로 바꿔주는 역할

        try {
            JSONArray list = (JSONArray) parser.parse(hashtag); //json데이터를 json 배열로 변환

            for(Object obj : list) {
                String tag = (String)((JSONObject)obj).get("value");
                //System.out.println(tag);
                String hseq = dao.getHashTagSeq(tag);
                //System.out.println(hseq);

                dao.delHashTag(seq); //삭제하고
                dao.addHashTag(hseq, Integer.parseInt(seq)); //추가하기
            }
        } catch(ParseException e) {
            e.printStackTrace();
        }

        int result = dao.updatePost(dto);
        System.out.println("result : " +  result);

        if(result == 1) {
            resp.sendRedirect("/movie/main/view.do?seq=" + seq);
        }

    }
}
