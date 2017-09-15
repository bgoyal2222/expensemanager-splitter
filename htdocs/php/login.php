<?php
	if(isset($_REQUEST["sub"]))
	{
		if($_REQUEST["uname"]=="Vishal" && $_REQUEST["pwd"]=="123456")
		{
			header('location:home.php');
		}
		else
		{
			header('location:login.php');	
		}
	}
	
?>
<html>
<head>
<title>
</title>
</head>

<body>
<form method="post">

<table>

<tr>
	<td>Username:</td>
	<td>
	<input type="text" name="uname">
	</td>
</tr>

<tr>
	<td>Password:</td>
	<td>
	<input type="password" name="pwd">
	</td>
</tr>

<tr>
	<td>
	<input type="submit" value="Submit" name="sub">
	</td>
</tr>

</table>
</form>
</body>
</html>