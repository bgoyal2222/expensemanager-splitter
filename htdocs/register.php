<?php

	mysql_connect("localhost","root","");
	
	$email=$_REQUEST["email"];

	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}

	$res=mysql_query("select email from user where email='".$email."'");

	mysql_select_db('expense_tracker');
   	$res = mysql_query( $sql, $conn );
	$cnt=mysql_num_rows($res);

	if($cnt>0) 
	{
     		$response["success"]="1";
		$row=mysql_fetch_row($res);
		$response["email"]=$row[1];
 	}
	else
	{
  		$response["success"]="0";
	}

	$data=json_encode($response);
	echo $data;

?>