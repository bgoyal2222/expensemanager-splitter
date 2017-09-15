<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	
	$uid=$_REQUEST["uid"];

	$res=mysql_query("select * from user where user_id='".$uid."'");

	$row=mysql_fetch_row($res);
	
	
	$response["email"]=$row[1];
	$response["pass"]=$row[2];
	$response["uname"]=$row[3];
	$response["contact"]=$row[4];
	$response["limit"]=$row[5];
	$response["from"]=$row[6];
	$response["to"]=$row[7];

	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>