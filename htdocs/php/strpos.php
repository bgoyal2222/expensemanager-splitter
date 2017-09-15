<?php

$mystring='Personal Computer Coaching';
$findme='P';
echo strpos($mystring,$findme)."<br>";
$findme='C';
echo strpos($mystring,$findme)."<br>";
echo strpos($mystring,$findme,10)."<br>";
echo strpos($mystring,'z')."<br>";
$mystring='Personal Computer Coaching';

?>