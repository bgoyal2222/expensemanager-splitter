<?php

	$conn=mysql_connect("localhost","root","");

	$email=$_REQUEST["email"];
	$pass=$_REQUEST["password"];
	

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
   
   	$sql = "select * from  user where email='".$email."' and password= '".$pass."'";
      
   mysql_select_db('expense_tracker');
   $res = mysql_query( $sql, $conn );
	$cnt=mysql_num_rows($res);

   
   if($cnt>0 ) {
     $response["success"]="1";
	$row=mysql_fetch_row($res);
	$response["id"]=$row[0];

	$response["name"]=$row[3];
  }
else
  $response["success"]="0";
   
   

echo json_encode($response);   

mysql_close($conn);

?>