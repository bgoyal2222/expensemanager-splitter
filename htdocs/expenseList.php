<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	

	$uid=$_REQUEST["uid"];
	$from=$_REQUEST["from"];
	$to=$_REQUEST["to"];
	
	$res=mysql_query("select * from expense where user_id='".$uid."' and date>='".$from."' and date<='".$to."'");
	$res2=mysql_query("select * from expense where user_id='".$uid."'");
	$response["expense_data"]=array();

	while($row=mysql_fetch_row($res))
	{
		$expense=array();
		$expense["expense_id"]=$row[0];
		$expense["expense_name"]=$row[1];
		$expense["amount"]=$row[2];
		$expense["date"]=$row[3];
		$expense["user_id"]=$row[4];
		$expense["category_id"]=$row[5];
		array_push($response["expense_data"],$expense);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>