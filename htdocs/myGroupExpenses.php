<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");

	$uid=$_REQUEST["uid"];
	$gid=$_REQUEST["gid"];
	
	$res=mysql_query("select * from group_ex where group_id=1 and gex_id in(select gex_id from involved where user_id='".$uid."')");

	$res2=mysql_query("select u.user_name,r.gex_id,r.expense_name,r.amount,r.date from user u natural join (select * from group_ex where group_id=1 and gex_id in(select gex_id from involved where user_id='".$uid."')) as r");

	$res3=mysql_query("select u.user_name,r.gex_id,r.expense_name,r.amount,r.date from user u join (select * from group_ex where group_id=1 and gex_id in(select gex_id from involved where user_id='".$uid."')) as r on r.user_id=u.user_id");

	$res4=mysql_query("select r2.*,i.status from involved i join(select u.user_name,r.gex_id,r.expense_name,r.amount,r.date from user u join (select * from group_ex where group_id='".$gid."' and gex_id in(select gex_id from involved where user_id='".$uid."' and status='unpaid')) as r on r.user_id=u.user_id) as r2 on r2.gex_id=i.gex_id where i.user_id='".$uid."'");	

	$response["my_data"]=array();


	while($row=mysql_fetch_row($res4))
	{
		$details=array();
		$details["gex_id"]=$row[1];
		$details["name"]=$row[2];
		$details["amount"]=$row[3];
		$details["date"]=$row[4];	
		$details["addedBy"]=$row[0];
		$details["status"]=$row[5];
		array_push($response["my_data"],$details);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>