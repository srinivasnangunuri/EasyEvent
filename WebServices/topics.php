<html>
<body>
<?php
	# o = get
	# title

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
	else if($operation === 'add'){
		$sql = "SELECT DISTINCT topic_id AS tid FROM TOPIC;";
		$result = $conn->query($sql);
		$has = false;
		$tid = 0;
		for($i = 1; ; $i++){
			$has = false;
			if ($result->num_rows > 0) {
				while($row = $result->fetch_assoc()) {
					if($row["tid"]===$i.""){
						$has = true;
						break;
					}
				}
			}
			if($has === false){
				$tid = $i;
				break;
			}
		}
		$title = $_POST['title'];
		$sql = "INSERT INTO TOPIC (topic_id, title, active, updated_at) VALUES ($tid, '$title', DEFAULT, DEFAULT);";
		if($conn->query($sql) === TRUE)
			echo "<result>success</result>";
		else
			echo "<result>fail</result>";

	}
	$conn->close();
?>
</body>
</html>