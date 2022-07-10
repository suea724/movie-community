<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <style>

        #board {

            margin-left: 10px;
            margin-bottom: 20px;
            padding-left: 50px;
            padding-bottom: 20px;
            width: 1000px;
            background-color: #373737;

        }



        #btns {

            display: flex;
            justify-content: right;
            margin-right: 20px;

        }



    </style>
</head>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp"%>

    <section>

        <div id="subboard">
            <div id="submenu">
                <ul>
                    <li><a href="">내 그룹</a></li>
                    <li><a href="">모집 게시판</a></li>

                </ul>
            </div>


            <div id="board" style="color: white;">

                <form method="POST" action="/movie/recruit/del.do">

                    <div>삭제하시겠습니까?</div>

                    <div class="btns" id="btns">
                        <input type="button" value="돌아가기" class="btn btn-secondary" onclick="history.back()">
                        <input type="submit" value="삭제하기" class="btn-primary">
                        <input type="hidden" value="${seq}" name="seq">
                    </div>


                </form>


                </div>

            </div>
        </div>

    </section>
    <footer>

    </footer>
</main>

<script>

/*    <c:if test="${result == 1}">
    location.href = '/movie/recruit/recruitlist.do';
    </c:if>

    <c:if test="${result == 0}">
    alert('failed');
    history.back();
    </c:if>*/


</script>


</body>
</html>