<html>
<body>
<?php
	$servername = "mysql.leowrd.com";
	$username = "adminee";
	$password = "adminadmin";
	$dbname = "easyevent";
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);

	$sql = "SELECT username AS uname, password, email FROM USER;";
	$result = $conn->query($sql);

	$verified = "false";
	
	$username = $_POST['username'];
	$password = $_POST['password'];
	
	if ($result->num_rows > 0) {
		while($row = $result->fetch_assoc()) {
			if($row["uname"]==$username&&$row["password"]==$password)
				$verified = "true";
		}
	}

	echo "<result>$verified</result>";
	$conn->close();
?>
</body>
</html>