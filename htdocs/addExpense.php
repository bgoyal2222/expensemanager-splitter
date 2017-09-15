<?php

	$conn=mysql_connect("localhost","root","");

	$uid=$_REQUEST["uid"];		

	$name=$_REQUEST["name"];
	$amt=$_REQUEST["amount"];
	$date=$_REQUEST["date"];

	$cid=$_REQUEST["category"];

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
   
   	$sql = "INSERT INTO expense (expense_name,amount,date,user_id,category_id) VALUES ( '".$name."', '".$amt."', '".$date."', '".$uid."','".$cid."')";
      
   mysql_select_db('expense_tracker');
   $retval = mysql_query( $sql, $conn );
   
   if(! $retval ) {
      die('Could not enter data: ' . mysql_error());
   }
   
  
   $response["success"]="1";
echo json_encode($response);  
 mysql_close($conn);
?>