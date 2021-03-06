<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <link rel="stylesheet" href="/movie/asset/css/groupcss.css">
</head>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp"%>

    <section>

        <div id="subboard">
            <%@ include file="/WEB-INF/inc/groupmenu.jsp"%>
            <div id="board">
                <table class="table" style="width: 1000px">
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성날짜</th>
                        <th>조회수</th>
                    </tr>
                    <c:forEach items="${list}" var="dto">
                        <tr class="table-hover">
                            <td>${dto.seq}</td>
                            <td><a href="/movie/group/groupview.do?group=${group}&seq=${dto.seq}" style="color: white">${dto.title} <span style="color: red; text-decoration:underline;">${dto.commentcount}</span></a></td>
                            <td>${dto.nickname}</td>
                            <td>${dto.regdate}</td>
                            <td>${dto.readcount}</td>
                        </tr>

                    </c:forEach>
                </table>
            </div>
        </div>
        <c:if test="${not empty auth}">
            <div id="add" style="display: flex; justify-content: right">
                <input type="button" class="btn btn-danger" value="글 쓰기" onclick="location.href='/movie/group/groupadd.do?group=${group}';" style="margin-top: 20px; margin-right: 30px">
            </div>
        </c:if>
        <div style="justify-content: center; display: flex">
            ${pagebar}
        </div>

        <div style="display: flex; justify-content: center">
            <form method="GET" action="/movie/group/grouplist.do">
                <table class="search">
                    <tr>
                        <td>
                            <select name="column" class="form-select">
                                <option value="title">제목</option>
                                <option value="content">내용</option>
                                <option value="nickname">이름</option>
                            </select>
                        </td>
                        <td>
                            <input type="text" name="word" class="form-control" required>
                        </td>
                        <td>
                            <button class="btn btn-primary">
                                검색하기
                            </button>

                        </td>
                    </tr>
                </table>


                <input type="hidden" name="group" value="${group}">
            </form>
        </div>

    </section>
    <footer>

    </footer>
</main>
<script>

</script>


</body>
</html>