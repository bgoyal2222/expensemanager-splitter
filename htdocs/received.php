<?php

	$conn=mysql_connect("localhost","root","");
	mysql_select_db('expense_tracker');

	$gex_id=$_REQUEST["gex_id"];
	$uid=$_REQUEST["user_id"];

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
   
   	$sql = "update involved set status='paid' where gex_id='".$gex_id."' and user_id='".$uid."'";
      
   
   $retval = mysql_query( $sql, $conn );
   
   if(! $retval ) {
      die('Could not enter data: ' . mysql_error());
   }
   
  
   $response["success"]="1";
echo json_encode($response);  
 mysql_close($conn);
?>