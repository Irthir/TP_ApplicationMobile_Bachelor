<?php

//Récupération des constantes
require_once dirname(__FILE__).'/DbConnect.php';

class DbOperation
/*****************************************************\
 *BUT     : Réaliser les opérations sur l'utilisateur.
 *ENTREE  : Les entrée du formulaire.
 *SORTIE  : Les opérations sur l'utilisateur.
\*****************************************************/
{   

  private $conn;

  function __construct()
  {
    $db = new DbConnect();
    $this->conn = $db->Connect();
  }


  function isUserExists($username, $email)
  //BUT : Déterminer si le couple nom d'utilisateur ou le mail existe en base.
  {
    if (!isset($this->conn))
    {
      //echo "Connexion innaccessible";
    }
    //Préparation de la requête
    if(!$stmt = $this->conn->prepare("SELECT * FROM USERS WHERE USERNAME = ? OR EMAIL = ?"))
    {
      //echo "Échec dans la préparation.";
    }
    $stmt->bind_param("ss", $username, $email);

    if(!$stmt->execute())
    {
      //echo "Échec de la recherche.";
    }

    $stmt->store_result();

    return $stmt->num_rows>0;
  }

  function createUser($username, $pass, $email)
  //BUT : Créer un utilisateur dans la base à partir des données du formulaire.
  {
    if (!isset($this->conn))
    {
      //echo "Connexion innaccessible";
    }

    if (!$this->isUserExists($username,$email))
    {
      //Préparation de la requête
      if(!$stmt = $this->conn->prepare("INSERT INTO USERS VALUES (?,?,?);"))
      {
        //echo "Échec dans la préparation.";
      }

      $password = md5($pass);

      $stmt->bind_param("sss", $username, $password, $email);

      if($stmt->execute())
      {
        //echo "Inscription réussit pour ".$username.".";
        return 1;
      }
      else
      {
        //echo "Échec de l'inscription pour ".$username.".";
        return 2;
      }
    }
    else
    {
      return 0;
      //echo "Nom d'utilisateur ou Email déjà existant.";
    } 
  }

  function userLogin($username, $pass)
  //BUT : Déterminer si le couple nom d'utilisateur mot de passe existe en base.
  {
    if (!isset($this->conn))
    {
        //echo "Connexion innaccessible";
    }
    //Préparation de la requête
    if(!$stmt = $this->conn->prepare("SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?"))
    {
      //echo "Échec dans la préparation.";
    }

    $password = md5($pass);
    $stmt->bind_param("ss", $username, $password);

    if(!$stmt->execute())
    {
      //echo "Échec de la recherche.";
    }

    $stmt->store_result();

    return $stmt->num_rows>0;
  }

  function getUserByUserName($username)
  //BUT : Récupérer un utilisateur par son nom.
  {
    if (!isset($this->conn))
    {
      //echo "Connexion innaccessible";
    }
    //Préparation de la requête
    if(!$stmt = $this->conn->prepare("SELECT * FROM USERS WHERE USERNAME = ?"))
    {
      //echo "Échec dans la préparation.";
    }

    $stmt->bind_param("s", $username);

    if(!$stmt->execute())
    {
      //echo "Échec de la recherche.";
    }

    return $stmt->get_result()->fetch_assoc();
  }

  function getUserByMulticritere($username = '%', $email = '%', $localite = '%', $ddn = '%')
  {
    if ($username=="")
      $username = '%';
    if ($email=="")
      $email = '%';
    if ($localite=="")
      $localite = '%';
    if ($ddn=="")
      $ddn = '%';


    $req = "SELECT * FROM USERS WHERE (UPPER(USERNAME) LIKE UPPER('%".$username."%')) AND (UPPER(EMAIL) LIKE UPPER('%".$email."%')) AND (UPPER(LOCALITE) LIKE UPPER('%".$localite."%')) AND (UPPER(DDN) LIKE UPPER('%".$ddn."%'))";

    if (!$stmt = $this->conn->prepare($req))
    //if (!$stmt = $this->conn->prepare("SELECT * FROM USERS WHERE CONTAINS(USERNAME, ?) AND CONTAINS(EMAIL, ?) AND CONTAINS(LOCALITE, ?) AND CONTAINS(DDN, ?)"))
    {
      echo "Erreur dans la preparation";
      return false;
    }


    /*if (!$stmt->bind_param("ssss", $username, $email, $localite, $ddn))
    {
      echo "Erreur dans l'association";
      return false;
    }*/

    //var_dump($stmt);

    if (!$stmt->execute())
    {
      echo "Erreur dans l'execution'";
      return false;
    }

    $result = $stmt->get_result();

    $arrayResult = array();

    while($row = $result->fetch_assoc())
    {
      array_push($arrayResult,$row);
    }

    return $arrayResult;
  }

  function deleteUser($username)
  {
    $req = "DELETE FROM USERS WHERE USERNAME = ?";

    if (!$stmt = $this->conn->prepare($req))
    //if (!$stmt = $this->conn->prepare("SELECT * FROM USERS WHERE CONTAINS(USERNAME, ?) AND CONTAINS(EMAIL, ?) AND CONTAINS(LOCALITE, ?) AND CONTAINS(DDN, ?)"))
    {
      echo "Erreur dans la preparation";
      return false;
    }

    if (!$stmt->bind_param("s", $username))
    {
      echo "Erreur dans l'association";
      return false;
    }

    if (!$stmt->execute())
    {
      echo "Erreur dans l'execution";
      return false;
    }

    return true;
  }

  function UpdateUsername($username, $newusername)
  {
    $req = "UPDATE USERS SET USERNAME = ? WHERE USERNAME = ?";

    if (!$stmt = $this->conn->prepare($req))
    {
      echo "Erreur dans la preparation";
      return false;
    }

    if (!$stmt->bind_param("ss", $newusername, $username))
    {
      echo "Erreur dans l'association";
      return false;
    }

    if (!$stmt->execute())
    {
      echo "Erreur dans l'execution";
      return false;
    }

    return true;
  }

  function UpdateEmail($username, $newemail)
  {
    $req = "UPDATE USERS SET EMAIL = ? WHERE USERNAME = ?";

    if (!$stmt = $this->conn->prepare($req))
    {
      echo "Erreur dans la preparation";
      return false;
    }

    if (!$stmt->bind_param("ss", $newemail, $username))
    {
      echo "Erreur dans l'association";
      return false;
    }

    if (!$stmt->execute())
    {
      echo "Erreur dans l'execution";
      return false;
    }

    return true;
  }

  function UpdateLocalite($username, $newlocalite)
  {
    $req = "UPDATE USERS SET EMAIL = ? WHERE USERNAME = ?";

    if (!$stmt = $this->conn->prepare($req))
    {
      echo "Erreur dans la preparation";
      return false;
    }

    if (!$stmt->bind_param("ss", $newlocalite, $username))
    {
      echo "Erreur dans l'association";
      return false;
    }

    if (!$stmt->execute())
    {
      echo "Erreur dans l'execution";
      return false;
    }

    return true;
  }

  function UpdateDDN($username, $newddn)
  {
    $req = "UPDATE USERS SET DDN = ? WHERE USERNAME = ?";

    if (!$stmt = $this->conn->prepare($req))
    {
      echo "Erreur dans la preparation";
      return false;
    }

    if (!$stmt->bind_param("ss", $newddn, $username))
    {
      echo "Erreur dans l'association";
      return false;
    }

    if (!$stmt->execute())
    {
      echo "Erreur dans l'execution";
      return false;
    }

    return true;
  }
}

?>