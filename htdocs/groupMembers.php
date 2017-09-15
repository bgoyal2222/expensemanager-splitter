<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	

	$gid=$_REQUEST["gid"];
	
	$res=mysql_query("select * from user where user_id in(select user_id from participants where group_id='".$gid."')");
	$response["group_members"]=array();

	while($row=mysql_fetch_row($res))
	{
		$users=array();
		$users["user_id"]=$row[0];
		$users["user_name"]=$row[3];
		array_push($response["group_members"],$users);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>