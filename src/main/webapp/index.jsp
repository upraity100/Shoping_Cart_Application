<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.java.connection.DBCon"%>
<%@ page import="com.java.model.*"%>
<%@ page import="com.java.dao.*"%>
<%@ page import="java.util.*"%>
<%
User ok = (User) request.getSession().getAttribute("ok");
if (ok != null) 
{
	request.setAttribute("ok", ok);
}

ProductDao pd = new ProductDao(DBCon.getConnection());
List<Product> products = pd.getAllProducts();

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
if (cart_list != null) 
{
	request.setAttribute("cart_list", cart_list);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index page</title>
<%@ include file="Includes/header.jsp"%>
</head>
<body>
	<%@ include file="Includes/navbar.jsp"%>
	<div class="container">
		<div class="card-header my-3">All Products</div>
		<div class="row">
			<%
			if (!products.isEmpty()) {
				for (Product p : products) {
			%>
			<div class="col-md-3 my-3">
				<div class="card w-100" style="width: 18rem;">
					<img class="card-img-top" alt="card image cap"
						src="product-images/<%=p.getImage() %>">
					<div class="card-body">
						<h5 class="card-title"><%=p.getName() %></h5>
						<h6 class="cart-title">
							Price: $<%=p.getPrice() %></h6>
						<h6 class="category">
							Category:
							<%=p.getCategory() %></h6>
						<div class="mt-3 d-flex justify-content-between">
							<a href="add-to-cart?id=<%=p.getId() %>" class="btn btn-danger">Add
								To Cart</a> <a href="order-servlet?quantity=1&id=<%=p.getId() %>" class="btn btn-primary">Buy Now</a>
						</div>
					</div>
				</div>
			</div>
			<%
			}
			}
			%>
		</div>
	</div>

	<%@ include file="Includes/footer.jsp"%>
</body>
</html>