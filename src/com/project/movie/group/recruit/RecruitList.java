package com.project.movie.group.recruit;


import com.project.movie.dto.RecruitmentPostDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

@WebServlet("/recruit/recruitlist.do")
public class RecruitList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //RecruitList.java
        //1. DB 작업 > DAO 위임 > select
        //2. 결과
        //3. JSP 호출 + 결과 전달하기

        req.setCharacterEncoding("UTF-8");


        //검색

        String column = req.getParameter("column");
        String word = req.getParameter("word");
        String isSearch = "n";

        if ((column == null || word == null) || (column == "" || word == "")) {
            isSearch = "n";
        } else {
            isSearch = "y";
        }


        HashMap<String,String> map = new HashMap<String,String>();

        map.put("column", column);
        map.put("word", word);
        map.put("isSearch", isSearch);


        //페이징

        int nowPage = 0; //현재 페이지 번호
        int begin = 0;
        int end = 0;
        int pageSize = 20; //한 페이지당 출력할 게시물 수

        int totalCount = 0; //총 게시물 수
        int totalPage = 0;  //총 페이지 수


        String page = req.getParameter("page");

        if (page == null || page.equals("")) nowPage = 1;
        else nowPage = Integer.parseInt(page);

        begin = ((nowPage - 1) * pageSize) + 1;
        end = begin + pageSize - 1;


        map.put("begin", begin + "");
        map.put("end", end + "");



        HttpSession session = req.getSession();

        RecruitDAO dao = new RecruitDAO();

        ArrayList<RecruitmentPostDTO> list = dao.list(map);

        Calendar now = Calendar.getInstance();
        String strNow = String.format("%tF", now);

        for(RecruitmentPostDTO dto : list) {

            //regdate
            if (dto.getRegdate().startsWith(strNow)) {
                //오늘
                dto.setRegdate(dto.getRegdate().substring(11));
            } else {
                //어제 이전
                dto.setRegdate(dto.getRegdate().substring(0, 10));
            }

            //제목 자르기
            if (dto.getTitle().length() > 25) {
                dto.setTitle(dto.getTitle().substring(0,25) + "...");
            }

            //태그 비활성화
            dto.setTitle(dto.getTitle().replace("<","&lt;").replace(">","&gt;"));


        }

        //새로고침 조회수 증가 방지
       // session.setAttribute("read", "n");


        //총 페이지 수 구하기
        totalCount = dao.getTotalCount(map);
        totalPage = (int)Math.ceil((double)totalCount / pageSize);

        System.out.println(totalCount);
        System.out.println(totalPage);

        String pagebar = "";

        int blockSize = 10;	//한번에 보여질 페이지 개수
        int n = 0;			//페이지 번호
        int loop = 0;		//루프

        pagebar = "";

        loop = 1;
        n = ((nowPage - 1) / blockSize) * blockSize + 1;

        pagebar += "<ul class=\"pagination\">";

        if (n == 1) {
            pagebar += String.format(" <li class=\"page-item\">\r\n"
                    + "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Previous\">\r\n"
                    + "		        <span aria-hidden=\"true\">&laquo;</span>\r\n"
                    + "		      </a>\r\n"
                    + "		    </li> "
            );
        } else {
            pagebar += String.format(" <li class=\"page-item\">\r\n"
                            + "		      <a class=\"page-link\" href=\"/movie/recruit/recruitlist.do?page=%d\" aria-label=\"Previous\">\r\n"
                            + "		        <span aria-hidden=\"true\">&laquo;</span>\r\n"
                            + "		      </a>\r\n"
                            + "		    </li> "
                    , n - 1
            );
        }


        while (!(loop > blockSize || n > totalPage)) {

            if (n == nowPage) {
                pagebar += String.format(" <li class=\"page-item active\"><a class=\"page-link\" href=\"#!\">%d</a></li> "
                        , n);
            } else {
                pagebar += String.format(" <li class=\"page-item\"><a class=\"page-link\" href=\"/movie/recruit/recruitlist.do?page=%d\">%d</a></li> "
                        , n
                        , n);
            }

            loop++;
            n++;
        }


        if (n > totalPage) {
            pagebar += String.format(" <li class=\"page-item\">\r\n"
                    + "		      <a class=\"page-link\" href=\"#!\" aria-label=\"Next\">\r\n"
                    + "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
                    + "		      </a>\r\n"
                    + "		    </li> "
            );
        } else {
            pagebar += String.format(" <li class=\"page-item\">\r\n"
                            + "		      <a class=\"page-link\" href=\"/movie/recruit/recruitlist.do?page=%d\" aria-label=\"Next\">\r\n"
                            + "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
                            + "		      </a>\r\n"
                            + "		    </li> "
                    , n
                    );
        }


        pagebar += "</ul>";



        req.setAttribute("list", list);

        req.setAttribute("map", map);

        req.setAttribute("totalCount", totalCount);
        req.setAttribute("totalPage", totalPage);

        req.setAttribute("nowPage", nowPage);

        req.setAttribute("pagebar", pagebar);




        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/recruit/recruitlist.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}



