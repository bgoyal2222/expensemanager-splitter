<html>
<head>
</head>
<body>
<?php
	$a;
	$b=NULL;
	$c=1;
	unset($c);
	$d=2;
	if(is_null($a))
		echo "\$a is null\n";
	if(is_null($b))
	{
		echo"<br>";
		echo "\$b is null\n";
	}
	if(is_null($c))
	{
		echo"<br>";
		echo "\$c is null\n";
	}
	if(is_null($d))
	{
		echo"<br>";
		echo "\$d is null\n";
	}
?>
</body>
</html>

