<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	
	$uid=$_REQUEST["uid"];
	
	$res=mysql_query("select * from groups where group_id in(select group_id from participants where user_id='".$uid."')");
	$response["group_data"]=array();

	while($row=mysql_fetch_row($res))
	{
		$groups=array();
		$groups["group_id"]=$row[0];
		$groups["group_name"]=$row[1];
		array_push($response["group_data"],$groups);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>