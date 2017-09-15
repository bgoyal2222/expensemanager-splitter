<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");

	$uid=$_REQUEST["uid"];
	$gid=$_REQUEST["gid"];
	

	$res=mysql_query("select r2.*,u.user_name from user u join(select r.*,g.expense_name,g.amount,g.date from group_ex g join(select * from involved where user_id!='".$uid."' and status='unpaid' and gex_id in(select gex_id from group_ex where user_id='".$uid."' and group_id='".$gid."')) as r on g.gex_id=r.gex_id)as r2 on u.user_id=r2.user_id");

	
	$response["my_data"]=array();


	while($row=mysql_fetch_row($res))
	{
		$details=array();
		$details["gex_id"]=$row[0];
		$details["user_id"]=$row[1];
		$details["status"]=$row[2];
		$details["name"]=$row[3];
		$details["amount"]=$row[4];
		$details["date"]=$row[5];
		$details["user_name"]=$row[6];
		array_push($response["my_data"],$details);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>