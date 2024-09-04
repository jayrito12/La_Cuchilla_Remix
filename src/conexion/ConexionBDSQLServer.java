package conexion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBDSQLServer {

	// Variable de tipo properties
	private static Properties Propiedades;
	// Variable de Tipo FileReader Lee el archivo
	// f�sico de Properties y pasa su ruta a a la
	// variale propiedades
	private static FileReader RutaFisica;
	// Variable que permite realizar la conexi�n a la base de datos
	public static Connection Conexion = null;
	// Variables globales que se obtendran valores desde archivo propeties
	private static String usuario;
	private static String pwd;
	private static String db;
	private static String ip;
	private static String jdbc;
	private static String port;


	public static void GetParametros() {
		// Se inicializa la variable Properties
		Propiedades = new Properties();

		try {// Se inicializa la FileReader pasndo como parametro
				// la ruta fisica del archivo propiertie
			RutaFisica = new FileReader(
					"src\\properties\\configSqlServer.properties");
		} catch (FileNotFoundException eRuta) {
			// TODO Auto-generated catch block
			eRuta.printStackTrace();
		}

		try {
			Propiedades.load(RutaFisica);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		usuario = Propiedades.getProperty("servidor.usuario");
		pwd = Propiedades.getProperty("servidor.password");
		db = Propiedades.getProperty("bd.name");
		jdbc = Propiedades.getProperty("servidor.control");
		port= Propiedades.getProperty("servidor.port");
		ip = Propiedades.getProperty("ip");

	}

	public static Connection GetConexion() {
		// Se ejecuta el metodo que optiene los parametros de el archivo propertie
		GetParametros();
		// Inicializa la conexion en nulo
		Conexion = null;
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			// esta cadena permite determinar configuraciones basi
			
			 String      DbUrl = jdbc + "://" +ip+":"+port+";"+"databaseName="+db+";";

			//System.out.println(DbUrl);
			Conexion = DriverManager.getConnection(DbUrl, usuario, pwd);
			
			// Si la conexión fue exitosa debe devolver una variable con un Valor diferente
			// de Null
		} catch (SQLException ex) {
			System.err.println("Error." + ex.getMessage());
			Conexion = null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Conexion = null;
		}

		return Conexion;
	}
	
	public static void main(String[] args) {
		GetConexion();
	}

}
