<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header>
    <div id="mainLogo"><a href="/"><img src="/movie/asset/images/logo.png" alt="logo"></a></div>
    <nav id="menubar">
        <ul>
            <li><a href="/movie/main/mainlist.do">전체</a></li>
            <li><a href="/movie/main/mainlist.do?type=1">리뷰</a></li>
            <li><a href="/movie/main/mainlist.do?type=2">자유</a></li>
            <li><a href="/movie/group/mygroup/mygrouplist.do">그룹</a></li>

        </ul>
    </nav>
    <c:if test="${empty auth}">
    <div class="btns">
        <input type="button" value="회원가입" class="btn btn-light btn-lg" onclick="location.href='/movie/member/register.do'">
        <input type="button" value="로그인" class="btn btn-secondary btn-lg"  onclick="location.href='/movie/member/login.do'">
    </div>
    </c:if>
    <c:if test="${not empty auth}">
    <div id="login">
        <div id="profile"><img src="/movie/asset/images/${auth.picture}" alt="" ></div>
        <div id="info">
            <div>
                <span class="badge rounded-pill bg-warning">Lv.${auth.grade}</span>
                <span id="nickname">${auth.nickname}님</span>
            </div>
            <div class="control">
                <a href="/movie/member/mypage.do">마이페이지</a>
                <a href="/movie/member/logout.do">로그아웃</a>
            </div>
        </div>
    </div>
    </c:if>
    <a href="" id="bell"><i class="fa-solid fa-bell"></i></a>
</header>