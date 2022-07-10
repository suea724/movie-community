<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="submenu">
    <div id="profile-box">
        <div class="image-container" id="submenu-profile">
            <img src="/movie/asset/images/${auth.picture}" class="profile-img">
        </div>
        <h5>${auth.name}(${auth.nickname})</h5>
        <h5>${auth.id}</h5>
    </div>
    <ul>
        <li><a href="/movie/member/mypage.do" id="myinfo">내 정보 조회</a></li>
        <li><a href="/movie/member/mypage/posts.do" id="myposts">내 글 보기(${auth.postCnt})</a></li>
        <li><a href="/movie/member/mypage/comments.do" id="mycomments">내 댓글 보기(${auth.commentCnt})</a></li>
        <li><a href="/movie/member/unregister.do" style="color: dimgray;">탈퇴하기</a></li>
    </ul>
</div>
