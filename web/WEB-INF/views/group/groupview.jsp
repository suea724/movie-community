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
          <li><a href="/movie/group/grouplist.do?group=${group}">그룹 게시판</a></li>
          <li><a href="">그룹 정보</a></li>
          <li><a href="">그룹원 목록</a></li>
          <c:if test="${auth.id == groupId}">
          <li><a href="">신청 목록</a></li>
          </c:if>
        </ul>
      </div>

      <div style="background-color: #DDDD; padding: 50px; width: 700px; margin-left: 25px;">
        <div><h1>${dto.title}</h1></div>
        <div style="display: flex; justify-content: space-between; margin-top: 20px;">
          <div><span>${dto.nickname}</span></div>
          <div><span>${dto.regdate}</span><span style="margin-left: 20px;">조회수 : ${dto.readcount}</span></div>
        </div>

        <hr>

        <div style="margin-top: 30px; margin-bottom: 30px; display: flex">
          <c:forEach items="${list}" var="tag">
            <form method="get" action="/movie/group/grouplist.do" style="margin-left: 3px;">
              <input type="submit" name="tag" value="${tag}">
              <input type="hidden" name="group" value="${group}">
            </form>
          </c:forEach>

        </div>

        <div>${dto.content}</div>

        <div style="display: flex; justify-content: center">
          <button style="border-radius: 100%; width: 50px; height: 50px; margin-left: 5px;" onclick="goodchoice(0);"><i style="color: #27aae2;" class="fa-solid fa-thumbs-up"></i><span id="good">${glist.get(0)}</span></button>
          <input type="hidden" name="seq" value="${dto.seq}">
          <input type="hidden" name="goodbad" value="0">

          <button style="border-radius: 100%; width: 50px; height: 50px; margin-left: 5px;" onclick="goodchoice(1);"><i style="color: red;" class="fa-solid fa-thumbs-down"></i><span id="bad">${glist.get(1)}</span></button>
          <input type="hidden" name="seq" value="${dto.seq}">
          <input type="hidden" name="goodbad" value="1">
        </div>



        <c:if test="${dto.id == auth.id}">
          <div style="display: flex; justify-content: right">
            <button type="button" class="btn btn-primary" style="margin-left: 5px;" onclick="location.href='/movie/group/groupedit.do?seq=${dto.seq}'">수정하기</button>
            <button type="button" class="btn btn-danger" onclick="deletePost();">삭제하기</button>
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
                  쓰기
                </button>
              </td>
            </tr>
          </table>
          <input type="hidden" name="pseq" value="${dto.seq}">
        </form>
        <table class="comment">
          <c:forEach items="${clist}" var="cdto">
            <tr>
              <td style="width: 500px; color: black; background-color: #DDDD;">
                <div style="display: flex; justify-content: left; font-weight: bold;"><span>${cdto.nickname}</span></div>
                <div style="display: flex; justify-content: left;">${cdto.content}</div>
                <div style="display: flex; justify-content: left;">
                  <span>${cdto.regdate}</span>
                </div>
                <c:if test="${cdto.id == auth.id}">
                  <div style="display: flex; justify-content: right;">
                    <span class="btnspan"><a href="#!" onclick="delcomment(${cdto.seq});">[삭제]</a></span>
                    <span class="btnspan"><a href="#!" onclick="editcomment(${cdto.seq});">[수정]</a></span>
                  </div>
                </c:if>
              </td>
            </tr>
          </c:forEach>

        </table>
      </div>


    </div>

  </section>
  <footer>

  </footer>
</main>

<script>
  function goodchoice(goodbad) {

    $.ajax({
      type: 'POST',
      url: '/movie/group/groupgoodbad.do',
      data: 'goodbad=' + goodbad + '&seq=' + ${dto.seq},
      dataType: 'json',
      success: function(result) {

        if (result.result == "1") {

          $("#good").html(result.good);
          $("#bad").html(result.bad);

        }

      },
      error: function (a,b,c) {
        console.log(a,b,c);
      }
    });

  }

  let isEdit = false;

  function editcomment(seq) {

    if (!isEdit) {

      const tempStr = $(event.target).parent().parent().prev().prev().text();

      $(event.target).parents('tr').after(temp);

      isEdit = true;

      $(event.target).parents('tr').next().find('textarea').val(tempStr);
      $(event.target).parents('tr').next().find('input[name=seq]').val(seq);
    }

  }
  const temp = `<tr id='editRow' style="background-color: #CDCDCD;">
						<td>
							<form id="editCommentForm">
							<table class="tblEditComment">
								<tr>
									<td>
										<textarea style="resize: none; width: 350px" class="form-control" name="content" required id="txtcontent"></textarea>
									</td>
									<td>
                                        <button class="btn btn-primary btn-sm" type="button"
                                            onclick="editComment();">
											수정하기
										</button>
										<button class="btn btn-secondary btn-sm" type="button"
											onclick="cancelForm();">
											취소하기
										</button>
									</td>
								</tr>
							</table>

							<input type="hidden" name="seq">
							</form>
						</td>
					</tr>`;

  function cancelForm() {
    $('#editRow').remove();
    isEdit = false;
  }


  function deletePost() {
    if(confirm('삭제하시겠습니까?')) {

      location.href='/movie/group/groupdel.do?group=${group}&seq=${dto.seq}'

    }
  }

  function addComment() {


    $.ajax({
      type: 'POST',
      url: '/movie/group/groupaddcomment.do',
      data: $('#addCommentForm').serialize(),
      dataType: 'json',
      success: function(result) {
        if (result.result == "1") {
          //성공 > 새로 작성된 댓글을 목록에 반영하기

          let temp = `<tr>
              <td style="width: 500px; color: black; background-color: #DDDD;">
                <div style="display: flex; justify-content: left; font-weight: bold;"><span>\${result.nickname}</span></div>
                <div style="display: flex; justify-content: left;">\${$('[name=content]').val()}</div>
                <div style="display: flex; justify-content: left;">
                  <span>\${result.regdate}</span>
                </div>
                  <div style="display: flex; justify-content: right;">
                    <span class="btnspan"><a href="#!" onclick="delcomment(\${result.seq});">[삭제]</a></span>
                    <span class="btnspan"><a href="#!" onclick="editcomment(\${result.seq});">[수정]</a></span>
                  </div>
              </td>
            </tr>`;


          if ($('.comment tbody').length == 0) {
            $('.comment').append('<tbody></tbody>');
          }

          $('.comment tbody').prepend(temp);

          $('[name=content]').val('');


          $('.table.comment td').mouseover(function() {
            $(this).find('.btnspan').show();
          });

          $('.table.comment td').mouseout(function() {
            $(this).find('.btnspan').hide();
          });


        } else {
          //실패
          alert('failed');
        }
      },
      error: function(a,b,c) {
        console.log(a,b,c);
      }
    });

  }

  function editComment() {

    $.ajax({

      type: 'POST',
      url: '/movie/group/groupeditcomment.do',
      data: $('#editCommentForm').serialize(),
      dataType: 'json',
      success: function(result) {

        if (result.result == "1") {

          //수정된 댓글을 화면에 반영하기
          //$('textarea[name=content]').val()
          $('#editRow').prev().children().eq(0).children().eq(1).text($('#txtcontent').val());

          $('#editRow').remove();

          isEdit = false;

        } else {
          alert('failed');
        }

      },
      error: function(a,b,c) {
        console.log(a,b,c);
      }

    });

  }

  function delcomment(seq) {

    //$(event.target).parents('tr').remove();
    let tr = $(event.target).parents('tr');


    if (confirm('delete?')) {

      $.ajax({

        type: 'POST',
        url: '/movie/group/groupdelcomment.do',
        data: 'seq=' + seq,
        dataType: 'json',
        success: function(result) {

          if (result.result == "1") {

            //$(event.target).parents('tr').remove();
            tr.remove();

          } else {
            alert('failed');
          }

        },
        error: function(a,b,c) {
          console.log(a,b,c);
        }

      });

    }

  }

</script>


</body>
</html>
