package com.example.lapersistancedesdonnees4bddmysql;

public class Constante
{
    private static final String IP_CONFIG = "192.168.43.44";
    private static final String ROOT_URL = "http://"+IP_CONFIG+"/androidphpmysql/WebService/V1/"; //Récupérer IP correspondant à localhost.
    public static final String URL_REGISTER = ROOT_URL+"UserRegister.php";

    public static final String URL_LOGIN = ROOT_URL+"Login.php";

    public static final String URL_USER = ROOT_URL+"User.php";

    public static final String URL_RECHERCHE = ROOT_URL+"RechercheMulticritere.php";

    public static final String URL_DELETE = ROOT_URL+"Delete.php";

    public static final String URL_UPDATE = ROOT_URL+"Update.php";
}
