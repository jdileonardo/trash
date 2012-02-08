<?php
$con = mysql_connect("localhost:3306","root","password");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }

  // Create table
mysql_select_db("my_db", $con);

mysql_query("INSERT INTO Persons (FirstName, LastName, Age)
VALUES ('Peter', 'Griffin', '35')");

mysql_query("INSERT INTO Persons (FirstName, LastName, Age) 
VALUES ('Glenn', 'Quagmire', '33')");

mysql_close($con);

?>