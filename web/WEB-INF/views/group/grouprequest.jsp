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
      <%@ include file="/WEB-INF/inc/groupmenu.jsp"%>
      <div id="board">
        <table class="table" style="width: 1000px">
          <tr>
            <th>번호</th>
            <th>신청자</th>
            <th>신청날짜</th>
            <th></th>
          </tr>
          <c:forEach items="${list}" var="dto">
            <tr>
              <td>${dto.num}</td>
              <td style="text-align: center">${dto.nickname}(${dto.id})</td>
              <td>${dto.regdate}</td>
              <td>
                <form id="requestForm">
                  <input name="yesNo" type="button" value="수락" onclick="groupRequest(0, ${dto.seq}, ${group}, '${dto.id}');">
                  <input name="yesNo" type="button" value="거절" onclick="groupRequest(1, ${dto.seq}, ${group}, '${dto.id}');">
                </form>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>

    <div style="justify-content: center; display: flex; margin-top: 30px;">
      ${pagebar}
    </div>

  </section>
  <footer>

  </footer>
</main>
<script>
  function groupRequest(yesNo, seq, group, id) {

    let temp = $(event.target).parent().parent().parent();

    $.ajax({

      type: 'POST',
      url: '/movie/group/grouprequest.do',
      data: 'yesNo=' + yesNo + '&seq=' + seq + '&group=' + group + '&id=' + id,
      dataType: 'json',
      success: function(result) {
        if (result.result == 1) {
          temp.remove();
        }
      },
      error: function(a,b,c) {
        console.log(a,b,c);
      }
    });
  }
</script>


</body>
</html>
