<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="submenu">
    <ul>
        <li><a href="/movie/group/grouplist.do?group=${group}">그룹 게시판</a></li>
        <li><a href="/movie/group/mygroup/groupinfo.do${group}">그룹 정보</a></li>
        <li><a href="/group/mygroup/groupmember.do${group}">그룹원 목록</a></li>
        <c:if test="${auth.id == groupId}">
            <li><a href="/movie/group/grouprequest.do?group=${group}">신청 목록</a></li>
        </c:if>
    </ul>
</div>