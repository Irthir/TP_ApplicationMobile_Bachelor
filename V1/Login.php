<?php 
	require_once dirname(__FILE__).'/Includes/DbOperation.php'; 
/*

UserRegister est un web service REST

*/

	$db = new DbOperation();

	if ($_SERVER['REQUEST_METHOD']=='GET')
	{
		if (isset($_GET["username"]) && isset($_GET["password"]))
		{
			$response;
			$result = $db->userLogin($_GET["username"], $_GET["password"]);
			if ($result)
			{
				$response['status'] = true;
				$response['error'] = false;
				$response['message'] = "Connexion réussie";
			}
			else
			{
				$response['status'] = false;
				$response['error'] = false;
				$response['message'] = "Couple nom d'utilisateur mot de passe invalide.";
			}
			echo json_encode($response);
		}
	}
?>