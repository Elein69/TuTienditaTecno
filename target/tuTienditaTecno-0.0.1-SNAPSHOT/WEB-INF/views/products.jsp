<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <title>Products</title>
    </head>
    <body>
        <!--     <section>
            <div class="jumbotron">
                <div class="container">
                    <h1>Products</h1>
                    <p>All the available products in our store</p>
                </div>
            </div>
        </section>-->
        <section class="container">
            <div class="row">
                <c:forEach items="${products}" var="product">
                    <div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
                        <div class="thumbnail">
                            <img src="<c:url value="/img/${product.productId}.png">
                                 </c:url>" alt="image" style="width: 100%" />
                            <div class="caption">
                                <h3>${product.name}</h3>
                                <p>${product.description}</p>
                                <p>${product.unitPrice} USD</p>
                                <p>Available ${product.unitsInStock} units</p>
                                <p>
                                    <a href="<spring:url value='/market/product?id=${product.productId}' />" class="btn btn-info">
                                        <span class="glyphicon glyphicon-info-sign"></span> Details
                                    </a>
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </section>
    </body>
</html>

