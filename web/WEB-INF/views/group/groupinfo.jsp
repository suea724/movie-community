<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Movie Community</title>
  <%@ include file="/WEB-INF/inc/asset.jsp"%>
  <style>

    section > div > #board > .tbl > tr > td:nth-child(odd) {
    text-align: left;


  }

  section > div > #board > .tbl > tr > td:nth-child(even) {
    text-align: right;

  }

  #board {
    width: 700px;
  }

  </style>
</head>
<body id="body">
<main>
  <%@ include file="/WEB-INF/inc/header.jsp"%>

  <section>
    <div id="subboard">
      <%@ include file="/WEB-INF/inc/groupmenu.jsp"%>
      <div id="board" style="width: 700px; padding: 20px">
        <table id="tblinfo" class="tbl" style="width: 660px">
          <tr></tr>
          <tr>
            <td style="text-align: left; padding: 10px 10px 0px 10px; font-size: 20px; font-weight: bold;">- 그룹명 -</td>
            <td style="text-align: left; padding: 10px 10px 0px 10px; font-size: 20px; font-weight: bold;">- 그룹 개설 날짜 -</td>
          </tr>

          <tr>
            <td style="text-align: left; padding: 10px 10px 20px 23px;">${dto.name}</td>
            <td style="text-align: left; padding: 10px 10px 20px 23px;">${dto.regdate}</td>
          </tr>
          <tr>
            <td style="text-align: left; padding: 0px 10px 10px 10px; font-size: 20px; font-weight: bold;">- 그룹 인원</td>
            <td style="text-align: left; padding: 0px 10px 10px 10px; font-size: 20px; font-weight: bold;">- 그룹장 -</td>
          </tr>
          <tr>
            <td style="text-align: left; padding: 0px 10px 20px 23px;">${dto.cnt}/${dto.recruitment} 명</td>
            <td style="text-align: left; padding: 01px 10px 20px 23px;">${dto.nickname}</td>
          </tr>
          <tr>
            <td style="text-align: left; padding: 0px 10px 5px 10px; font-size: 20px; font-weight: bold;">- 그룹 설명 -</td>
            <td style="text-align: left; padding: 0px 10px 5px 10px; font-size: 20px; font-weight: bold;">- 해시태그 -</td>
          </tr>
          <tr>
            <td style="text-align: left; padding: 5px 10px 20px 23px;">
              ${dto.info}
            </td>

            <td style="text-align: left; padding: 5px 10px 20px 23px;">
              <c:forEach items="${hlist}" var="hdto">
              #${hdto}
              </c:forEach>
            </td>
          </tr>

        </table>
      </div>
    </div>
    <div style="display: flex; justify-content: right">
      <c:if test="${groupId == auth.id}">
      <input type="button" class="btn btn-primary" id="editbtn" value="그룹 수정" onclick="location.href='/movie/group/mygroup/info/groupinfoedit.do?group=${dto.seq}&groupid=${groupId}';" style="margin: 5px 5px 0px 0px">
      <input type="button" class="btn btn-danger" id="delbtn" value="그룹 삭제" onclick="location.href='/movie/group/mygroup/info/groupinfoedit.do?group=${dto.seq}&groupid=${groupId}';" style="margin: 5px 335px 0px 0px">
      </c:if>
      <c:if test="${groupId != auth.id}">
        <input type="button" class="btn btn-danger" id="delbtn" value="그룹 탈퇴" onclick="location.href='그룹탈퇴';" style="margin: 5px 335px 0px 0px">
      </c:if>
    </div>


  </section>
  <footer>

  </footer>
</main>


</body>
</html>
