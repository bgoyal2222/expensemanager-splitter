<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");

	$from=$_REQUEST["from"];
	$to=$_REQUEST["to"];
	$uid=$_REQUEST["uid"];
	$cid=$_REQUEST["category"];

	$res=mysql_query("select sum(amount) from expense where user_id='".$uid."' and date>='".$from."' and date<='".$to."' group by category_id having category_id='".$cid."'");
	$res2=mysql_query("select expense_name from expense where date>='2016-06-16' and date<='2016-07-16'");
	
	$row=mysql_fetch_row($res);
	
	
	$response["total"]=$row[0];

	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>