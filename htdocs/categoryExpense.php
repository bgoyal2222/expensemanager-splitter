<?php

	$conn=mysql_connect("localhost","root","");

	$uid=$_REQUEST["uid"];		

	$amt=$_REQUEST["amount"];
	$from=$_REQUEST["from"];
	$to=$_REQUEST["to"];
	$cid=$_REQUEST["category"];

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
   
   	$sql = "INSERT INTO category_limit (amount,from_date,to_date,category_id,user_id) VALUES ( '".$amt."', '".$from."', '".$to."', '".$cid."','".$uid."')";
      
   mysql_select_db('expense_tracker');
   $retval = mysql_query( $sql, $conn );
   
   if(! $retval ) {
      die('Could not enter data: ' . mysql_error());
   }
   
  
   $response["success"]="1";
echo json_encode($response);  
 mysql_close($conn);
?>