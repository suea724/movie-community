<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <style>
        h2, h5 {
            color: white;
        }

        h2 {
            text-align: center;
        }

        #box {
            width: 500px;
            margin: 0 auto;
        }

        #login-box > input, h2 {
            margin-bottom: 20px;
        }

        #login-box > input {
            height: 50px;
        }

        #labels span a {
            /*font-size: 1.1em;*/
            margin-left: 40px;
            margin-right: 30px;
            color: white;
        }

        #updatepw-box > div {
            margin-bottom : 20px;
        }

        #updatepw-box > div > input {
            height: 50px;
        }

        #btn-box {
            margin-bottom: 50px;
        }

    </style>
</head>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp"%>
    <section>
        <div id="box">
            <h2>비밀번호 재설정</h2>
            <form action="/movie/member/updatepw.do" method="post">
                <div id="updatepw-box">
                    <div>
                        <h5>새로운 비밀번호</h5>
                        <input type="password" name="pw" id="pw" class="form-control" required>
                    </div>
                    <div>
                        <h5>새로운 비밀번호 확인</h5>
                        <input type="password" name="pwCheck" id="pwCheck" class="form-control" required>
                    </div>
                    <div class="d-grid gap-2" id="btn-box">
                        <input type="submit" value="비밀번호 변경하기" id="updatepw-btn" class="btn btn-primary">
                    </div>
                </div>
                <input type="hidden" name="id" value="${id}">
            </form>
        </div>
    </section>
</main>
<script>
    <c:if test="${result == 1}">
        alert('비밀번호가 수정되었습니다.');
        location.href="/movie/member/login.do"
    </c:if>

    <c:if test="${result == 0}">
        alert('비밀번호 변경에 실패했습니다..');
        history.back();
    </c:if>

    <c:if test="${not empty error}">
        alert('${error}');
        history.back();
    </c:if>
</script>
</body>
</html>
