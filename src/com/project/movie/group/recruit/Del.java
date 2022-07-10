package com.project.movie.group.recruit;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/recruit/del.do")
public class Del extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        //1. seq 가져오기
        //2. DB 작업 > DAO 위임 > seq
        //3. 결과
        //4. JSP 호출하기


        //HttpSession session = req.getSession();

        //1.
        String seq = req.getParameter("seq");

        //2. + 3.
        RecruitDAO dao = new RecruitDAO();

        int result = dao.del(seq);


        //4.
        req.setAttribute("result", result);


        if (result == 1) {
            resp.sendRedirect("/movie/recruit/recruitlist.do");
        } else {

            PrintWriter writer = resp.getWriter();

            writer.println("<html>");
            writer.println("<body>");
            writer.println("<script>");
            writer.println("alert('failed');");
            writer.println("history.back();");
            writer.println("</script>");
            writer.println("</body>");
            writer.println("</html>");

            writer.close();

        }




    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



        //1.

        String seq = req.getParameter("seq");

        //2.
        req.setAttribute("seq", seq);



        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/group/recruit/del.jsp");
        dispatcher.forward(req, resp);
    }


}



