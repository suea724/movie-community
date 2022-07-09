<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <link rel="stylesheet" href="/movie/asset/css/mygroup.css">
</head>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp"%>



        <section>
            <div id="selbox">
                <select class="form-select form-select-sm" style="width: 108px;">
                    <option value="manage">운영중</option>
                    <option value=in">참여중</option>
                    <option value="apply">참여신청</option>
                </select>
            </div>

            <div id="subboard">
                <div id="submenu">
                    <ul>
                        <li><a href="/movie/group/mygroup/mygrouplist.do">내 그룹</a></li>
                        <li><a href="#!">모집게시판</a></li>
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
                            <td>호러영화 매니아 그룹 모집</td>
                            <td>홍길동</td>
                            <td>운영중</td>
                            <td><input type="button" class="btn btn-success btn-click" value="입장하기"></td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>마녀2 재밌다</td>
                            <td>아무개</td>
                            <td>참여중</td>
                            <td><input type="button" class="btn btn-success btn-click" value="입장하기"></td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>마녀2 재밌다</td>
                            <td>하하하</td>
                            <td>참여신청</td>
                            <td><input type="button" class="btn btn-danger btn-click" value="신청취소"></td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>마녀2 재밌다</td>
                            <td>길동이</td>
                            <td>2022-07-05</td>
                            <td>100</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>마녀2 재밌다</td>
                            <td>길동이</td>
                            <td>2022-07-05</td>
                            <td>100</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>마녀2 재밌다</td>
                            <td>길동이</td>
                            <td>2022-07-05</td>
                            <td>100</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>마녀2 재밌다</td>
                            <td>길동이</td>
                            <td>2022-07-05</td>
                            <td>100</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>마녀2 재밌다</td>
                            <td>길동이</td>
                            <td>2022-07-05</td>
                            <td>100</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>마녀2 재밌다</td>
                            <td>길동이</td>
                            <td>2022-07-05</td>
                            <td>100</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>마녀2 재밌다</td>
                            <td>길동이</td>
                            <td>2022-07-05</td>
                            <td>100</td>
                        </tr>
                    </table>
                </div>
            </div>

            <div id="addg">
                <input type="button" value="그룹 생성하기" class="btn btn-primary" onclick="location.href='/movie/group/mygroup/creategroup.do'">
            </div>


            <%-- 페이지--%>
            <div style="text-align: center">
                <%--
                <select id="pagebar">
                    <c:forEach var="i" begin="1" end="${totalPage}">
                    <option value="${i}">${i}페이지</option>
                    </c:forEach>
                </select>
                 --%>
                <%--${pagebar}--%>
            </div>

            <%-- 검색--%>
            <div id="searchBox">
                <!-- 검색에 한하여 POST가 아닌 GET방식을 씀  -->
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
                                <input type="text" name="word" class="form-control" placeholder="검색어를 입력하시오." required>
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

<script>



</script>

</body>
</html>
