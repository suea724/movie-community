package com.project.movie.group.mygroup;
import com.project.movie.dto.GroupDTO;
import com.project.movie.dto.MemberDTO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet("/group/mygroup/mygrouplist.do")
public class MyGroupList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        request.setCharacterEncoding("UTF-8");





        //id 넣기
        MemberDTO memberDto = (MemberDTO) session.getAttribute("auth");
        String id = memberDto.getId();



        MyGroupDAO dao = new MyGroupDAO();

        ArrayList<GroupDTO> list = dao.list(id);

        ArrayList<GroupDTO> list2 = dao.list2(list, id);

        for (GroupDTO dto : list2) {
            if (dto.getName().length() > 15) {
                dto.setName(dto.getName().substring(0, 15) + "..");
            }
        }





        request.setAttribute("list", list2);



        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/group/mygroup/mygrouplist.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
