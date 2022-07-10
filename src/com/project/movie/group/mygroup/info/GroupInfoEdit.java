package com.project.movie.group.mygroup.info;

import com.project.movie.dto.GroupDTO;
import com.project.movie.dto.MemberDTO;
import com.project.movie.group.mygroup.MyGroupDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

@WebServlet("/group/mygroup/info/groupinfoedit.do")
public class GroupInfoEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String seq = request.getParameter("group");
        String groupId = request.getParameter("groupid");

//        System.out.println(seq);
//        System.out.println(groupId);

        //쿼리

        MyGroupDAO dao = new MyGroupDAO();

        GroupDTO dto = new GroupDTO();

        dto = dao.findGroup(seq);


        int result = dao.delHashTag(seq);

        //System.out.println(result);

        ArrayList<String> taglist = dao.taglist();

        request.setAttribute("taglist", taglist);


        request.setAttribute("dto", dto);
        request.setAttribute("group", seq);
        request.setAttribute("groupId", groupId);




        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/group/mygroup/groupinfoedit.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        HttpSession session = request.getSession();

        request.setCharacterEncoding("UTF-8");


        //MultipartRequest multi = null;

        MyGroupDAO dao = new MyGroupDAO();
        String seq = request.getParameter("seq");
        String name = request.getParameter("gname");
        String info = request.getParameter("content");
        String recruitment = request.getParameter("people");

        System.out.println(seq);
        System.out.println(name);
        System.out.println(info);
        System.out.println(recruitment);

        String groupId = request.getParameter("groupid");

        GroupDTO gdto = new GroupDTO();

        gdto.setSeq(seq);
        gdto.setName(name);
        gdto.setInfo(info);
        gdto.setRecruitment(recruitment);

        MemberDTO memberDto = (MemberDTO) session.getAttribute("auth");
        gdto.setId(memberDto.getId());


        int result = 0;


        //그룹 수정
        if (session.getAttribute("auth")!= null) {

            result = dao.edit(gdto);
            //seq = dao.getSeq();
            //gdto.setSeq(seq);
            //result2 = dao.addGroupMaster(gdto);
        }




        String tags = request.getParameter("tags-disabled-user-input");

        System.out.println(tags);

        //위에 방금 만든 그룹 번호 알아오기


        if (!tags.equals("")) {

            JSONParser parser = new JSONParser();


            try {

                JSONArray list = (JSONArray) parser.parse(tags);

                for (Object obj : list) {

                    String tag = (String) ((JSONObject) obj).get("value");

                    //태그 번호 알아오기
                    String hseq = dao.getHashTagSeq(tag);

                    System.out.println(hseq);

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("gseq", seq);
                    map.put("hseq", hseq);

                    dao.addTagging(map);

                }

            } catch (Exception e) {
                System.out.println("CreateGroup_doPost");
                e.printStackTrace();

            }
        }

        System.out.println(result);

        request.setAttribute("group", seq);
        request.setAttribute("groupId", groupId);

        if (result == 1) {
            response.sendRedirect(String.format("/movie/group/mygroup/groupinfo.do?group=%s", seq));
        } else {
            response.sendRedirect(String.format("/movie/group/mygroup/info/groupinfoedit.do?seq=%s&groupid=%s", seq, groupId));
        }



    }
}
