<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.java.model.User"%>
<%@ page import="com.java.dao.*"%>
<%@ page import="com.java.connection.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.java.model.*"%>
<%
User ok = (User) request.getSession().getAttribute("ok");
List<Order> order = null;
if (ok != null) {
	request.setAttribute("ok", ok);
	order = new OrderDao(DBCon.getConnection()).userOrders(ok.getId());
} else {
	response.sendRedirect("login.jsp");
}

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
if (cart_list != null) {
	request.setAttribute("cart_list", cart_list);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Orders page</title>
<%@ include file="Includes/header.jsp"%>
</head>
<body>
	<%@ include file="Includes/navbar.jsp"%>

	<div class="container">
		<div class="card-header my-3">All Orders</div>
		<table class="table table-dark">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Quantity</th>
					<th scope="col">Price</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>

				<%
				if (order != null) {
					for (Order o:order){%>
					<tr>
						<td><%=o.getDate()%></td>
						<td><%=o.getName()%></td>
						<td><%=o.getCategory()%></td>
						<td><%=o.getQuantity()%></td>
						<td><%=o.getPrice()%></td>
						<td><a class="btn btn-sm btn-danger"
							href="cancel-order?id=<%=o.getOrderId()%>">Cancel</a></td>
				</tr>
				<%}
				}
				%>

			</tbody>
		</table>

	</div>

	<%@ include file="Includes/footer.jsp"%>
</body>
</html>