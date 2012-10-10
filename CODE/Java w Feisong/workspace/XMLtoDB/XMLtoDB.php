<?php
$feed = simplexml_load_file('URLEHERE');
$con = mysql_connect(","","");
if (!$con)
  {
  die('Could not connect: ' . mysql_error());
  }
mysql_select_db("wordpress", $con);
foreach($feed->channel->item as $item)
{
	//Basic
	$title = $item->title;
	$link = $item->link;
	
	//Namespaces
	$d2lmeta = $item->children('');
	
  	$telephone = $d2lmeta->telephone;
 	$addr1 = $d2lmeta->addr1;
 	$addr2 = $d2lmeta->addr2;
 	$city = $d2lmeta->city;
 
 	//Basic
 	$description = $item->description;

	$mediafiles=array();
	$mediadescriptions=array();
	$mediathumbs=array();
	$mediatitles=array();

	/*echo "Title:".$title."\n";
	echo "Link:".$link."\n";
	echo "Telephone:".$telephone."\n";
	echo "Address1:".$addr1."\n";
	echo "Address2:".$addr2."\n";
	echo "City:".$city."\n";
	echo "Description:".$description."\n";*/
  //Media stuff
  	$media = $item->children('http://search.yahoo.com/mrss/');
  	$i = 0;
 	foreach ($media->group as $group) { 
 		$mediatitle[$i] = $group->description;
 		$mediadescription[$i] =$group->description;
		$mediafile[$i] = $group->content->attributes()->url;
		$mediathumbnail[$i]= $group->thumbnail->attributes()->url;
		
		/*echo "MEDIACONTENT:".$i."\n";
		echo "MedTitle:".$mediatitle[$i]."\n";
 		echo "MedDes:".$mediadescription[$i]."\n";
		echo "MedFile".$mediafile[$i]."\n";
		echo "MedThumb".$mediathumbnail[$i]."\n";*/
		
		$i++;
  }
  //MySQL STUFF
  
  
}
mysql_close($con);