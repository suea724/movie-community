<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp" %>
</head>
<style>
    #box {
        display: flex;
        width: 1000px;
        margin: 0 30% 50px 30px;
        justify-content: space-between;
    }

    #submenu {
        width: 200px;
        margin-right: 50px;
        position: relative;
        left: 0;
        top: 0;
        color: white;
    }

    #submenu ul > li {
        list-style: none;
        margin-left: 0;
    }

    #submenu ul > li > a {
        color: white;
    }

    #content {
        width: 900px;
        background-color: lightgrey;
        border-radius: 30px;
        padding: 50px;
        margin-bottom: 50px;
    }

    h2 {
        text-align: center;
        margin-bottom: 50px;
    }

    .image-container {
        display: inline-block;
        width: 150px;
        height: 150px;
        border-radius: 50%;
        overflow: hidden;
        position: relative;
        left: 0;
        right: 0;
        margin-right: 5px;
        padding: 20px;
    }

    .profile-img {
        postion: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        border-radius: 50%;
        object-fit: cover;
        margin: auto;
    }

    #profile-box {
        margin-bottom: 30px;
    }

    #profile-box > * {
        text-align: center;
    }

    #submenu #submenu-profile {
        display: block;
        margin: 0 auto;
    }

    table th {
        width: 200px;
    }

    table {
        width: 500px;
        margin: 0 auto;
    }

    table th, table td {
        padding: 10px;
    }

     table tr:last-child {
        padding-top: 30px;
    }

</style>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp" %>

    <section>

        <div id="box">
            <div id="submenu">
                <div id="profile-box">
                    <div class="image-container" id="submenu-profile">
                        <img src="/movie/asset/images/${auth.picture}" class="profile-img">
                    </div>
                    <h5>${auth.name}(${auth.nickname})</h5>
                    <h5>${auth.id}</h5>
                </div>
                <ul>
                    <li><a href="/movie/member/mypage/posts.do" id="myinfo">내 정보 조회</a></li>
                    <li><a href="/movie/member/mypage/comments.do" id="mypost">내 글 보기(${auth.postCnt})</a></li>
                    <li><a href="/movie/member/mypage.do" id="mycomment">내 댓글 보기(${auth.commentCnt})</a></li>
                    <li><a href="/movie/member/unregister.do" style="color: dimgray;">탈퇴하기</a></li>
                </ul>
            </div>

            <div id="content">
                <h2>비밀번호 변경하기</h2>
            <form action="/movie/member/mypage/updatepw.do" method="post" id="form">
            <table>
                <tr>
                    <th>
                        현재 비밀번호
                    </th>
                    <td>
                        <input type="password" name="currentPw" id="current-pw" class="form-control" required>
                    </td>
                </tr>
                <tr>
                    <th>
                        새로운 비밀번호
                    </th>
                    <td>
                        <input type="password" name="newPw" id="new-pw" class="form-control" required>
                    </td>
                </tr>
                <tr>
                    <th>
                        새로운 비밀번호 확인
                    </th>
                    <td>
                        <input type="password" name="checkPw" id="check-pw" class="form-control" required>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                            <div class="d-grid gap-2">
                            <input type="submit" value="변경 완료" id="update-btn" class="btn btn-secondary" required>
                            </div>
                    </td>
                </tr>
            </table>
            </form>
            </div>
        </div>

    </section>
    <footer>

    </footer>
</main>

<script>

    $('#myinfo').css('color', 'rgb(69, 149 , 140)');

    let pw = <c:out value="${auth.password}"/>

    $('#form').submit( function() {

        let currentPw = $('#current-pw').val();
        let newPw = $('#new-pw').val();
        let pwCheck = $('#check-pw').val();

        if (currentPw != pw) {
            alert('현재 비밀번호가 일치하지 않습니다.');
            return false;
        }

        if (newPw != pwCheck) {
            alert('새로운 비밀번호가 일치하지 않습니다.');
            return false;
        }

        $('#update-btn').submit();
    });

</script>

</body>
</html>
