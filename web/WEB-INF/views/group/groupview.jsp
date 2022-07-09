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
          <li><a href="/movie/group/grouplist.do?group=${group}">전체게시판</a></li>
          <li><a href="">그룹 정보</a></li>
          <li><a href="">그룹원 목록</a></li>
          <li><a href="">신청 목록</a></li>
        </ul>
      </div>

      <div style="background-color: #DDDD; padding: 50px; width: 700px; margin-left: 25px;">
        <div><h1>${dto.title}</h1></div>
        <div style="display: flex; justify-content: space-between; margin-top: 20px;">
          <div><span class="badge rounded-pill bg-warning">Lv.${auth.grade}</span><span>${dto.nickname}</span></div>
          <div><span>${dto.regdate}</span><span style="margin-left: 20px;">조회수 : ${dto.readcount}</span></div>
        </div>

        <hr>

        <div style="margin-top: 30px; margin-bottom: 30px;">
          <c:forEach items="${list}" var="tag">
            <button>${tag}</button>
          </c:forEach>

        </div>

        <div>${dto.content}</div>

        <div style="display: flex; justify-content: center">
          <button style="border-radius: 100%; width: 50px; height: 50px; margin-left: 5px;"><i style="color: #27aae2;" class="fa-solid fa-thumbs-up"></i>${dto.good}</button> <button style="border-radius: 100%; width: 50px; height: 50px; margin-left: 5px;"><i style="color: red;" class="fa-solid fa-thumbs-down"></i>${dto.bad}</button>
        </div>



        <c:if test="${dto.id == auth.id}">
          <div style="display: flex; justify-content: right">
            <button type="button" class="btn btn-primary" style="margin-left: 5px;">수정하기</button>
            <button type="button" class="btn btn-danger">삭제하기</button>
          </div>
        </c:if>

        <hr>

        <form id="addCommentForm">
          <table class="tblAddComment">
            <tr>
              <td>
                <textarea style="resize: none; width: 500px;" class="form-control" name="content" required></textarea>
              </td>
              <td>
                <button class="btn btn-primary" type="button"
                        onclick="addComment();">
                  <i class="fas fa-pen"></i>
                  쓰기
                </button>
              </td>
            </tr>
          </table>
          <input type="hidden" name="pseq" value="${dto.seq}">
        </form>
      </div>


    </div>

  </section>
  <footer>

  </footer>
</main>


</body>
</html>
