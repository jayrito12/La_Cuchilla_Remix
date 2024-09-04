package conexion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestSQLServer {

	// Variable de tipo properties
	private static Properties Propiedades;
	// Variable de Tipo FileReader Lee el archivo
	// físico de Properties y pasa su ruta a a la
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
	private static String NombreServer;

	public static void GetParametros() {
		// Se inicializa la variable Properties
		Propiedades = new Properties();

		try {// Se inicializa la FileReader pasndo como parametro
				// la ruta fisica del archivo propiertie
			RutaFisica = new FileReader("src\\properties\\configSqlServer.properties");
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
		NombreServer = Propiedades.getProperty("servidor.nombre");
		jdbc = Propiedades.getProperty("servidor.control");
		port= Propiedades.getProperty("servidor.port");
		ip = Propiedades.getProperty("ip");
		

	}

	public static void GetConexion() {
		// Se ejecuta el metodo que optiene los parametros de el archivo propertie
		GetParametros();
		try {

			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// esta cadena permite determinar configuraciones basi
			String DbUrl = jdbc + "://" + ip + "\\" + NombreServer + ";databaseName=" + db + ";";
			//System.out.println(DbUrl);
			DbUrl=jdbc+"://"+ip+":"+port+";"+"databaseName="+db+";";
			System.out.println(DbUrl);
			Conexion = DriverManager.getConnection(DbUrl, usuario, pwd);

			if (Conexion != null) {

				DatabaseMetaData dm = Conexion.getMetaData();

				System.out.println("Driver name: " + dm.getDriverName());

				System.out.println("Driver version: " + dm.getDriverVersion());

				System.out.println("Product name: " + dm.getDatabaseProductName());

				System.out.println("Product version: " + dm.getDatabaseProductVersion());
				
				//System.out.println("Alumno Juan Jaime Fuentes");
			
				
				
		

			}
		} catch (SQLException ex) {
			System.out.println("Error." + ex.getMessage());
		}

		try {
			Conexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	public static void main(String[] args) {

		GetConexion();

	}

}
