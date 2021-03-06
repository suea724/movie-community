package com.project.movie.group.mygroup.member;

import com.project.movie.dto.GroupMemberDTO;
import com.project.movie.dto.MemberDTO;
import com.project.movie.group.mygroup.MyGroupDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/group/mygroup/member/mygroupmember.do")
public class MyGroupMember extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String seq = request.getParameter("group");

        HttpSession session = request.getSession();

        GroupMemberDTO dto = new GroupMemberDTO();

        MemberDTO memberDto = (MemberDTO) session.getAttribute("auth");
        dto.setId(memberDto.getId());


        MyGroupDAO dao = new MyGroupDAO();




        ArrayList<GroupMemberDTO> list = dao.groupmember(seq);


        String id = dto.getId();

        request.setAttribute("groupId", id);
        request.setAttribute("group", seq);
        request.setAttribute("list", list);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/group/mygroup/mygroupmember.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");




    }
}
