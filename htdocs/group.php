<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	

	$uid=$_REQUEST["uid"];
	
	$res=mysql_query("select * from groups where group_id in(select group_id from participants where user_id='".$uid."')");
	$res2=mysql_query("select * from group g join participants p on g.group_id=p.group_id where user_id=71 ");
	$response["group_data"]=array();

	while($row=mysql_fetch_row($res))
	{
		$group=array();
		$group["group_id"]=$row[0];
		$group["group_name"]=$row[1];
		array_push($response["group_data"],$group);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>