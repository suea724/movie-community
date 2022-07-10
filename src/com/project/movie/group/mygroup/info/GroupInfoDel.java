package com.project.movie.group.mygroup.info;

import com.project.movie.group.GroupDAO;
import com.project.movie.group.mygroup.MyGroupDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/group/mygroup/info/groupinfodel.do")
public class GroupInfoDel extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String seq = request.getParameter("group");
        String id = request.getParameter("groupid");

        MyGroupDAO dao = new MyGroupDAO();

        int result = 0;

        //해시태그 지우기
        //그룹원 1명이어야 가능

        dao.delHashTag(seq); //해시태그
        dao.delUserGroup(seq); //
        dao.delMyGroup(seq);




        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/group/groupadd.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



    }
}

