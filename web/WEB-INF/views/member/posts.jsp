<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp" %>
    <link rel="stylesheet" href="/movie/asset/css/mypage-submenu.css">
</head>
<style>
    #box {
        display: flex;
        width: 1000px;
        margin: 0 30% 50px 30px;
        justify-content: space-between;
    }

    section div#board{
        margin-left: 16px;
        background-color: rgba(170, 170, 170, 0.158);
        border-radius: 20px;
    }

    .table {
        width: 800px;
    }

    .table-hover:hover {
        background-color: rgb(69, 149 , 140);
    }

    section #board > .table tr td:nth-child(3) {
        text-align: left;
    }


    section div > table tr:nth-child(1) {
        background-color: gray;
    }

    section div > table tr th {
        color: white;
        text-align: center;
    }

    section div > table tr td {
        color: white;
        text-align: center;
        padding: 10px;
    }

    section div > table tr:first-child th:first-child { border-top-left-radius: 20px; }
    section div > table tr:first-child th:last-child { border-top-right-radius: 20px; }

    .table th:nth-child(1) { width: 80px; }
    .table th:nth-child(2) { width: 80px; }
    .table th:nth-child(3) { width: auto; }
    .table th:nth-child(4) { width: 200px; }
    .table th:nth-child(5) { width: 80px; }

    .pagination {
        justify-content: center;
    }

    .table tr td a {
        color: white;
    }

</style>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp" %>

    <section>

        <div id="box">
            <%@ include file="submenu.jsp"%>

            <div>
            <div id="board">
                <table class="table">
                    <tr>
                        <th>번호</th>
                        <th>분류</th>
                        <th>제목</th>
                        <th>작성날짜</th>
                        <th>조회수</th>
                    </tr>
                    <c:if test="${empty list}">
                        <tr>
                            <td colspan="5" style="text-align: center">작성된 글이 없습니다.</td>
                        </tr>
                    </c:if>
                    <c:forEach var="dto" items="${list}">
                    <tr>
                        <td>${dto.seq}</td>
                        <td>${dto.type}</td>
                        <c:if test="${dto.type == '리뷰' || dto.type == '자유'}">
                        <td><a href="">${dto.title}</a></td>
                        </c:if>
                        <c:if test="${dto.type == '그룹'}">
                        <td>${dto.title}</td>
                        </c:if>
                        <td>${dto.regdate}</td>
                        <td>${dto.readcnt}</td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <c:if test="${pagination.prev == true}">
                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                    </c:if>
                    <c:forEach var="index" begin="${pagination.beginPage}" end="${pagination.endPage}" step="1">
                    <li class="page-item"><a class="page-link" href="/movie/member/mypage/posts.do?page=${index}">${index}</a></li>
                    </c:forEach>
                    <c:if test="${pagination.prev == true}">
                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                    </c:if>
                </ul>
            </nav>
            </div>
        </div>

    </section>
    <footer>

    </footer>
</main>

<script>
    $('#myposts').css('color', 'rgb(69, 149 , 140)');
</script>


</body>
</html>
