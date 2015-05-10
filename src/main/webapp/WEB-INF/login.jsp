<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form method=post>
	<table>
		<tr>
			<td>Login:</td>
			<td><input name="login" value="${param.login}"/></td>
		</tr>
		<tr>
			<td>Password:</td>
			<td><input name="password" type="password"/></td>
		</tr>
		<tr>
			<td colspan=2>
				<button>Login</button>
			</td>
		</tr>
	</table>
	<c:if test="${not empty message}">
		<p style="color:red">${message}</p>
	</c:if>
</form>