<?php

	$conn=mysql_connect("localhost","root","");


	$uid=$_REQUEST["uid"];
	$gid=$_REQUEST["gid"];

      	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
	mysql_select_db('expense_tracker');
	$sql="select * from participants where group_id='".$gid."'";
	
	
   	$res = mysql_query( $sql, $conn );
	$cnt=mysql_num_rows($res);
	
	if($cnt>0)
	{
		$sql = "delete from participants where group_id='".$gid."' and user_id='".$uid."'";
      
   		$retval = mysql_query( $sql, $conn );
   
   		if(! $retval )
		{
      			die('Could not delete data: ' . mysql_error());
   		}
		else
		{
			$cnt=$cnt-1;
		}
   		$response["success"]="1";
	}
	if($cnt==0)
	{
		$sql = "delete from groups where group_id='".$gid."'";
      
   		$retval = mysql_query( $sql, $conn );
   
   		if(! $retval )
		{
      			die('Could not enter data: ' . mysql_error());
   		}
   		$response["success"]="1";
	}	

	   	
echo json_encode($response);  
 mysql_close($conn);
?>