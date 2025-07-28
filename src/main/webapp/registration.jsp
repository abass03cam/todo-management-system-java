<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet" href="styles.css">
<script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<title>Register</title>
<style>
	body {
		font-family: Arial, sans-serif;
		margin: 0;
		padding: 0;
		background-color: #f2f2f2;
	}

	.main {
		max-width: 400px;
		margin: 50px auto;
		background-color: #fff;
		border-radius: 5px;
		padding: 20px;
		box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	}

	h2 {
		margin-top: 0;
		font-size: 24px;
		text-align: center;
		margin-bottom: 20px;
	}

	.form-group {
		margin-bottom: 20px;
	}

	label {
		display: block;
		font-weight: bold;
		margin-bottom: 5px;
	}

	input[type="text"],
	input[type="email"],
	input[type="password"] {
		width: 100%;
		padding: 10px;
		border: 1px solid #ccc;
		border-radius: 5px;
		box-sizing: border-box;
	}

	button[type="submit"] {
		width: 100%;
		padding: 10px;
		background-color: #007bff;
		color: #fff;
		border: none;
		border-radius: 5px;
		cursor: pointer;
		transition: background-color 0.3s;
	}

	button[type="submit"]:hover {
		background-color: #0056b3;
	}

	.login-link {
		display: block;
		text-align: center;
		margin-top: 20px;
		text-decoration: none;
		color: #007bff;
	}

	.login-link:hover {
		text-decoration: underline;
	}
</style>
</head>
<body>

<div class="main">
    <h2>Register</h2>
    <form method="post" action="register">
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" name="name" id="name" placeholder="Your Name" />
        </div>
        <div class="form-group">
            <label for="email">E-Mail</label>
            <input type="email" name="email" id="email" placeholder="Your Email" />
        </div>
        <div class="form-group">
            <label for="pass">Password</label>
            <input type="password" name="pass" id="pass" placeholder="Password" />
        </div>
        <div class="form-group">
            <label for="re-pass">Repeat Password</label>
            <input type="password" name="re_pass" id="re_pass" placeholder="Repeat Password" />
        </div>
        <button type="submit" name="signup" id="signup">Register</button>
    </form><br>
    <a href="login.jsp" class="login-link">Already have an account? Login here</a>
</div>

<script>
    var status = document.getElementById("status").value;
    if(status == "success") {
        swal("Account successfully created", "success");
    }
</script>

</body>
</html>
