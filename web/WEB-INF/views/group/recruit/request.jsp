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

                <form method="POST" action="/movie/recruit/request.do">

                    <div>신청하시겠습니까?</div>

                    <div class="btns" id="btns">
                        <input type="button" value="돌아가기" class="btn btn-secondary" onclick="history.back()">
                        <input type="submit" value="신청하기" class="btn btn-primary">
                    </div>
                    <input type="hidden" value="${seq}" name="seq">
                    <input type="hidden" value="${mdto.id}" name="id">
                    <input type="hidden" value="${dto.gseq}" name="gseq">



                </form>


            </div>

        </div>
        </div>

    </section>
    <footer>

    </footer>
</main>

<script>

</script>


</body>
</html>