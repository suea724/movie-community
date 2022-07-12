<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
<style>

    #board {

        margin-left: 10px;
        margin-bottom: 20px;
        padding-left: 50px;
        padding-bottom: 20px;
        width: 1000px;
        background-color: #373737;

    }



    #content {

        margin-top: 30px;
        margin-bottom: 50px;


    }

    #btns {

        display: flex;
        justify-content: right;
        margin-right: 20px;

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
                    <li><a href="/movie/recruit/recruitlist.do">모집 게시판</a></li>

                </ul>
            </div>


            <div id="board" style="color: white;">
                <h2 style="margin-bottom: 30px; padding-top: 20px;">${dto.title}</h2>
                <div>${dto.nickname}(${dto.id})<span style="display: inline; padding-left: 600px;">${dto.regdate} 조회수: ${dto.readcount}</span></div>
                <hr>
                <div style="height: 300px; vertical-align: middle; color: white; display: inline" id="content" >${dto.content}</div>
                <hr style="margin-bottom: 50px;">


            <div class="btns" id="btns">


                <c:if test="${dto.id == auth.id}">
                <button class="btn btn-primary"
                        onclick="location.href='/movie/recruit/edit.do?seq=${dto.seq}';" style="margin-right: 10px;">
                    수정하기
                </button>

                <%--<button class="btn btn-primary"
                        onclick="location.href='/movie/recruit/del.do?seq=${dto.seq}';">
                    삭제하기
                </button>--%>

                <button type="button" class="btn btn-danger" onclick="deletePost();" >삭제하기</button>

                </c:if>

                <c:if test="${not empty auth}">
                    <c:if test="${dto.id != auth.id}">
                    <button class="btn btn-primary"
                            onclick="location.href='/movie/recruit/request.do?seq=${dto.seq}';">
                        신청하기
                    </button>
                    </c:if>
                </c:if>

                <hr>
                <%--댓글--%>

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
                    <input type="hidden" name="rseq" value="${dto.seq}">
                </form>
                <table class="comment">
                    <c:forEach items="${rlist}" var="rdto">
                        <tr>
                            <td style="width: 500px; color: black; background-color: #DDDD;">
                                <div style="display: flex; justify-content: left; font-weight: bold;"><span>${rdto.nickname}</span></div>
                                <div style="display: flex; justify-content: left;">${rdto.content}</div>
                                <div style="display: flex; justify-content: left;">
                                    <span>${rdto.regdate}</span>
                                </div>
                                <c:if test="${rdto.id == auth.id}">
                                    <div style="display: flex; justify-content: right;">
                                        <span class="btnspan"><a href="#!" onclick="delComment(${rdto.seq});">[삭제]</a></span>
                                        <span class="btnspan"><a href="#!" onclick="editComment(${rdto.seq});">[수정]</a></span>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>

                </table>
            </div>

        </div>
    </div>

    </section>
    <footer>

    </footer>
</main>

<script>


    let isEdit = false;
    function editComment(seq) {
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
            location.href='/movie/recruit/del.do?seq=${dto.seq}'
        }
    }



    function addComment() {
        $.ajax({
            type: 'POST',
            url: '/movie/recruit/addcomment.do',
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
                    <span class="btnspan"><a href="#!" onclick="delComment(\${result.seq});">[삭제]</a></span>
                    <span class="btnspan"><a href="#!" onclick="editComment(\${result.seq});">[수정]</a></span>
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
            url: '/movie/recruit/editcomment.do',
            data: $('#editCommentForm').serialize(),
            dataType: 'json',
            success: function(result) {
                if (result.result == "1") {
                    //수정된 댓글을 화면에 반영하기
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

    function delComment(seq) {

        let tr = $(event.target).parents('tr');
        if (confirm('delete?')) {
            $.ajax({
                type: 'POST',
                url: '/movie/recruit/delcomment.do',
                data: 'seq=' + seq,
                dataType: 'json',
                success: function(result) {
                    if (result.result == "1") {
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