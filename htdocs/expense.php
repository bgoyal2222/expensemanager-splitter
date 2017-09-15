<?php

	$conn=mysql_connect("localhost","root","");


	$uname=$_REQUEST["name"];
	$email=$_REQUEST["email"];
	$pass=$_REQUEST["pass"];
	$contact=$_REQUEST["contact"];;

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
	mysql_select_db('expense_tracker');
	$sql="select * from user where email='".$email."'";
	$sql1="select * from user where phone_no='".$contact."'";
	
	
   	$res = mysql_query( $sql, $conn );
	$cnt=mysql_num_rows($res);
	$res1 = mysql_query( $sql1, $conn );
	$cnt1=mysql_num_rows($res1);


	if($cnt>0 ) 
	{
     		$response["success"]="0";
		$response["message"]="email";		
 	}
	elseif($cnt1>0)
	{		$response["success"]="0";
		$response["message"]="phone";		
 	
	}
	else
	{
  		$sql = "INSERT INTO user (email,password,user_name,phone_no) VALUES ( '".$email."', '".$pass."', '".$uname."', '".$contact."')";
      
   		$retval = mysql_query( $sql, $conn );
   
   		if(! $retval )
		{
      			die('Could not enter data: ' . mysql_error());
   		}
   		$response["success"]="1";
	}
	



   
   	
echo json_encode($response);  
 mysql_close($conn);
?>