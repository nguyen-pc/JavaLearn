<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" 
    rel="stylesheet">
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <title>User detail ${user.id}</title>
</head>
<body>
   <div class="container mt-5">
        <div class="d-flex justify-content-between">
            <h1>Detail User ${user.id}</h1>
        </div>
        <hr />
        <div>
            <div class="card" style="width: 60%;">
                <div class="card-header">
                User information
                </div>
                <ul class="list-group list-group-flush">
                <li class="list-group-item">ID: ${user.id}</li>
                <li class="list-group-item">Email: ${user.email}</li>
                <li class="list-group-item">FullName: ${user.fullName}</li>
                <li class="list-group-item">Address: ${user.address}</li>
                <li class="list-group-item">Phone: ${user.phone}</li>
                </ul>
               </div>
        </div>
        <a href="/admin/user" class="mt-5 btn btn-success">Back</a>
   </div>
</body>
</html>