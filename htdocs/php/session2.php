<?php
	session_start();
?>
<pre>
	<?php
	echo $_SESSION["username"];
	echo "\n";
	echo $_SESSION["utype"];
	unset($_SESSION["utype"]);
	session_destroy();
	echo "\n";
	echo $_COOKIE["username"];
	echo "\n";
	echo $_COOKIE["password"];
	unset($_COOKIE["username"]);
?>

</pre>