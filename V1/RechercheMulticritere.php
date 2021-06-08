<?php 
	require_once dirname(__FILE__).'/Includes/DbOperation.php'; 
/*

UserRegister est un web service REST

*/

	$db = new DbOperation();
	
	$response;

	$response['status'] = false;
	$response['error'] = true;
	$response['message'] = "Verbe de requete incorrect.";
			
	if ($_SERVER['REQUEST_METHOD']=='GET')
	{
		if (!isset($_GET["username"]))
			$_GET["username"]='%';
		if (!isset($_GET["email"]))
			$_GET["email"]='%';
		if (!isset($_GET["localite"]))
			$_GET["localite"]='%';
		if (!isset($_GET["ddn"]))
			$_GET["ddn"]='%';

		$result = $db->getUserByMulticritere($_GET["username"], $_GET["email"], $_GET["localite"], $_GET["ddn"]);

		//var_dump($result);

		if ($result)
		{
			$response['status'] = true;
			$response['error'] = false;
			$response['message'] = "Resultat disponible";
			$response['resultat'] = $result;
			/*$tabResult = array();
			foreach ($result as $value)
			{
				$tabResult['username'] = $value["USERNAME"];
				$tabResult['email'] = $value["EMAIL"];
				$tabResult['localite'] = $value["LOCALITE"];
				$tabResult['ddn'] = $value["DDN"];
				array_push($response,$tabResult);
			}*/
		}
		else
		{
			$response['status'] = false;
			$response['error'] = true;
			$response['message'] = "Erreur dans la requete.";
		}
	}

	echo json_encode($response);
	
?>