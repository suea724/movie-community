package com.project.movie.group.mygroup.info;

import com.project.movie.dto.GroupDTO;
import com.project.movie.group.GroupDAO;
import com.project.movie.group.mygroup.MyGroupDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/group/mygroup/groupinfo.do")
public class GroupInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String seq = request.getParameter("group");


        MyGroupDAO dao = new MyGroupDAO();


        //그룹명, 그룹인원, 그룹 설명, 그룹 개설날짜, 그룹장,   >  해시태그

        ArrayList<GroupDTO> list = dao.groupinfo(seq);








        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/group/grouprequest.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }
}
