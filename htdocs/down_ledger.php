<?php

	mysql_connect("localhost","root","");
	mysql_select_db("expense_tracker");
	

	$uid=$_REQUEST["uid"];
	$from=$_REQUEST["from"];
	$to=$_REQUEST["to"];
	
	$res=mysql_query("select expense_name,amount,date from expense where user_id='".$uid."' and date>='".$from."' and date<='".$to."' order by date desc");


$setData="Name\tAmount\tDate\n";

while($rec = mysql_fetch_row($res))  {
  $rowLine = '';
  foreach($rec as $value)       {
    if(!isset($value) || $value == "")  {
      $value = "\t";
    }   else  {
//It escape all the special charactor, quotes from the data.
      $value = strip_tags(str_replace('"', '""', $value));
      $value = '"' . $value . '"' . "\t";
    }
    $rowLine .= $value;
  }
  $setData .= trim($rowLine)."\n";
}
 $setData = str_replace("\r", "", $setData);

if ($setData == "") {
  $setData = "no Data";
}

/*header("Content-type: application/octet-stream");
header("Content-Disposition: attachment; filename=a1.doc");
header("Pragma: no-cache");
header("Expires: 0");

*/
//It will print all the Table row as Excel file row with selected column name as header.

$r=rand(0,10000000);
$file="ExpenseList".$r.".xls";

$fp=fopen($file,"w");
fwrite($fp,$setData."\n");
fclose($fp);

//echo ucwords($setMainHeader)."\n".$setData."\n";
//$f_name['fname']=$file;
$response["fname"]=$file;
	$data=json_encode($response);
	echo $data;

//$fname['fname']=$file;
//$ledger_file=json_encode($fname);
//echo $ledger_file;