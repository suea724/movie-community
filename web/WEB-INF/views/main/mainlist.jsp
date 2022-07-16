<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
</head>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp"%>

    <section>
        <c:if test="${map.isSearch == 'y'}">
            <div style="text-align: center; color: white; margin-bottom: 10px;">
                '${map.word}'으로 검색한 결과 총 <span style="color: rgb(69, 149 , 140);">${list.size()}</span>개의 게시물이 발견되었습니다.
            </div>
        </c:if>
        <div id="subboard" style="justify-content: center;">

            <div id="board" >
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
                        <td><a href="/movie/main/view.do?seq=${dto.seq}" style="color: white">${dto.title}</a><span style="color: red; text-decoration:underline;">${dto.commentcount}</span></td>
                        <td>${dto.nickname}</td>
                        <td>${dto.regdate}</td>
                        <td>${dto.readcount}</td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <c:if test="${not empty auth.id}">
        <div class="btns" style="display: flex; justify-content: right; margin-top: 20px; margin-right: 95px;"> <%--글쓰기 버튼 추가--%>
            <input type="button" id="addBtn" class="btn btn-primary" value="글쓰기" onclick="location.href='/movie/main/add.do';">
        </div>
        </c:if>
        <div id="pagebar" style="display: flex; justify-content: center;">
            ${pagebar}
        </div>
        <div id="search" style="display: flex; justify-content: center">
            <form action="/movie/main/mainlist.do" method="get">
                <table class="search">
                    <tr>
                        <td style="margin-left: 5px;">
                            <select name="column" class="form-control">
                                <option value="title">제목</option>
                                <option value="content">내용</option>
                                <option value="nickname">작성자</option>
                            </select>
                        </td>
                        <td style="margin-left: 5px;">
                            <input type="text" name="word" class="form-control" required>
                        </td>
                        <td>
                            <input type="submit" value="검색하기" class="btn btn-light">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

    </section>
    <footer>

    </footer>
</main>


</body>
</html>