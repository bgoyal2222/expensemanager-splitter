<?php

	$conn=mysql_connect("localhost","root","");

	mysql_select_db('expense_tracker');

	$date=date('Y-m-d');
	$uid=$_REQUEST["uid"];	

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
   
   	$res = mysql_query("select limit_id from category_limit where user_id='".$uid."' and to_date<'".$date."'");

	$response["limit_data"]=array();
	
	

	while($row=mysql_fetch_row($res))
	{
		$limit=array();
		$limit["limit_id"]=$row[0];
		array_push($response["limit_data"],$limit);
	}
      
   	
   
   $response["success"]="1";
   $data=json_encode($response);
   echo $data;  
   mysql_close($conn);


?>