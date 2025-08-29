<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>簡易Twitter</title>
        <link href="./css/style.css" rel="stylesheet" type="text/css">
    </head>

    <body>
        <div class="main-contents">
            <div class="header">
          	 	<c:if test="${ empty loginUser }">
                	<a href="login">ログイン</a>
                	<a href="signup">登録する</a>
                </c:if>
                <c:if test="${ not empty loginUser }">
        			<a href="./">ホーム</a>
        			<a href="setting">設定</a>
        			<a href="logout">ログアウト</a>
        		</c:if>
            </div>
            <div class="profile">
        		<div class="name"><h2><c:out value="${loginUser.name}" /></h2></div>
        		<div class="account">@<c:out value="${loginUser.account}" /></div>
        		<div class="description"><c:out value="${loginUser.description}" /></div>
    		</div>

    		<c:if test="${ not empty errorMessages }">
    			<div class="errorMessages">
     				<ul>
        			    <c:forEach items="${errorMessages}" var="errorMessage">
        			       <li><c:out value="${errorMessage}" />
       				    </c:forEach>
       				</ul>
  				</div>
   				<c:remove var="errorMessages" scope="session" />
			</c:if>

			<!-- つぶやき投稿 -->
			<div class="form-area">
   				<c:if test="${ isShowMessageForm }">
					<form action="message" method="post">
						いま、どうしてる？<br />
						<textarea name="text" cols="100" rows="5" class="tweet-box"></textarea>
						<br>
						<input type="submit" value="つぶやく">（140文字まで）
					</form>
    			</c:if>
			</div><br>

			<!-- つぶやきの絞り込み -->
			<div class="filter">
				【つぶやきフィルター】<br>
				<form action="./" method="get">
					<label for="start">開始日</label>
						<input type="date" value="${start}" name="start">
	    			<label for="end">～  終了日</label>
	    				<input type="date" value="${end}" name="end">
	    			<input type="submit" value="絞り込み">
				</form>
			</div>
			<!-- つぶやき一覧 -->
			<div class="messages">
				<div class="message">
	    			<c:forEach items="${messages}" var="message">
						<div class="account-name">
							<!-- アカウント名表示 -->
                			<span class="account">
                				<a href="./?user_id=<c:out value="${message.userId}"/>">
                					<c:out value="${message.account}"/>
                				</a>
                			</span>
                			<!-- ユーザー名表示 -->
                			<span class="name">
                				<c:out value="${message.name}" />
                			</span>
						</div>
						<!-- つぶやき本文表示 -->
           				<div class="text"><c:out value="${message.text}" /></div>
           				<!-- つぶやいた日時表示 -->
            			<div class="date"><fmt:formatDate value="${message.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>

						<div class="bottun">
							<!-- ログインかつ本人のみ編集削除が可能 -->
							<c:if test="${ message.userId == loginUser.id}">
								<!-- つぶやきの削除 -->
								<form action="deleteMessage" method="post">
									<input type="hidden" name="id" value="${message.id}" id="id">
		            				<input type="submit" value="削除" >
        	    				</form>

        	    				<!-- つぶやきの編集 -->
        	    				<form action="edit" method="get">
        	    					<input type="hidden" name="id" value="${message.id}" id="id">
        	    					<input type="submit" value="編集">
        	    				</form>
							</c:if>
						</div>
						<!-- 返信の表示 -->
						<div class="comments">
							<c:forEach items="${comments}" var="comment">
								<c:if test="${ comment.messageId == message.id }" >
									<div class="account-name">
										<!-- 返信したアカウント名表示 -->
										<span class="account">
											<c:out value="${comment.account}" />
										</span>
										<!-- 返信したユーザー名表示 -->
										<span class="name">
											<c:out value="${comment.name}" />
										</span>
									</div>
									<!-- 返信表示 -->
									<div class="comment"><c:out value="${comment.text}" /></div>
									<!-- 返信日時表示 -->
									<div class="date"><fmt:formatDate value="${comment.createdDate}" pattern="yyyy/MM/dd HH:mm:ss" /></div>
								</c:if>
							</c:forEach>
						</div>
						<!-- ログインかつ本人ではないつぶやきのみ返信可能 -->
						<c:if test="${ loginUser.id != null && message.userId != loginUser.id}">
							<!-- 返信フォーム -->
							<form action="comment" method="post">
								<div class="comment-form">
									返信
									<textarea name="comment-text" cols="100" rows="3" class="comment-text"></textarea><br />
									<input type="hidden" name="id" value="${message.id}" id="id">
									<input type="submit" value="返信">
								</div>
							</form>
						</c:if>
        			</c:forEach>
    			</div>
			</div>
            <div class="copyright"> Copyright(c)ShiraiwaKazuki</div>
        </div>
    </body>

</html>