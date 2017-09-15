<?php

	$conn=mysql_connect("localhost","root","");
	$limit_id=$_REQUEST["limit_id"];
	
		$arr=explode(",",$limit_id);
		$size=sizeof($arr);
	
	if(! $conn ) 
	{
      		die('Could not connect: ' . mysql_error());
   	}
	
	 mysql_select_db('expense_tracker');	

		for($i=0;$i<$size;$i++)
		{
			$sql = "delete from category_limit where limit_id='".$arr[$i]."'";
			$retval = mysql_query( $sql, $conn );
		}
   
   		if(! $retval ) 
		{
      			die('Could not enter data: ' . mysql_error());
   		}
$response["success"]="1";
echo json_encode($response);  
 mysql_close($conn);
	
?>