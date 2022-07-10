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
            <div id="submenu">
                <ul>
                    <li><a href="/movie/group/mygroup/mygrouplist.do">내 그룹</a></li>
                    <li><a href="/movie/recruit/recruitlist.do">모집 게시판</a></li>

                </ul>
        </div>
            <div id="board">

                <c:if test="${map.isSearch == 'y'}">
                    <div style="color: white;">'${map.word}'으로 검색한 결과 총 ${list.size()}개의 게시물이 발견되었습니다.</div>
                </c:if>


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
                            <td><a href="/movie/recruit/view.do?seq=${dto.seq}" style="color:white;"> ${dto.title}</a> </td>
                            <td>${dto.nickname}</td>
                            <td>${dto.regdate}</td>
                            <td>${dto.readcount}</td>
                        </tr>

                    </c:forEach>
                    <c:if test="${list.size() == 0}">
                    <tr>
                        <td colspan="5">게시물이 없습니다.</td>
                    </tr>
                    </c:if>

                </table>


                <div style="text-align: center; display: flex">
                    ${pagebar}
                </div>



                <div>
                    <form method="GET" action="/movie/recruit/recruitlist.do">
                        <table class="search">
                            <tr>
                                <td>
                                    <select name="column" class="form-control">
                                        <option value="title">제목</option>
                                        <option value="content">내용</option>
                                        <option value="nickname">작성자</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="text" name="word" class="form-control" required>
                                </td>
                                <td>
                                    <button class="btn btn-primary">
                                        검색하기
                                    </button>
                                </td>
                            </tr>
                        </table>
                    </form>


                </div>


                <c:if test="${not empty auth}">
                <div class="btns" id="btns" style="display: flex; justify-content: right;">
                    <button class="btn btn-primary" onclick="location.href='/movie/recruit/add.do';">
                        작성하기
                    </button>
                </div>
                </c:if>

            </div>
        </div>

    </section>
    <footer>

    </footer>
</main>
<script>
    //검색 중 > 상태유지
/*    <c:if test="${map.isSearch == 'y'}">
        $('select[name=column]').val('${map.column}');
        $('input[name=word]').val('${map.word}');
    </c:if>*/


    $("#pagebar").change(function() {

        location.href = '/toy/board/list.do?page=' + $(this).val() + "&column=${map.column}&word=${map.word}";

    });

    $('#pagebar').val(${nowPage});

</script>




</body>












</html>
