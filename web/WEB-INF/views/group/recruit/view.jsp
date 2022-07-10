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



    #content {

        margin-top: 30px;
        margin-bottom: 50px;


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
                <h2 style="margin-bottom: 30px; padding-top: 20px;">${dto.title}</h2>
                <div>${dto.nickname}(${dto.id})<span style="display: inline; padding-left: 600px;">${dto.regdate} 조회수: ${dto.readcount}</span></div>
                <hr>
                <div style="height: 300px; vertical-align: middle; color: white; display: inline" id="content" >${dto.content}</div>
                <hr style="margin-bottom: 50px;">


            <div class="btns" id="btns">


                <c:if test="${dto.id == auth.id}">

                <button class="btn btn-primary"
                        onclick="location.href='/movie/recruit/edit.do?seq=${dto.seq}';" style="margin-right: 10px;">
                    수정하기
                </button>

                <button class="btn btn-primary"
                        onclick="location.href='/movie/recruit/del.do?seq=${dto.seq}';">
                    삭제하기
                </button>

                </c:if>

                <c:if test="${not empty auth}">
                    <c:if test="${dto.id != auth.id}">
                    <button class="btn btn-primary"
                            onclick="location.href='/movie/recruit/request.do?seq=${dto.seq}';">
                        신청하기
                    </button>
                    </c:if>
                </c:if>


            </div>

        </div>
    </div>

    </section>
    <footer>

    </footer>
</main>


</body>
</html>