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
        <div id="subboard">
            <div id="board">
                <table class="table-bordered" style="width: 1000px">
                    <tr>
                        <td colspan="3">${dto.title}</td>
                    </tr>

                    <tr>
                        <td style="width: 600px; text-align: left"> <span class="badge rounded-pill bg-warning">Lv.${auth.grade}</span> ${dto.nickname}(${dto.id})</td>
                        <td style="width: 250px; text-align: center;">${dto.regdate}</td>
                        <td style="width: auto; text-align: center;">조회수 : ${dto.readcount}</td>
                    </tr>

                    <tr>
                        <td colspan="3">
                            <c:forEach items="${list}" var="hash">
                                <input type="button" value="${hash}">
                            </c:forEach>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="3">${dto.content}</td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <form action="">
                                <button>좋아요</button>
                            </form>
                            <form action="">
                                <button>싫어요</button>
                            </form>
                        </td>
                    </tr>
                    <c:if test="${dto.id == auth.id}">
                    <tr>
                        <td colspan="3">
                            <button class="btn btn-primary" onclick="location.href='/movie/main/edit.do?seq=${dto.seq}';">수정하기</button>
                            <button class="btn btn-secondary" onclick="del();">삭제하기</button>
                        </td>
                    </tr>
                    </c:if>
                </table>
            </div>
            <%--댓글--%>
            <form action="">
                <table>
                    <tr>
                        
                    </tr>
                </table>
            </form>
        </div>

    </section>
    <footer>

    </footer>
</main>
<script>
    function del() {
        if(confirm('삭제하시겠습니까?')) {
            location.href='/movie/main/del.do?seq=${dto.seq}';
        }
    }
</script>

</body>
</html>
