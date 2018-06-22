<?php
include 'db_connect.php';
$response = array();
 
// Check mandatory parameters
if(isset($_GET['libro'])){
	$book = $_GET['libro'];
	
	//Query (prep statement)
	$query = "INSERT INTO consigli(libro, commento, valutazione) VALUES (?,?,?)";
	//Prepare the query
	if($stmt = $con->prepare($query)){
		//Bind parameters
		$stmt->bind_param("ssis",$book,$_GET['commento'],$_GET['valutazione']);
		// Executing MySQL statement
		$stmt->execute();
		sleep(1);
		// Check if data got inserted
		if($stmt->affected_rows == 1){
			$response["success"] = 1;			
			$response["message"] = "Successful insert operation.";			
			
		}else{
			//Some error while inserting
			$response["success"] = 0;
			$response["message"] = "Error in insert operation!";
		}					
	}else{
		$response["success"] = 0;
		$response["message"] = mysqli_error($con);
	}
 
}else{
	// Mandatory parameters are missing
	$response["success"] = 0;
	$response["message"] = "Missing mandatory parameters!";
}
// JSON response
echo json_encode($response);
?>