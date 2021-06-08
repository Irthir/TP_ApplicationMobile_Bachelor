<?php 
	require_once dirname(__FILE__).'/Includes/DbOperation.php'; 
/*

UserRegister est un web service REST

*/

	$db = new DbOperation();

	$response;

	if ($_SERVER['REQUEST_METHOD']=='POST')
	{
		if (isset($_POST["usernamebase"]))
		{
			$response['status'] = true;
			$response['error'] = false;
			$response['message'] = "";
			//Gestion de la mise à jour de l'email.
			if (isset($_POST["email"]))
			{
				
				$result = $db->UpdateEmail($_POST["usernamebase"],$_POST["email"]);
				if ($result)
				{
					$response['status'] = $response['status']&&true;
					$response['error'] = $response['status']||false;
					$response['message'] = $response['message']." réussite email ";
					$response['newemail'] = $_POST["email"];
				}
				else
				{
					$response['status'] = false;
					$response['error'] = true;
					$response['message'] = $response['message']." échec email ";
				}
			}
			//Gestion de la mise à jour de la localite.
			if (isset($_POST["localite"]))
			{
				$result = $db->UpdateLocalite($_POST["usernamebase"],$_POST["localite"]);
				if ($result)
				{
					$response['status'] = $response['status']&&true;
					$response['error'] = $response['status']||false;
					$response['message'] = $response['message']." réussite localite ";
					$response['newlocalite'] = $_POST["localite"];
				}
				else
				{
					$response['status'] = false;
					$response['error'] = true;
					$response['message'] = $response['message']." échec localite ";
				}
			}
			//Gestion de la mise à jour de la date de naissance.
			if (isset($_POST["ddn"]))
			{
				$result = $db->UpdateDDN($_POST["usernamebase"],$_POST["ddn"]);
				if ($result)
				{
					$response['status'] = $response['status']&&true;
					$response['error'] = $response['status']||false;
					$response['message'] = $response['message']." réussite ddn ";
					$response['newddn'] = $_POST["ddn"];
				}
				else
				{
					$response['status'] = false;
					$response['error'] = true;
					$response['message'] = $response['message']." échec ddn ";
				}
			}
			//Gestion de la mise à jour du nom d'utilisateur.
			if (isset($_POST["username"]))
			{
				$result = $db->UpdateUsername($_POST["usernamebase"],$_POST["username"]);
				if ($result)
				{
					$response['status'] = $response['status']&&true;
					$response['error'] = $response['status']||false;
					$response['message'] = $response['message']." réussite username ";
					$response['newusername'] = $_POST["username"];
				}
				else
				{
					$response['status'] = false;
					$response['error'] = true;
					$response['message'] = $response['message']." échec username ";
				}
			}
		}
		else
		{
			$response['status'] = false;
			$response['error'] = true;
			$response['message'] = "Nom d'utilisateur de base manquant.";
		}
	}
	else
	{
		$response['status'] = false;
		$response['error'] = true;
		$response['message'] = "Methode de requete invalide.";
	}

	echo json_encode($response);
?>