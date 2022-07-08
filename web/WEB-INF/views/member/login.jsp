<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <style>
        h2, #labels > span {
            color: white;
            text-align: center;
        }

        h2 {
            margin-bottom: 10px;
        }

        #box {
            width: 500px;
            margin: 0 auto;
        }

        #login-box > input, h2 {
            margin-bottom: 20px;
        }

        #login-box > input, #login-btn {
            height: 50px;
        }

        #login-btn {
            margin: 5px 0;
        }

        #labels {
            margin: 5px auto;
        }

        #labels span a {
            /*font-size: 1.1em;*/
            margin-left: 40px;
            margin-right: 30px;
            color: white;
        }

        #login-error {
            margin: 10px 0;
            color: red;
            text-align: center;
        }
    </style>
</head>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp"%>
    <section>
        <div id="box">
            <h2>로그인</h2>
            <form action="/movie/member/login.do" method="post">
            <div id="login-box">
                <c:if test="${not empty loginError}">
                    <div id="login-error">
                        아이디 또는 비밀번호가 일치하지 않습니다.
                    </div>
                </c:if>
                <input type="text" name="id" id="id" class="form-control" placeholder="아이디를 입력해주세요." required>
                <input type="password" name="pw" id="pw" class="form-control" placeholder="비밀번호를 입력해주세요." required>
                <div class="d-grid gap-2">
                    <input type="submit" value="로그인" id="login-btn" class="btn btn-primary">
                </div>
            </div>
            </form>
            <div id="labels">
                <span><a href="/movie/member/findpw.do">비밀번호 찾기</a></span>
                <span><a href="/movie/member/findid.do">아이디 찾기</a></span>
                <span><a href="/movie/member/register.do">회원가입</a></span>
            </div>
        </div>
    </section>
</main>

</body>
</html>
