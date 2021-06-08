<?php 
	require_once dirname(__FILE__).'/Includes/DbOperation.php'; 
/*

UserRegister est un web service REST

*/

	$db = new DbOperation();

	if ($_SERVER['REQUEST_METHOD']=='POST')
	{
		if (isset($_POST["username"]) && isset($_POST["password"]) && isset($_POST["email"]))
		{
			$response;
			$result = $db->createUser($_POST["username"], $_POST["password"], $_POST["email"]);
			switch($result)
			{
				case 0:
					//Cas Utilisateur existe déjà.
					$response['status'] = false;
					$response['error'] = true;
					$response['message'] = "Existe deja";
				break;
				case 1:
					//Cas Tout a fonctionné.
					$response['status'] = true;
					$response['error'] = false;
					$response['message'] = "utilisateur cree";
				break;
				case 2:
					//Cas Erreur d'insertion.
					$response['status'] = false;
					$response['error'] = true;
					$response['message'] = "Erreur d'insertion";
				break;
				default:
					//Cas non répertorié.
					echo "Cas non répertorié.";
				break;
			}
			echo json_encode($response);
		}
	}
?>