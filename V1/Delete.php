<?php 
	require_once dirname(__FILE__).'/Includes/DbOperation.php'; 
/*

UserRegister est un web service REST

*/

	$db = new DbOperation();

	$response;
	if ($_SERVER['REQUEST_METHOD']=='POST')
	{
		if (isset($_POST["username"]))
		{
			$result = $db->deleteUser($_POST["username"]);
			if ($result)
			{
				$response['status'] = true;
				$response['error'] = false;
				$response['message'] = "Suppression réussie";
			}
			else
			{
				$response['status'] = false;
				$response['error'] = true;
				$response['message'] = "Échec de la suppression";
			}
		}
		else
		{
			$response['status'] = false;
			$response['error'] = true;
			$response['message'] = "Variable delete non trouvée";
		}
	}
	else
	{
		$response['status'] = false;
		$response['error'] = true;
		$response['message'] = "Mauvaise méthode de requête";
	}

	echo json_encode($response);
?>