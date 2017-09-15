<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	
	$from=$_REQUEST["from"];
	$to=$_REQUEST["to"];
	
	$res=mysql_query("select * from group_ex where group_id=1 and date>='".$from."' and date<='".$to."'");
	$response["expense_data"]=array();

	while($row=mysql_fetch_row($res))
	{
		$expense=array();
		$expense["expense_id"]=$row[0];
		$expense["expense_name"]=$row[1];
		$expense["amount"]=$row[2];
		array_push($response["expense_data"],$expense);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>