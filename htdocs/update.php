<?php

	$conn=mysql_connect("localhost","root","");

	$uid=$_REQUEST["uid"];

	$changePassword=$_REQUEST["changePassword"];
	$npass=$_REQUEST["npass"];			

	$profile=$_REQUEST["profile"];
	$uname=$_REQUEST["uname"];
	$contact=$_REQUEST["contact"];

	$limit=$_REQUEST["limit"];
	$from=$_REQUEST["from"];
	$to=$_REQUEST["to"];
	

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
	mysql_select_db('expense_tracker');	
   	if(strcmp($profile,"1")==0)
	{
	$sql = "UPDATE user set user_name='".$uname."',phone_no='".$contact."' where user_id='".$uid."' ";
	}
	elseif(strcmp($changePassword,"1")==0)
	{
	$sql = "UPDATE user set password='".$npass."' where user_id='".$uid."' ";
	}
	else
	{
   	$sql = "UPDATE user set expense_limit='".$limit."',from_date='".$from."',to_date='".$to."' where user_id='".$uid."' ";
	}
      
	
   
   $retval = mysql_query( $sql, $conn );
   
   if(! $retval ) {
      die('Could not update data: ' . mysql_error());
   }
   
   
   $response["success"]="1";

echo json_encode($response);   

mysql_close($conn);

?>