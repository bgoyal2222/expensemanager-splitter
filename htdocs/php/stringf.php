<html>
<head>
</head>
<body>
<?php
$str="    pcc    ";
echo rtrim($str)."*<br>";
echo ltrim($str)."*<br>";
$name="*****Personal Computer Coaching*****";
echo rtrim($name,"*")."<br>";
echo ltrim($name,"*")."<br>";
echo ltrim(rtrim($name,"*"),"*")."<br>";
?>