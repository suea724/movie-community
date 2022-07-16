<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <style>



    </style>
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
                        <th >그룹원 이름</th>
                        <th>그룹원 닉네임</th>
                        <th>작성글 수</th>
                        <th>댓글 수</th>
                    </tr>

                    <c:forEach items="${list}" var="dto">
                    <tr class="table-hover">
                        <td>${dto.seq}</td>
                        <td style="text-align: center">${dto.name}</td>
                        <td style="text-align: center">${dto.nickname}</td>
                        <td>${dto.postcnt}</td>
                        <td>${dto.commentcnt}</td>
                    </tr>
                    </c:forEach>



                    <%--<c:if test="${list.size() == 0}">
                        <tr>
                            <td colspan="5">그룹이 없습니다.</td>
                        </tr>
                    </c:if>--%>

                </table>
            </div>
        </div>



    </section>
    <footer>

    </footer>
</main>


</body>
</html>
