<html>
<body>
<?php

	# o = sub (subscribe), un (unsubscribe), get (get related subscription)
	# param:
	# user_name, topic_id
	
	$servername = "mysql.leowrd.com";
	$username = "adminee";
	$password = "adminadmin";
	$dbname = "easyevent";
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);
	$operation = $_POST["o"];
	if($operation==="s"){
		// Generating subscription_id
		$sql = "SELECT DISTINCT subscription_id AS sid FROM SUBSCRIPTION;";
		$result = $conn->query($sql);
		$has = false;
		$sid = 0;
		for($i = 1; ; $i++){
			$has = false;
			if ($result->num_rows > 0) {
				while($row = $result->fetch_assoc()) {
					if($row["sid"]===$i.""){
						$has = true;
						break;
					}
				}
			}
			if($has === false){
				$sid = $i;
				break;
			}
		}
		$uname = $_POST['user_name'];
		$tid = $_POST['topic_id'];
		$insert = "INSERT INTO SUBSCRIPTION(subscription_id, user_name, topic_id) VALUES ($sid,'$uname',$tid);";
		if($conn->query($insert) === TRUE)
			echo "<result>success</result>";
		else
			echo "<result>fail</result>";
	}
	else if($operation ==="get"){
		$username = $_POST['user_name'];
		$sql = "SELECT e.event_id ,e.name AS name FROM SUBSCRIPTION s, EVENT e WHERE s.topic_id = e.topic_id AND s.user_name = '$username';";
		$result = $conn->query($sql);
		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
				$tid = $row['name'];
				$eid = $row['event_id'];
				echo "<result>";
				echo "<event_id>$eid</event_id>";
				echo "<name>$tid</name>";
				echo "</result>";
			}
		}
	}
	else if($operation ==="un"){
		$uname = $_POST['user_name'];
		$tid = $_POST['topic_id'];
		$sql = "DELETE FROM SUBSCRIPTION WHERE user_name = '$uname' AND topic_id = $tid;";
		if($conn->query($sql) === TRUE)
			echo "<result>success</result>";
		else
			echo "<result>fail</result>";
	}
	$conn->close();
?>
</body>
</html>