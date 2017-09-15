<html>
<head>
<title>
</title>
</head>
<?php
$s1="";
$s2="";
if(isset($_REQUEST["submit"]))
{
	$s1=$_REQUEST["txt1"];
	$s2=$_REQUEST["txt2"];
}
?>
<body>
<form method="post">
<input name="txt1" type="text" value="<?php echo $s1;?>">
<input name="txt2" type="text" value="<?php echo $s2;?>"/>
<input name="submit" type="submit" value="Submit"/>
</form>
</body>
</html>
