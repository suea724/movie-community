<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Movie Community</title>
    <%@ include file="/WEB-INF/inc/asset.jsp"%>
    <style>




        #board {

            margin-left: 20px;
            margin-bottom: 50px;
            background-color: #f8f8f8;
            padding-left: 180px;
            padding-top: 50px;
            padding-bottom: 20px;

        }

        .table textarea {
            resize: none;
            height: 300px;
        }


        .form-control {

            margin-bottom: 30px;
            font-size: 1.3rem;


        }

        #btns {
            display: flex;
            justify-content: right;
            padding-right: 200px;
        }


    </style>
</head>
<body id="body">
<main>
    <%@ include file="/WEB-INF/inc/header.jsp"%>

    <section>

        <div id="board">

            <form method="POST" action="/movie/recruit/edit.do">

                <div style="width: 800px">

                    <select name="gseq" id="groups">
                        <c:forEach items="${glist}" var="gdto">
                            <option value="${gdto.gseq}">${gdto.name}</option>
                        </c:forEach>
                    </select>

                    <input type="text" name="title" class="form-control"  required value="${dto.title}" style="height: 50px;">

                    <textarea name="content" class="form-control"  required style="height: 500px;">${dto.content} </textarea>


                        <div class="btns" id="btns">
                            <input type="button" value="돌아가기" class="btn btn-secondary" style="margin-right: 10px"
                                   onclick="history.back()">
                            <input type="submit" value="수정하기" class="btn btn-primary">
                            <input type="hidden" value="${dto.seq}" name="seq">
                        </div>

                </div>

            </form>


        </div>


    </section>
    <footer>

    </footer>
</main>


<script>

/*    <c:if test="${result == 1}">
    location.href = '/movie/recruit/view.do?seq=${dto.seq}';
    </c:if>

    <c:if test="${result == 0}">
    alert('failed');
    history.back();
    </c:if>*/

</script>


</body>
</html>


