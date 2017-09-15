<?php
session_start();
$_SESSION['username']="Vishal";
$_SESSION['utype']="admin";
setcookie('username',"n");
setcookie('password',"123456",time()+1000);
?>

<html>
<head>
</head>
<body>
<form action=session2.php method="post">
<input type="submit" value="submit">
</form>
</body>
</html>