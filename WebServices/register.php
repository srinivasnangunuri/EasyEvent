<html>
<body>
<?php

	$servername = "mysql.leowrd.com";
	$username = "adminee";
	$password = "adminadmin";
	$dbname = "easyevent";
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);

	$sql = "SELECT username FROM USER;";
	$result = $conn->query($sql);

	$r1 = "false";
	
	$username = $_POST['username'];
	$password = $_POST['password'];
	$email = $_POST['email'];
	
	if(strpos($email, '@')===FALSE){
		$r1 = "error";
	}
	else{
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
				if($row["username"]===$username){
					$r1 = "exist";
					break;
				}
			}
		}
	}
	if($r1==="exist"){
		echo "<result>$r1</result>";
	}
	else if($r1==="error"){
		echo "<result>$r1</result>";
	}
	else{
		$insert = "INSERT INTO USER (username,password,email) VALUES ('$username','$password','$email');";
		if($conn->query($insert) === TRUE)
			echo "<result>success</result>";
		else
			echo "<result>fail</result>";
	}
	$conn->close();
?>
</body>
</html>