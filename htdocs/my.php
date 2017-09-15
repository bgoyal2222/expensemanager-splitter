<?php

	mysql_connect("localhost","root","");
	mysql_select_db("android");
	
	$res=mysql_query("select * from student");
	$response["mydata"]=array();

	while($row=mysql_fetch_row($res))
	{
		$stud=array();
		$stud["rno"]=$row[0];
		$stud["name"]=$row[1];
		array_push($response["mydata"],$stud);
	}
	$response["success"]="done";
	$data=json_encode($response);
	echo $data;

?>