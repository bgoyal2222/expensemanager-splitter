<?php

	$conn=mysql_connect("localhost","root","");
if(isset($_REQUEST["name"]))
{
	$uname=$_REQUEST["name"];
	$email=$_REQUEST["email"];
	$pass=$_REQUEST["pass"];
	$contact=$_REQUEST["contact"];

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
   
   	$sql = "INSERT INTO user (email,password,user_name,phone_no) VALUES ( '".$email."', '".$pass."', '".$uname."', '".$contact."')";
      
   mysql_select_db('expense_tracker');
   $retval = mysql_query( $sql, $conn );
   
   if(! $retval ) {
      die('Could not enter data: ' . mysql_error());
   }
   
   
   $response["success"]="1";

echo json_encode($response);   
}
mysql_close($conn);

?>