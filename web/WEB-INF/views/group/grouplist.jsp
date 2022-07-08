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
            <div id="submenu">
                <ul>
                    <li><a href="">전체게시판</a></li>
                    <li><a href="">그룹 정보</a></li>
                    <li><a href="">그룹원 목록</a></li>
                    <li><a href="">신청 목록</a></li>
                </ul>
            </div>
            <div id="board">
                <table class="table" style="width: 1000px">
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성날짜</th>
                        <th>조회수</th>
                    </tr>
                    <tr class="table-hover">
                        <td>1</td>
                        <td>마녀2 재밌다</td>
                        <td>길동이</td>
                        <td>2022-07-05</td>
                        <td>100</td>
                    </tr>
                </table>
            </div>
        </div>
        <div id="add" style="display: flex; justify-content: right"><input type="button" class="btn btn-danger" value="글 쓰기" onclick="location.href='/movie/group/add.do';" style="margin-top: 20px; margin-right: 30px"></div>

        <div style="justify-content: center; display: flex">
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="#"><</a></li>
                <li class="page-item"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#">3</a></li>
                <li class="page-item"><a class="page-link" href="#">></a></li>
            </ul>
        </div>

        <div style="display: flex; justify-content: center">
            <form method="GET" action="/toy/board/list.do">
                <table class="search">
                    <tr>
                        <td>
                            <select name="column" class="form-control">
                                <option value="subject">제목</option>
                                <option value="content">내용</option>
                                <option value="name">이름</option>
                            </select>
                        </td>
                        <td>
                            <input type="text" name="word" class="form-control" required>
                        </td>
                        <td>
                            <button class="btn btn-primary">
                                검색하기
                            </button>

                            <c:if test="${map.isSearch == 'y'}">
                                <button class="btn btn-secondary" type="button"
                                        onclick="location.href='/toy/board/list.do';">
                                    중단하기
                                </button>
                            </c:if>
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
