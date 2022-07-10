<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <link rel="stylesheet" href="/movie/asset/css/mygroup.css">
    <style>
        .table {
            color: white;
            text-align: center;
        }

        .table tr {

        }

        #addg {
            display: flex;
            justify-content: right;

        }

        #addg > button {
            margin-right: 10px;
        }

        #board > #table > tbody > tr > td {
            text-align: center;
        }

    </style>
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
                            <th>그룹명</th>
                            <th>그룹장</th>
                            <th>운영중/참여중/참여신청</th>
                            <th></th>
                        </tr>
                        <c:forEach items="${list}" var="dto">
                        <tr class="table-hover">
                            <td>${dto.rnum}</td>
                            <td>${dto.name}</td>
                            <td>${dto.groupKing}</td>
                            <c:if test="${dto.state == 1}">
                            <td>운영중</td>
                            </c:if>
                            <c:if test="${dto.state == 0}">
                            <td>참여중</td>
                            </c:if>
                            <c:if test="${dto.state == 2}">
                            <td>참여신청</td>
                            </c:if>

                            <c:if test="${(dto.state == 1) || (dto.state == 0)}">
                            <td><input type="button" class="btn btn-success btn-click" value="입장하기" onclick="location.href='/movie/group/grouplist.do?group=${dto.seq}'"></td>
                            </c:if>

                            <c:if test="${dto.state == 2}">
                            <td><input type="button" class="btn btn-danger btn-click" value="신청취소"></td>
                            </c:if>

                        </tr>
                        </c:forEach>

                        <c:if test="${list.size() == 0}">
                            <tr>
                                <td colspan="5">게시물이 없습니다.</td>
                            </tr>
                        </c:if>

                    </table>
                </div>
            </div>
            <div id="addg">
                <button type="submit" class="btn btn-primary" onclick="location.href='/movie/group/mygroup/creategroup.do'">그룹 생성하기</button>
            </div>


        </section>
    <footer>

    </footer>
</main>

<script>



</script>

</body>
</html>
