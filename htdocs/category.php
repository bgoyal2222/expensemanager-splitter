<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	
	$res=mysql_query("select * from category");
	$response["category_data"]=array();

	while($row=mysql_fetch_row($res))
	{
		$category=array();
		$category["category_id"]=$row[0];
		$category["category_name"]=$row[1];
		array_push($response["category_data"],$category);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>