<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <link rel="stylesheet" href="/movie/asset/css/mygroup.css">
    <style>
        #cGroup > tbody > tr {

        }

        #cGroup > tbody > tr > td {
            text-align: left;
            padding: 3px;
        }

        #cGroup > tbody > tr:nth-child(4) > td, #cGroup > tbody > tr:nth-child(6) > td, #cGroup > tbody > tr:nth-child(8) > td, #cGroup > tbody > tr:nth-child(10) > td {

            padding-bottom: 10px;
        }

        #cGroup > tbody > tr:nth-child(4) > td {
            color: red;
        }


        #cGroup > tbody > tr:nth-child(3) > td:nth-child(1) > input[type=text] {
            width: 400px;
            display: inline-block;
            margin-right: 10px;
        }
        #cGroup > tbody > tr:nth-child(3) > td:nth-child(1) > input[type=button] {
            width: 100px;
            display: inline-block;
            margin-bottom: 4px;
        }


        #cGroup > tbody > tr > td > textarea {
            width: 800px;
            height: 300px;
            resize: none;
        }

        #cGroup > tbody > tr:nth-child(3) > td:nth-child(1) > input[type=text] {
            width: 400px;
            display: inline-block;
            margin-right: 10px;
        }

        #cGroup > tbody > tr:nth-child(9) > td:nth-child(1) > input[name=tags-disabled-user-input] {
            padding-top: 0px;
            padding-bottom: 0px;
            height: 50px;
            width: 400px;
            display: inline-block;
            margin-right: 10px;
        }

        #cGroup > tbody > tr:nth-child(9) > td:nth-child(1) > tags {
            width: 400px;
            display: inline-block;
            margin-right: 10px;
        }

        #cGroup > tbody > tr:nth-child(11) > td:nth-child(1) > input[type=number] {
            width: 80px;
        }

        #createBox {
            display: flex;
            justify-content: right;
            margin-right: 35px;
            margin-top: 10px;
        }

        #yes {
            display: none;
        }

        #no {
            display: none;
        }

        .tblCGroup {
            color: white;
            margin: 15px 0px 20px 15px ;

        }

        .tagify__input {
            height: 30px;
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
                    <li><a href="#!">모집게시판</a></li>
                </ul>
            </div>
            <div id="board">

                <form method="POST" action="/movie/group/mygroup/creategroup.do">

                    <table class="tblCGroup" id="cGroup" style="width: 1000px;">
                        <tr></tr>
                        <tr><td>- 그룹명 -</td></tr>
                        <tr>
                            <td><input type="text" class="form-control" name="gname" id="gname" required><input type="button" class="btn btn-primary" name="nameCheck" id="nameCheck" value="중복 검사" onclick="check()"></td>
                        </tr>
                        <tr><td id="yes">사용 가능한 그룹명입니다.</td></tr>
                        <tr><td id="no">이미 존재하는 그룹명입니다.</td></tr>
                        <tr><td>- 그룹 설명 -</td></tr>
                        <tr>
                            <td><textarea name="content" class="form-control" required></textarea></td>
                        </tr>
                        <tr><td>- 해시태그 -</td></tr>
                        <tr>
                            <%--<td><input type="text" name="tags" class="form-control" required></td>--%>
                            <td><input name='tags-disabled-user-input' class="form-control" placeholder='장르를 선택하세요.'></td>
                        </tr>
                        <tr><td>- 그룹 인원 -</td></tr>
                        <tr>
                            <td><input type="number" name="people" class="form-control" min="30" max="300" value="30" required></td>
                        </tr>
                    </table>
                    <div id="hidesubmit"><button type="submit" class="btn btn-primary" id="hideBtn" hidden></button></div>
                </form>
            </div>
        </div>
        <div id="createBox"><button type="submit" class="btn btn-primary" id="createBtn" onclick="subBtn()" disabled>생성하기</button></div>


    </section>
    <footer>

    </footer>
</main>

<script>

    function check() {
        //alert($('#gname').val());

        let gname = $('#gname').val();
        event.target.blur();

        $.ajax({
            type:'GET',
            url:'/movie/group/mygroup/groupnamecheck.do',
            data: 'gname=' + gname,
            dataType: 'json',
            success: function(result) {

                if (result.result == '1') {

                    $('#yes').attr("style", "display:block; color:yellowgreen;");
                    $('#no').attr("style", "display:none");
                    $('#createBtn').attr("disabled", false);

                } else {
                    $('#no').attr("style", "display:block;");
                    $('#yes').attr("style", "display:none;");
                }

            },
            error: function(a,b,c) {
                console.log(a,b,c);
            }
        });

    }

    function subBtn() {

        $('#hideBtn').click();

    }


        //해시태그
       /*const obj = {
            dropdown: {
                classname: 'tags-look',
                enabled: 0,
                closeOnSelect: false
            }
        };*/

        <%--let temp = [];--%>

        <%--<c:forEach items="${taglist}" var="tag">--%>
        <%--temp.push('${tag}');--%>
        <%--</c:forEach>--%>

        <%--obj.whitelist = temp;--%>

        <%--$('input[name=tags]').tagify(obj);--%>



        var input = document.querySelector('input[name=tags-disabled-user-input]');

        let temp = [];

        <c:forEach items="${taglist}" var="tag">
        temp.push('${tag}');
        </c:forEach>

        new Tagify(input, {
            whitelist: temp,
            userInput: false
        });


</script>

</body>
</html>
