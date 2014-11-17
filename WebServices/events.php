<html>
<body>
<?php

	# o = get, add, edit
	# param:
	# event_id, name, desc, venue, topic_id, event_date

	$servername = "mysql.leowrd.com";
	$username = "adminee";
	$password = "adminadmin";
	$dbname = "easyevent";
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname);


	$operation = $_POST['o'];
	if($operation==="get"){
		$sql = "SELECT * FROM EVENT;";
		$result = $conn->query($sql);

		if ($result->num_rows > 0) {
			while($row = $result->fetch_assoc()) {
				$eid = $row['event_id'];
				$name = $row['name'];
				$desc = $row['desc'];
				$venue = $row['venue'];
				$tid = $row['topic_id'];
				$event_date = $row['event_date'];
				$active = $row['active'];
				$updated_at = $row['updated_at'];
				echo "<result>";
				echo "<event_id>$eid</event_id>";
				echo "<name>$name</name>";
				echo "<desc>$desc</desc>";
				echo "<venue>$venue</venue>";
				echo "<topic_id>$tid</topic_id>";
				echo "<event_date>$event_date</event_date>";
				echo "<active>$active</active>";
				echo "<updated_at>$updated_at</updated_at>";
				echo "</result>";
			}
		}
	}
	else if($operation === "add"){
		$sql = "SELECT DISTINCT event_id AS eid FROM EVENT;";
		$result = $conn->query($sql);
		$has = false;
		$eid = 0;
		for($i = 1; ; $i++){
			$has = false;
			if ($result->num_rows > 0) {
				while($row = $result->fetch_assoc()) {
					if($row["eid"]===$i.""){
						$has = true;
						break;
					}
				}
			}
			if($has === false){
				$eid = $i;
				break;
			}
		}
		$name = $_POST['name'];
		$desc = $_POST['desc'];
		$venue = $_POST['venue'];
		$tid = $_POST['topic_id'];
		$event_date = $_POST['event_date'];
		$sql = "INSERT INTO EVENT(event_id, `name`, `desc`, venue, topic_id, event_date, active, update_at) VALUES ($eid, '$name', '$desc', '$venue', $tid, '$event_date', DEFAULT, DEFAULT);";
		if($conn->query($sql) === TRUE)
			echo "<result>success</result>";
		else
			echo "<result>fail</result>";
	}
	else if($operation === 'edit'){
		$eid = $_POST['event_id'];
		$name = $_POST['name'];
		$desc = $_POST['desc'];
		$venue = $_POST['venue'];
		$tid = $_POST['topic_id'];
		$event_date = $_POST['event_date'];
		$sql = "UPDATE EVENT SET topic_id = $tid, `name` = '$name', `desc` = '$desc', venue = '$venue', event_date = '$event_date' WHERE event_id = $eid;";
		if($conn->query($sql) === TRUE)
			echo "<result>success</result>";
		else
			echo "<result>fail</result>";
	}
	$conn->close();
?>
</body>
</html>