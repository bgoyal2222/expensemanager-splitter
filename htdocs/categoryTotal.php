<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	

	$uid=$_REQUEST["uid"];
	$from=$_REQUEST["from"];
	$to=$_REQUEST["to"];
	
	$res=mysql_query("select c.category_name,sum(amount) from expense e join category c on e.category_id=c.category_id where e.user_id='".$uid."' and e.date>='".$from."' and e.date<='".$to."' group by c.category_name");
	$response["category_total"]=array();

	while($row=mysql_fetch_row($res))
	{
		$category=array();
		$category["category_name"]=$row[0];
		$category["total"]=$row[1];
		array_push($response["category_total"],$category);
	}
	$response["success"]="1";
	$data=json_encode($response);
	echo $data;

?>