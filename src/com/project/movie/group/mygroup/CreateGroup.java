package com.project.movie.group.mygroup;

import com.oreilly.servlet.MultipartRequest;
import com.project.movie.dto.GroupDTO;
import com.project.movie.dto.MemberDTO;
import oracle.jdbc.driver.json.tree.JsonpObjectImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/group/mygroup/creategroup.do")
public class CreateGroup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        MyGroupDAO dao = new MyGroupDAO();

        //태그리스트 가져오기
        ArrayList<String> taglist = dao.taglist();

        request.setAttribute("taglist", taglist);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/group/mygroup/creategroup.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        request.setCharacterEncoding("UTF-8");

        //MultipartRequest multi = null;

        MyGroupDAO dao = new MyGroupDAO();

        String name = request.getParameter("gname");
        String info = request.getParameter("content");
        String recruitment = request.getParameter("people");

        System.out.println(name);
        System.out.println(info);
        System.out.println(recruitment);

        GroupDTO gdto = new GroupDTO();

        gdto.setName(name);
        gdto.setInfo(info);
        gdto.setRecruitment(recruitment);

        MemberDTO memberDto = (MemberDTO) session.getAttribute("auth");
        gdto.setId(memberDto.getId());


        int result = 0;
        int result2 = 0;
        String seq = "";
        //그룹 생성
        if (session.getAttribute("auth")!= null) {

            result = dao.add(gdto);
            seq = dao.getSeq();
            gdto.setSeq(seq);
            result2 = dao.addGroupMaster(gdto);
        }




        String tags = request.getParameter("tags-disabled-user-input");

        System.out.println(tags);

        //위에 방금 만든 그룹 번호 알아오기


        JSONParser parser = new JSONParser();



        try {

            JSONArray list = (JSONArray)parser.parse(tags);

            for (Object obj : list) {

                String tag = (String)((JSONObject)obj).get("value");

                //태그 번호 알아오기
                String hseq = dao.getHashTagSeq(tag);

                System.out.println(hseq);

                HashMap<String,String> map = new HashMap<String,String>();

                map.put("gseq", seq);
                map.put("hseq", hseq);

                dao.addTagging(map);

            }

        } catch(Exception e) {
            System.out.println("CreateGroup_doPost");
            e.printStackTrace();

        }

        System.out.println(result);
        System.out.println(result2);

        if (result == 1 && result2 == 1) {
            response.sendRedirect("/movie/group/mygroup/mygrouplist.do");
        } else {
            response.sendRedirect("/movie/group/mygroup/creategroup.do");
        }

//        request.setAttribute("result", result);


//        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/group/mygroup/mygrouplist.jsp");
//
//        dispatcher.forward(request, response);

    }
}
