<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Movie Community</title>
  <%@ include file="/WEB-INF/inc/asset.jsp"%>
  <style>
    .tagify__input {
      height: 30px;
    }
  </style>
</head>
<body id="body">
<main>
  <%@ include file="/WEB-INF/inc/header.jsp"%>

  <section>
    <form method="post" action="/movie/group/groupadd.do">
      <div style="padding-bottom: 100px; padding-left: 50px; margin-left: 90px; margin-right: 240px; padding-top: 100px; background-color: #DDDD; border-radius: 20px; margin-bottom: 50px;">
        <div style="display: flex; margin-bottom: 15px">
          <input type="text" name="title" class="form-control" placeholder="제목을 입력하세요." style="width: 750px;" required>
        </div>
        <div style="width: 750px;">
          <input name='tags-disabled-user-input' class="form-control" placeholder='장르 해시태그를 추가하세요.'>
        </div>
        <div><textarea name="content" class="form-control" style="width: 750px; height: 600px; margin-top: 30px; resize: none;" placeholder="내용을 입력하세요." required></textarea></div>
        <div style="display: flex; justify-content: right; margin-top: 20px;">
          <input type="button" class="btn btn-secondary" value="돌아가기" onclick="history.back()">
          <input type="submit" class="btn btn-danger" value="글 등록" style="margin-left: 10px; margin-right: 70px;">
        </div>
      </div>

      <input type="hidden" name="group" value=${group}>
    </form>

  </section>
  <footer>

  </footer>
</main>

<script>
  var input = document.querySelector('input[name=tags-disabled-user-input]');

  let temp = [];

  <c:forEach items="${taglist}" var="tag">
    temp.push('${tag}');
  </c:forEach>

  new Tagify(input, {
    whitelist: temp,
    userInput: false
  })
</script>

</body>
</html>
