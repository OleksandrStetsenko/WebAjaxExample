<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<head>
	<script src="${pageContext.request.contextPath}/jquery-2.1.1.js"></script>
	<style>
		table { border-collapse: collapse }
		thead { background: #eee }
		td, th { 
			text-aligh: left; 
			border: 1px solid #ccc; 
			padding: .2em .5em; 
		}
		table caption { 
			text-align: left; 
			font-size: large; 
			font-weight: bold;
			margin-bottom: .5em;
		}
	</style>
	<script>
		window.setInterval(function() {
			
			jQuery.get('${pageContext.request.contextPath}/test.jsp', function(text) {
				jQuery('#message').html(text);
			});
			
		}, 1000);
	</script>
</head>
<body>
	<h1>${title}</h1>
	<p id="message">
		<jsp:include page="/test.jsp"/>
	</p>
	<table>
		<thead>
			<tr>
				<c:forEach var="column" items="${columns}">
					<th>${column}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="row" items="${rows}">
				<tr>
					<c:forEach var="column" items="${columns}">
						<td>${row[column]}</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>