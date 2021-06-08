<?php 
	require_once dirname(__FILE__).'/Includes/DbOperation.php'; 
/*

UserRegister est un web service REST

*/

	$db = new DbOperation();

	$response;
	if ($_SERVER['REQUEST_METHOD']=='GET')
	{
		if (isset($_GET["username"]))
		{
			$result = $db->getUserByUserName($_GET["username"]);
			if (isset($result['USERNAME']) && isset($result['EMAIL']))
			{
				$response['status'] = true;
				$response['error'] = false;
				$response['message'] = "Utilisateur trouve";
				$response['username'] = $result['USERNAME'];
				$response['email'] = $result['EMAIL'];
			}
			else
			{
				$response['status'] = false;
				$response['error'] = false;
				$response['message'] = "Utilisateur non trouve";
			}
		}
		else
		{
			$response['status'] = false;
			$response['error'] = true;
			$response['message'] = "Erreur dans la reponse";
		}
	}
	else
	{
		$response['status'] = false;
		$response['error'] = true;
		$response['message'] = "Mauvais verbe de requete";
	}

	echo json_encode($response);
?>