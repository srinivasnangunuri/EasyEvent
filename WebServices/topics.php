<html>
<body>
<?php
	# o = get

	$servername = "mysql.leowrd.com";
	$username = "adminee";
	$password = "adminadmin";
	$dbname = "easyevent";
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);

	$operation = $_POST['o'];
	if($operation==='get'){
		$sql = "SELECT * FROM TOPIC;";
		$result = $conn->query($sql);

		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
				$tid = $row['topic_id'];
				$title = $row['title'];
				$active = $row['active'];
				$updated_at = $row['updated_at'];
				echo "<result>";
				echo "<topic_id>$tid</topic_id>";
				echo "<title>$title</title>";
				echo "<active>$active</active>";
				echo "<updated_at>$updated_at</updated_at>";
				echo "</result>";
			}
		}
	}
	$conn->close();
?>
</body>
</html>