<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	
	
	$uid=$_REQUEST["uid"];
	$cid=$_REQUEST["category"];

	$res=mysql_query("select * from category_limit where user_id='".$uid."' and category_id='".$cid."'");

	$row=mysql_fetch_row($res);
	
	
	$response["limit_id"]=$row[0];
	$response["amount"]=$row[1];
	$response["from"]=$row[2];
	$response["to"]=$row[3];

	$response["success"]="1";
	$data=json_encode($response);
	echo $data;
	
?>