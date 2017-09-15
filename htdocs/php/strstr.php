<?php
	echo chr(65)."<br>";
	$email='user@example.com';
	$domain=strstr($email,'@');
	echo $domain."<br>";
	$email='USER@EXAMPLE.com';
	$domain=strstr($email,'E');
	echo $domain."<br>";
?>