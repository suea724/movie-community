<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp" %>
    <link rel="stylesheet" href="/movie/asset/css/mypage-submenu.css">
</head>
<style>
    #box {
        display: flex;
        width: 1000px;
        margin: 0 30% 50px 30px;
        justify-content: space-between;
    }

    #mypage {
        width: 900px;
        background-color: lightgrey;
        border-radius: 30px;
        padding: 50px;
        margin-bottom: 50px;
        !important; color: black;
    }

    h2 {
        text-align: center;
    }

    #mypage > #tbl {
        width: 500px;
        margin: 50px auto;
    }

    #mypage > #tbl tr:last-child {
        border: none;
    }

    #mypage > #tbl th {width: 150px;}


    #mypage > #tbl tr {
        height: 40px;
    }

    #mypage .line  td {
        padding: 0;
    }

    #profile-row {
        background-color: lightgrey;
    }

    .tagify__input {
        height: 30px;
    }

    section > #box > #mypage > table tr th, section > #box > #mypage > table tr td {
        color: black;
    }

</style>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp" %>

    <section>

        <div id="box">
            <%@ include file="submenu.jsp"%>

            <div id="mypage">
                <h2>내 정보</h2>

                <table id="tbl">
                    <tr id="profile-row">
                        <th>프로필 사진</th>
                        <td colspan="2">
                            <div class="image-container">
                            <img src="/movie/asset/images/${auth.picture}" class="profile-img" id="profile">
                            </div>
                        </td>
                    </tr>
                    <tr class="line">
                        <td colspan="3"><hr></td>
                    </tr>
                    <tr>
                        <th>회원등급</th>
                        <td><span class="badge rounded-pill bg-warning text-dark">Lv. ${auth.grade}</span></td>
                    </tr>
                    <tr class="line">
                        <td colspan="3"><hr></td>
                    </tr>
                    <tr>
                        <th>이름</th>
                        <td colspan="2">${auth.name}</td>
                    </tr>
                    <tr class="line">
                        <td colspan="3"><hr></td>
                    </tr>
                    <tr id="nickname-row">
                        <th>닉네임</th>
                        <td class="nickname" id="org-nickname">${auth.nickname}</td>
                        <td><input type="button" value="수정" class="btn btn-secondary btn-sm" id="update-nickname-btn"></td>
                    </tr>
                    <tr class="line">
                        <td colspan="3"><hr></td>
                    </tr>
                    <tr id="tel-row">
                        <th>전화번호</th>
                        <td class="tel" id="org-tel">${auth.tel}</td>
                        <td><input type="button" value="수정" class="btn btn-secondary btn-sm" id="update-tel-btn"></td>
                    </tr>
                    <tr class="line">
                        <td colspan="3"><hr></td>
                    </tr>
                    <tr>
                        <th>선호 장르</th>
                        <td colspan="2">
                            <c:if test="${not empty tagsValue}">
                                <input name='tags4' readonly value='${tagsValue}'>
                            </c:if>
                            <c:if test="${empty tagsValue}">
                                <div>선호 장르가 없습니다.</div>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" class="line"><hr></td>
                    </tr>
                    <tr>
                        <th>비밀번호</th>
                        <td colspan="2">
                            <div class="d-grid gap-2">
                            <button class="btn btn-secondary" type="button" onclick="location.href='/movie/member/mypage/updatepw.do'">수정하기</button>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </section>
    <footer>

    </footer>
</main>

<script>

    $('#myinfo').css('color', 'rgb(69, 149 , 140)');

    var input = document.querySelector('input[name=tags4]'),
    tagify = new Tagify(input);


    $(document).on("click","#update-nickname-btn", function() {

        // alert($('#nickname-row').html());

        let temp = '';

        temp += '<th>';
        temp += '닉네임';
        temp += '</th>';
        temp += '<td>';
        temp += '<input type="text" name="nickname" value="'+ $('#org-nickname').html() +'" class="form-control" id="new-nickname">';
        temp += '</td>';
        temp += '<td>';
        temp += '<input type="button" value="수정완료" class="btn btn-primary btn-sm" id="nickname-complete-btn">';
        temp += '</td>';

        $('#nickname-row').html('');
        $('#nickname-row').append(temp);

        $('#nickname-complete-btn').click(function () {
            $.ajax({
                url: '/movie/member/mypage/updatenickname.do',
                type: 'post',
                dataType: 'json',
                data: 'nickname=' + $('#new-nickname').val(),
                success: function(result) {

                    if (result.nickname != null) {
                        let temp = '';

                        temp += '<th>';
                        temp += '닉네임';
                        temp += '</th>';
                        temp += '<td id="org-nickname">' + result.nickname + '</td>';
                        temp += '<td>';
                        temp += '<input type="button" value="수정" class="btn btn-secondary btn-sm" id="update-nickname-btn">';
                        temp += '</td>';

                        $('#nickname-row').html('');
                        $('#nickname-row').append(temp);
                    } else {
                        alert('닉네임 변경 실패');
                    }

                },
                error: function (a, b, c) {
                    console.log(a, b, c);
                }

            });
        });

    });

    $(document).on("click","#update-tel-btn", function() {

        let temp = '';

        temp += '<th>';
        temp += '전화번호';
        temp += '</th>';
        temp += '<td>';
        temp += '<input type="text" name="tel" value="'+ $('#org-tel').html() +'" class="form-control" id="new-tel">';
        temp += '</td>';
        temp += '<td>';
        temp += '<input type="button" value="수정완료" class="btn btn-primary btn-sm" id="tel-complete-btn">';
        temp += '</td>';

        $('#tel-row').html('');
        $('#tel-row').append(temp);

        $('#tel-complete-btn').click(function () {
            $.ajax({
                url: '/movie/member/mypage/updatetel.do',
                type: 'post',
                dataType: 'json',
                data: 'tel=' + $('#new-tel').val(),
                success: function(result) {

                    if (result.tel != null) {
                        let temp = '';

                        temp += '<th>';
                        temp += '전화번호';
                        temp += '</th>';
                        temp += '<td id="org-tel">' + result.tel + '</td>';
                        temp += '<td>';
                        temp += '<input type="button" value="수정" class="btn btn-secondary btn-sm" id="update-tel-btn">';
                        temp += '</td>';

                        $('#tel-row').html('');
                        $('#tel-row').append(temp);
                    } else {
                        alert('전화번호 변경 실패');
                    }

                },
                error: function (a, b, c) {
                    console.log(a, b, c);
                }

            });
        });
    });

</script>

</body>
</html>
