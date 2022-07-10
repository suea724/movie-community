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
        <div id="subboard" style="justify-content: center;">
            <div id="board" >
                <table class="table-bordered" style="width: 1000px">
                    <tr>
                        <td colspan="3">${dto.title}</td>
                    </tr>

                    <tr>
                        <td style="width: 600px; text-align: left"> <span class="badge rounded-pill bg-warning">Lv.${auth.grade}</span> ${dto.nickname}(${dto.id})</td>
                        <td style="width: 250px; text-align: center;">${dto.regdate}</td>
                        <td style="width: auto; text-align: center;">조회수 : ${dto.readcount}</td>
                    </tr>

                    <tr>
                        <td colspan="3">
                            <c:forEach items="${list}" var="hash">
                                <input type="button" value="${hash}">
                            </c:forEach>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="3">${dto.content}</td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <form action="">
                                <button>좋아요</button>
                            </form>
                            <form action="">
                                <button>싫어요</button>
                            </form>
                        </td>
                    </tr>
                    <c:if test="${dto.id == auth.id}">
                    <tr>
                        <td colspan="3">
                            <button class="btn btn-primary" onclick="location.href='/movie/main/edit.do?seq=${dto.seq}';">수정하기</button>
                            <button class="btn btn-secondary" onclick="del();">삭제하기</button>
                        </td>
                    </tr>
                    </c:if>
                </table>
            </div>
        </div>

        <hr>

        <%--댓글 쓰기 창--%>
        <form id="addCommentForm">
            <table class="tblAddComment">
                <tr>
                    <td>
                        <textarea style="resize: none; width: 500px;" class="form-control" name="comment" required></textarea>
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

        <%--댓글--%>
        <div id="contentArea" style="display: flex; justify-content: center; margin-top: 30px;">
            <table class="comment table-bordered" style="color: white; width: 1000px;">
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



    </section>
    <footer>

    </footer>
</main>
<script>
    function del() {
        if(confirm('삭제하시겠습니까?')) {
            location.href='/movie/main/del.do?seq=${dto.seq}';
        }
    }

    function addComment() {


        $.ajax({
            type: 'POST',
            url: '/movie/main/addcomment.do',
            data: $('#addCommentForm').serialize(),
            dataType: 'json',
            success: function(result) {
                //alert(result.result);
                if (result.result == "1") {
                    //성공 > 새로 작성된 댓글을 목록에 반영하기
                    alert("dddddd");
                    let temp = `<tr>
              <td style="width: 500px; color: black; background-color: #DDDD;">
                <div style="display: flex; justify-content: left; font-weight: bold;"><span>\${result.nickname}</span></div>
                <div style="display: flex; justify-content: left;">\${$('[name=comment]').val()}</div>
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

                    $('[name=comment]').val('');


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

    function editComment() {

        $.ajax({

            type: 'POST',
            url: '/movie/main/editcomment.do',
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

    function cancelForm() {
        $('#editRow').remove();
        isEdit = false;
    }

    function delcomment(seq) {


        let tr = $(event.target).parents('tr');

        if(confirm('삭제하시겠습니까?')) {
            $.ajax({
                type: 'POST',
                url: '/movie/main/delcomment.do',
                data: 'seq=' + seq,
                dataType: 'json',
                success: function(result) {
                    if(result.result == 1) {
                        tr.remove();
                    } else {
                        alert('삭제가 실패하였습니다.');
                        history.back();
                    }
                },
                error: function(a, b, c) {
                    console.log(a, b, c);
                }
            });
        }
    }
</script>

</body>
</html>
