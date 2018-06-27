<?php
include 'db_connect.php';
// Query 
$query = "SELECT libro, commento, valutazione FROM consigli ORDER BY id DESC";
$result = array();
$postArray = array();   // Array containing all posts
$response = array();
// Prepare the query
if($stmt = $con->prepare($query)) {
	$stmt->execute();
	// Bind the fetched data
	$stmt->bind_result($book, $comment, $rating);
	// Fetch 1 row at a time					
	while($stmt->fetch()){
		// Populate the array
		$postArray["libro"] = $book;
        $postArray["commento"] = $comment;
        $postArray["valutazione"] = $rating;
		$result[]=$postArray;
	}
	$stmt->close();
	$response["success"] = 1;
	$response["data"] = $result;
	
 
}else{
	// Some error while fetching data
	$response["success"] = 0;
	$response["message"] = mysqli_error($con);	
}

// Display JSON response 
echo json_encode($response); 
?>