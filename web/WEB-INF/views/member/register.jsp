<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp" %>
</head>
<style>

    h2, h5 {
        color: white;
    }
    #box {
        width: 500px;
        margin: 0 auto;
        padding-bottom: 50px;
    }

    h2 {
        text-align: center;
    }

    form > div {
        margin-bottom: 10px;
    }

    #id-box > input, #nickname-box > input {
        display: inline;
    }

    #id, #nickname {
        width: 380px;
        margin-right: 10px;
    }

    #image_container {
        display: inline-block;
        width: 150px;
        height: 150px;
        border-radius: 50%;
        overflow: hidden;
        position: relative;
        left: 0;
        right: 0;
        margin-right: 5px;
        padding: 20px;
    }

    .profile-img {
        postion: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        border-radius: 50%;
        object-fit: cover;
        margin: auto;
    }

    #upload tr td:nth-child(1) {
        max-width: 250px;
    }

    #id-check-result {
        margin-top: 10px;
    }

    .tagify__input {
        height: 35px;
    }

    .error {
        color : red;
    }

</style>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp" %>

    <section>

        <div id="box">
            <div id="register-box">
                <form method="post" action="/movie/member/register.do" enctype="multipart/form-data">
                <h2>회원 가입</h2>
                <div id="id-box">
                    <h5>아이디</h5>
                    <input type="text" class="form-control" name="id" id="id" required>
                    <input type="button" class="btn btn-secondary" id="id-check" value="중복확인">
                    <div id="id-check-result" class="error"></div>
                </div>
                <div id="pw-box">
                    <h5>비밀번호</h5>
                    <input type="password" class="form-control" name="pw" id="pw" required>
                    <div id="pw-check-result" class="error"></div>
                </div>
                <div id="pw-check-box">
                    <h5>비밀번호 확인</h5>
                    <input type="password" class="form-control" name="pwcheck" id="pw-check" required>
                    <div id="is-same-pw" class="error"></div>
                </div>
                <div id="nickname-box">
                    <h5>닉네임</h5>
                    <input type="text" class="form-control" name="nickname" id="nickname" required>
                    <input type="button" class="btn btn-secondary" id="nickname-check" value="중복확인">
                    <div id="nickname-check-result" class="error"></div>
                </div>
                <div id="name-box">
                    <h5>이름</h5>
                    <input type="text" class="form-control" name="name" id="name" required>
                    <div id="name-check-result" class="error"></div>
                </div>
                <div id="tel-box">
                    <h5>전화번호</h5>
                    <input type="text" class="form-control" name="tel" id="tel" required>
                    <div id="tel-check-result" class="error"></div>
                </div>
                <div id="file-box">
                    <h5>프로필 사진 업로드</h5>
                    <table id="upload">
                        <tr>
                            <td>
                                <span id="image_container"><img src="/movie/asset/images/pic.png" class="profile-img"></span>
                            </td>
                            <td>
                                <input type="file" accept="image/*" onchange="setThumbnail(event);" name="profile" id="profile" class="form-control">
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="genre-box">
                    <h5>선호 장르</h5>
                    <input name='tags-disabled-user-input' class="form-control">
                </div>
                <div class="d-grid gap-2">
                <input type="submit" value="회원 가입" id="register-btn" class="btn btn-primary">
                </div>
                </form>
            </div>
        </div>
    </section>
    <footer>

    </footer>

    <script>

        var input = document.querySelector('input[name=tags-disabled-user-input]');

        let temp = [];

        <c:forEach items="${list}" var="tag">
        temp.push('${tag.hashtag}');
        </c:forEach>

        new Tagify(input, {
            whitelist: temp,
            userInput: false
        });

        let isValidId = false;
        let isValidNickname = false;

        $('#id-check').click(function() {
            $.ajax({
                url: '/movie/member/idcheck.do',
                type: 'POST',
                data: "id=" + $('#id').val(),
                dataType: 'json',
                success: function(result) {

                    if (result.result == "1") {
                        $('#id-check-result').text('중복된 아이디가 존재합니다.');
                        $('#id-check-result').css('color', 'red');
                        isValidId = false;
                    } else {
                        $('#id-check-result').text('사용 가능한 아이디입니다.');
                        $('#id-check-result').css('color','green');
                        isValidId = true;
                    }

                    if (isValidId && isValidNickname) {
                        $('#register-btn').removeAttr('disabled');
                    } else {
                        $('#register-btn').attr('disabled', true);
                    }
                },
                error: function(a, b, c) {
                    console.log(a, b, c);
                }
            });
        });

        $('#nickname-check').click(function() {
            $.ajax({
                url: '/movie/member/nicknamecheck.do',
                type: 'POST',
                data: "nickname=" + $('#nickname').val(),
                dataType: 'json',
                success: function(result) {

                    if (result.result == "1") {
                        $('#nickname-check-result').text('중복된 닉네임이 존재합니다.');
                        $('#nickname-check-result').css('color', 'red');
                        isValidNickname = false;
                    } else {
                        $('#nickname-check-result').text('사용 가능한 닉네임입니다.');
                        $('#nickname-check-result').css('color','green');
                        isValidNickname = true;
                    }

                    if (isValidId && isValidNickname) {
                        $('#register-btn').removeAttr('disabled');
                    } else {
                        $('#register-btn').attr('disabled', true);
                    }
                },
                error: function(a, b, c) {
                    console.log(a, b, c);
                }
            });
        });

        $('form').submit(function() {
            let id = $('#id').val();
            let pw = $('#pw').val();
            let pwCheck = $('#pw-check').val();
            let nickname = $('#nickname').val();
            let name = $('#name').val();
            let tel = $('#tel').val();

            if (!/^[A-Za-z]{1}[A-Za-z0-9]{3,15}$/.test($('#id').val())) {
                $('#id-check-result').css('color', 'red');
                $('#id-check-result').text("아이디는 영대소문자, 숫자를 포함한 4 ~ 16자로 입력해주세요.");
                return false;
            }

            if (!/^[A-Za-z0-9]{7,15}$/.test(pw)) {
                $('#pw-check-result').text("비밀번호는 영대소문자, 숫자를 포함한 8 ~ 16자로 입력해주세요.");
                return false;
            }

            if (pw != pwCheck) {
                $('#is-same-pw').text('입력하신 비밀번호와 일치하지 않습니다.');
                return false;
            }

            if (!/^[가-힣]{2,6}$/.test($('#nickname').val())) {
                $('#nickname-check-result').css('color', 'red');
                $('#nickname-check-result').text('닉네임은 2~6자 이내 한글로 입력해주세요.');
                return false
            }

            if (!/^[가-힣]{2,6}$/.test(name)) {
                $('#name-check-result').text('이름은 2~6자 이내 한글로 입력해주세요.');
                return false;
            }

            if (!/^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/.test(tel)) {
                $('#tel-check-result').text('전화번호는 '-'을 포함한 13자리로 입력해주세요.');
                return false;
            }

            $("#register-btn").submit();

        });

    </script>
</main>
</body>
</html>
