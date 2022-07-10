package com.project.movie.main;

import com.project.movie.dto.PostDTO;
import oracle.jdbc.proxy.annotation.Post;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

@WebServlet(urlPatterns = {"/main/mainlist.do", "/", "/index.do"})
public class MainList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 검색하기에서 넘긴 데이터 받기
        // 인코딩하기
        req.setCharacterEncoding("UTF-8");
        String column = req.getParameter("column"); //검색 카테고리
        String word = req.getParameter("word"); //검색 내용
        String isSearch = "n"; //검색 중인 상태 여부
        
        String type = req.getParameter("type"); //전체/리뷰/자유 받아오기

        if(type == null) {
            type = "0";
        }

        //검색 중인 상태인지 아닌지 판단하기
        // 넘긴 값(검색 카테고리와 검색내용) 중 하나라도 비어있으면 검색중인 상태가 아니라고 판단
        if((column == null || word == null)
                || (column == "" || word == "")) {
            isSearch = "n";
        } else {
            isSearch = "y";
        }

        //System.out.println(isSearch); //이상없음

        //포장하기
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("column", column);
        map.put("word", word);
        map.put("isSearch", isSearch);

        //페이징
        int nowPage = 0; //현재 페이지 번호
        int begin = 0;
        int end = 0;
        int pageSize = 20; //한 페이지 당 출력할 게시물 수

        int totalCount = 0;
        int totalPage = 0;

        String page = req.getParameter("page");

        if(page == null || page == "") {
            nowPage = 1;
        } else {
            nowPage = Integer.parseInt(page);
        }

        begin = ((nowPage - 1) * pageSize) + 1;
        end = begin + pageSize - 1;

        map.put("begin", begin + "");
        map.put("end", end + "");
        map.put("type", type);

        HttpSession session = req.getSession();

        MainDAO dao = new MainDAO();

        //전체 글 가져오기
        ArrayList<PostDTO> list = dao.list(map);
        //System.out.println(list); //이상없음
        //2.5 출력 데이터 가공업무

        Calendar now = Calendar.getInstance();
        String strNow = String.format("%tF", now);

        for(PostDTO dto : list) {

            //regdate
            dto.setRegdate(dto.getRegdate().substring(11));

            //title
            if(dto.getTitle().length() > 15) {
                dto.setTitle(dto.getTitle().substring(0, 15) + "...");
            }

            //태그 비활성화
            dto.setTitle(dto.getTitle().replace("<", "&lt;").replace(">", "&gt;"));

        }

        // 총 페이지수 구하기
        totalCount = dao.getTotalCount(map);
        totalPage = (int)Math.ceil((double) totalCount / pageSize);

        String pagebar = "";

        int blockSize = 10;
        int n = 0;
        int loop = 0;

        pagebar = "";

        loop = 1;
        n = ((nowPage - 1) / blockSize ) * blockSize + 1;

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
                            + "		      <a class=\"page-link\" href=\"/movie/main/mainlist.do?page=%d\" aria-label=\"Previous\">\r\n"
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
                pagebar += String.format(" <li class=\"page-item\"><a class=\"page-link\" href=\"/movie/main/mainlist.do?page=%d\">%d</a></li> "
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
                            + "		      <a class=\"page-link\" href=\"/movie/main/mainlist.do?page=%d\" aria-label=\"Next\">\r\n"
                            + "		        <span aria-hidden=\"true\">&raquo;</span>\r\n"
                            + "		      </a>\r\n"
                            + "		    </li> "
                    , n
            );
        }


        pagebar += "</ul>";

        //3.
        req.setAttribute("list", list);
        req.setAttribute("map", map);
        req.setAttribute("totalCount", totalCount);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("nowPage", nowPage);
        req.setAttribute("pagebar", pagebar);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/main/mainlist.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
