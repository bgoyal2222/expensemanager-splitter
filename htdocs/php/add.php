<html>
<head>
</head>
<?php
	$value="";
	if(isset($_REQUEST["btn1"]))
	{
		$value=$_REQUEST["text1"]+$_REQUEST["text2"];
	}
?>

<body>
<form>
<input type="text" name="text1"/>
<input type="text" name="text2"/>
<input type="submit" name="btn1" value="ADD"/>
<input type="text" name="text3" value="<?php echo $value;?>"/>		
</form>
</body>
</html>