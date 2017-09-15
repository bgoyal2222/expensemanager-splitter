<?php

	$conn=mysql_connect("localhost","root","");

	$uid=$_REQUEST["user_id"];		
	$gid=$_REQUEST["gex_id"];
	$addedBy=$_REQUEST["addedBy"];
	
	$arr=explode(",",$uid);
	$size=sizeof($arr);
	
      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
	
	 mysql_select_db('expense_tracker');

   	for($i=0;$i<$size;$i++)
	{
		if($arr[$i]==$addedBy)
		{
   			$sql = "INSERT INTO involved (gex_id,user_id,status) VALUES ( '".$gid."', '".$arr[$i]."', 'paid')";
			$retval = mysql_query( $sql, $conn );
		}
		else
		{
			$sql = "INSERT INTO involved (gex_id,user_id,status) VALUES ( '".$gid."', '".$arr[$i]."', 'unpaid')";
			$retval = mysql_query( $sql, $conn );
		}
	}
      
  
   
   
   if(! $retval ) {
      die('Could not enter data: ' . mysql_error());
   }
   
  
   $response["success"]="1";
echo json_encode($response);  
 mysql_close($conn);
?>