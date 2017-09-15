<?php

	$conn=mysql_connect("localhost","root","");
		

	$name=$_REQUEST["name"];
	$amt=$_REQUEST["amount"];
	$date=$_REQUEST["date"];

	$cid=$_REQUEST["category"];
	$uid=$_REQUEST["uid"];

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
   
   	$sql = "INSERT INTO group_ex (expense_name,amount,date,group_id,category_id,user_id) VALUES ( '".$name."', '".$amt."', '".$date."', 1,'".$cid."','".$uid."')";
	


   mysql_select_db('expense_tracker');
   $retval = mysql_query( $sql, $conn );
   
   if(! $retval ) {
      die('Could not enter data: ' . mysql_error());
   }
   
	$insertedId=mysql_insert_id();
        
	$res=mysql_query("select * from group_ex where gex_id='".$insertedId."'");

	$row=mysql_fetch_row($res);
	
	$response["gex_id"]=$insertedId;
	$response["expense_name"]=$row[1];
	$response["amount"]=$row[2];
	$response["date"]=$row[3];
	$response["group_id"]=$row[4];
	$response["category_id"]=$row[5];
	$response["user_id"]=$row[6];  

   $response["success"]="1";
echo json_encode($response);  
 mysql_close($conn);
?>