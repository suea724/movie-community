<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
</head>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp"%>

    <section style="display: flex; justify-content: center;">
        <form method="post" action="/movie/main/add.do">
            <table class="table table-bordered" style="width: 1000px; height: 700px;">
                <tr>
                    <td style="width: 150px;">
                        <select name="type" required style="width: 130px; height: 40px;">
                            <option disabled selected>전체</option>
                            <option value="1">리뷰</option>
                            <option value="2">자유</option>
                        </select>
                    </td>
                    <td colspan><input type="text" name="title" required placeholder="제목을 입력하세요." style="width: 830px; height: 40px;"></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input name='tags-disabled-user-input' class="form-control" placeholder='장르를 선택하세요.' style="height: 40px;">
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><textarea name="content" placeholder="내용을 입력하세요." style="width: 980px; height: 600px;" required></textarea></td>
                </tr>
            </table>
            <div class="btns" style="display: flex; justify-content: right;">
                <input type="button" value="돌아가기" class="btn btn-light" onclick="history.back();">
                <input type="submit" value="글쓰기" class="btn btn-dark">
            </div>
        </form>

    </section>
    <footer>

    </footer>
</main>


<script>
    var input = document.querySelector('input[name=tags-disabled-user-input]');

    let temp = [];

    <c:forEach items="${taglist}" var="tag">
    temp.push('${tag}');
    </c:forEach>

    new Tagify(input, {
        whitelist: temp,
        userInput: false
    })



</script>

</body>
</html>