<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");

	$gex_id=$_REQUEST["gex_id"];
	
	$res=mysql_query("select * from involved where gex_id='".$gex_id."'");
	$response["my_involvement"]=array();

	$involvement=NULL;

	while($row=mysql_fetch_row($res))
	{
		$involvement=array();
		$involvement["gex_id"]=$row[0];
		$involvement["status"]=$row[2];
		array_push($response["my_involvement"],$involvement);
	}
	
	$count=sizeof($involvement);
	$response["count"]=$count;
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>