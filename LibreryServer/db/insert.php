<?php
include 'db_connect.php';
$response = array();
 
// Check mandatory parameters
if(isset($_POST['libro'])){
	$book = $_POST['libro'];
	
	//Query (prep statement)
	$query = "INSERT INTO consigli(libro, commento, valutazione) VALUES (?,?,?)";
	//Prepare the query
	if($stmt = $con->prepare($query)){
		$comment = $_POST['commento'];
		$rating = $_POST['valutazione'];
		//Bind parameters
		$stmt->bind_param("ssd",$book,$comment,$rating);
		// Executing MySQL statement
		echo $book;
		echo $comment;
		echo $rating;
		$stmt->execute();
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