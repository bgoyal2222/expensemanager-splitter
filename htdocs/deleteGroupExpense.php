<?php

	$conn=mysql_connect("localhost","root","");

	$eid=$_REQUEST["expense_id"];

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
   
   	$sql = "delete from group_ex where gex_id='".$eid."'";
      
   mysql_select_db('expense_tracker');
   $retval = mysql_query( $sql, $conn );
   
   if(! $retval ) {
      die('Could not enter data: ' . mysql_error());
   }
   
  
   $response["success"]="1";
echo json_encode($response);  
 mysql_close($conn);
?>