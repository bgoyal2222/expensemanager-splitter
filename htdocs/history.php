<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");

	$uid=$_REQUEST["uid"];
	

	

	$res=mysql_query("select r.*,u.user_name from user u join (select gex_id,expense_name,amount,date,user_id from group_ex where gex_id in(select gex_id from involved where user_id='".$uid."' and status='paid')) as r on r.user_id=u.user_id");

	
	$response["history_data"]=array();


	while($row=mysql_fetch_row($res))
	{
		$history=array();
		$history["gex_id"]=$row[0];
		$history["expense"]=$row[1];
		$history["amount"]=$row[2];
		$history["date"]=$row[3];
		$history["user_id"]=$row[4];
		$history["paidTo"]=$row[5];
		array_push($response["history_data"],$history);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>