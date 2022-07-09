<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <link rel="stylesheet" href="/movie/asset/css/mygroup.css">
    <style>



        #cGroup > tbody > tr > td {
            text-align: left;
        }

        #cGroup > tbody > tr:nth-child(4) > td {
            color: red;
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
                    <li><a href="/movie/group/mygroup/mygrouplist.do">내 그룹</a></li>
                    <li><a href="#!">모집게시판</a></li>
                </ul>
            </div>
            <div id="board">
                <table class="tblCGroup" id="cGroup" style="width: 1000px;">
                    <tr></tr>
                    <tr><td>- 그룹명 -</td></tr>
                    <tr>
                        <td><input type="text" class="form-control" name="gname" id="gname" required></td>
                        <td><input type="button" class="btn btn-primary" name="nameCheck" id="nameCheck" value="중복 검사"></td>
                    </tr>
                    <tr><td >이미 존재하는 그룹명입니다.</td></tr>
                    <tr><td>- 그룹 설명 -</td></tr>
                    <tr>
                        <td><textarea name="content" class="form-control" required></textarea></td>
                    </tr>
                    <tr><td>- 해시태그 -</td></tr>
                    <tr>
                        <td><input type="text" name="tags"></td>
                    </tr>
                    <tr><td>- 그룹 인원 -</td></tr>
                    <tr>
                        <td><input type="number" min="30" max="300" value="30" required></td>
                    </tr>

                </table>
            </div>
        </div>

    </section>
    <footer>

    </footer>
</main>


</body>
</html>
