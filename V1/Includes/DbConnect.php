<?php

//Récupération des constantes
require_once dirname(__FILE__).'/Constantes.php';

class DbConnect
/**************************************************\
 *BUT     : Réaliser la connexion.
 *ENTREE  : Les constantes de connexion.
 *SORTIE  : L'objet de connexion, ou un message d'erreur.
\**************************************************/
{   

  private $conn;

  function __construct()
  {
  }


  function Connect()
  {
    //Technique PDO
    /*
      //Mise en place de l'adresse de la base de donnée.
      $dsn="mysql:host=".SERVERNAME.";dbname=".DBNAME.";charset=UTF8";
      //Bloc try/catch pour récupérer les erreur le cas échéant.
      try
      {
        //Tentative de la connexion.
        $this->$conn = new PDO($dsn, USERNAME, PASSWORD);
        //Mise en place de la gestion des exceptions.
        $this->$conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
      }
      catch(PDOException $e)
      {
        echo "Échec de connexion : " . $e->getMessage();
      }
    */

    //Technique MySQLi

    $this->conn = new mysqli(SERVERNAME, USERNAME, PASSWORD, DBNAME); //Retourne mysqli_connect_erno()
    if (mysqli_connect_errno())
    {
      //echo "Problème de connexion : Erreur " . mysqli_connect_err();
    }
    else
    {
      //echo "Connexion réussit."."<br/>";
    }

    //echo "Return Conn"."<br/>";
    return $this->conn;
  }
}
 
?>