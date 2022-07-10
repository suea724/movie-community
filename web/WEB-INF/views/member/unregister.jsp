<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp" %>
</head>
<body id="body">
<style>
    #box {
        width: 900px;
        background-color: lightgrey;
        padding: 30px;
        border-radius: 10px;
        margin: 0 auto 50px auto;
    }

    h2, p, #check-msg {
        margin-bottom: 50px;
    }

    #check-msg {
        text-align: center;
    }

    #check-member {
        display: flex;
        justify-content: space-between;
    }

    #unregister {
        margin-top: 20px;
    }

</style>
<main>
    <%@ include file="/WEB-INF/inc/header.jsp" %>

    <section>
        <div id="box">
            <h2>회원 탈퇴</h2>
            <p>
                회원 탈퇴 전, 유의사항을 확인해 주시기 바랍니다. <br>
                - 회원 탈퇴시 영화 커뮤니티 이용이 불가합니다. <br>
                - 회원 탈퇴 후 커뮤니티에 작성하신 글, 댓글은 삭제되지 않으며 글, 댓글 삭제를
                원하시는 경우에는 먼저 해당 게시물을 삭제한 후 탈퇴를 신청하시기 바랍니다. <br>
                - 아이디 제외 모든 개인 정보는 파기됩니다. <br>
                - 회원 탈퇴 후 2년동안은 동일 아이디로 재가입이 불가능합니다. <br>
            </p>
            <div id="check-msg">
                <input type="checkbox" name="check" id="check" class="form-check-input">
                <label for="check">상기 회원 탈퇴 시 처리사항 안내를 확인하였음에 동의합니다.</label>
            </div>
            <form method="post" action="/movie/member/unregister.do">
            <div id="check-member">
                <span>
                    <label>이름<input type="text" name="name" class="form-control" required></label>
                </span>
                <span>
                    <label>아이디<input type="text" name="id" class="form-control" required></label>
                </span>
                <span>
                     <label>비밀번호<input type="password" name="pw" class="form-control" required></label>
                </span>
                <span>
                    <input type="submit" value="탈퇴하기" class="btn btn-secondary" id="unregister">
                </span>
            </div>
            </form>
        </div>

    </section>
    <footer>

    </footer>
</main>

<script>

    <c:if test="${not empty error}">
        alert('${error}');
    </c:if>

    $("#unregister").attr("disabled", true);
    $("#check").on('click',function(){
        var chk = $('input:checkbox[id="check"]').is(":checked");
        if(chk){
            $("#unregister").removeAttr('disabled');
        }else{
            $("#unregister").attr("disabled", true);
        }
    });

    $('#unregister').click(function() {
        if (confirm('정말 탈퇴하시겠습니까?')) {
            $('form').submit();
        }
    });

</script>
</body>
</html>
