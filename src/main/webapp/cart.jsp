<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.java.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.java.dao.*"%>
<%@ page import="com.java.connection.*"%>
<%
User ok = (User) request.getSession().getAttribute("ok");
if (ok != null) {
	request.setAttribute("ok",ok);
}

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
List<Cart> cartProduct = null;
if (cart_list != null) {
	ProductDao pDao = new ProductDao(DBCon.getConnection());
	cartProduct = pDao.getCartProducts(cart_list);
	double total = pDao.getTotalCartPrice(cart_list);
	request.setAttribute("cart_list", cart_list);
	request.setAttribute("total", total);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cart page</title>
<%@ include file="Includes/header.jsp"%>
<style type="text/css">
.table tbody td {
	varticale-align: middle;
}

.btn-incre, .btn-decre {
	box-shadow: none;
	font-size: 25px;
}
</style>
</head>
<body>
	<%@ include file="Includes/navbar.jsp"%>

	<div class="container">
		<div class="d-flex py-3">
			<h3>Total Price :$ ${ (total>0)?total:0}</h3>
			<a class="mx-3 btn btn-dark" href="check-out-servlet">Check OUT</a>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Buy Now</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (cart_list != null) {
					for (Cart c : cartProduct) {
				%>
				<tr>
					<td><%=c.getName() %></td>
					<td><%=c.getCategory() %></td>
					<td><%=c.getPrice() %></td>
					<td>
						<form action="order-servlet" method="post" class="form-inline">
							<input type="hidden" name="id" value="<%=c.getId() %>" class="form-input">
							<div class="form-group d-flex justify-content-between w-50">
								<a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%=c.getId() %>"><i class="fas fa-minus-square"></i></a> 
								<input type="text" name="quantity" class="form-control w-50" value="<%=c.getQuantity() %>" readonly>
								<a class="btn btn-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%=c.getId() %>"><i class="fas fa-plus-square"></i></a>
							</div>
							<button type="submit" class="btn btn-primary btn-sm">Buy Now</button>
						</form>
					</td>
					<td><a class="btn btn-sm btn-danger" href="remove-servlet?id=<%=c.getId() %>">Remove</a></td>
				</tr>
				<%
				}
				}
				%>
			</tbody>
		</table>
	</div>

	<%@ include file="Includes/footer.jsp"%>
</body>
</html>