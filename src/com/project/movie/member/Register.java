package com.project.movie.member;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.project.movie.dto.HashTagDTO;
import com.project.movie.dto.MemberDTO;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet("/member/register.do")
public class Register extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        MemberDAO dao = new MemberDAO();
        ArrayList<HashTagDTO> list = dao.getGenres();

        req.setAttribute("list", list);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        boolean isRegistered = false;
        boolean isAdded = false;

        int size = 1024 * 1024 * 100; // 최대 100MB
        String path = req.getRealPath("/asset/images");

        MultipartRequest multi = new MultipartRequest(req, path, size, "UTF-8", new DefaultFileRenamePolicy());

        String id = multi.getParameter("id");
        String pw = multi.getParameter("pw");
        String nickname = multi.getParameter("nickname");
        String name = multi.getParameter("name");
        String tel = multi.getParameter("tel");
        String profile = multi.getFilesystemName("profile");
        String genres = multi.getParameter("tags-disabled-user-input");

        MemberDTO dto = new MemberDTO();
        dto.setId(id);
        dto.setPassword(pw);
        dto.setNickname(nickname);
        dto.setName(name);
        dto.setTel(tel);
        dto.setPicture(profile);

        MemberDAO dao = new MemberDAO();
        isRegistered = dao.addMember(dto);// 회원가입

        ArrayList<HashTagDTO> list = new ArrayList<>();

        JSONParser parser = new JSONParser();

        try {
            JSONArray array = (JSONArray) parser.parse(genres);

            for (Object obj : array) {

                HashTagDTO hdto = new HashTagDTO();

                JSONObject tag = (JSONObject) obj;
                hdto.setHashtag((String)tag.get("value"));

                list.add(hdto);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // 회원이 선택한 태그 추가
        ArrayList<String> seqList = dao.getHashSeqList(list);
        isAdded = dao.addTag(id, seqList);

        if (isRegistered && isAdded) {
            resp.sendRedirect("/movie/member/login.do");
        }
    }
}
