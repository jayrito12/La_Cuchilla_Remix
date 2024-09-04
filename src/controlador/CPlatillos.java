package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBDSQLServer;
import objetos.OPlatillo;

public class CPlatillos {
	public static Connection Conexion = null;

	public boolean registrarPlatillo(OPlatillo Platillo) {
		
		Conexion = ConexionBDSQLServer.GetConexion();
		try {
			String sql = "INSERT INTO platillos (nombre, precio, descripcion) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = Conexion.prepareStatement(sql);
			preparedStatement.setString(1, Platillo.getNombre());
			preparedStatement.setDouble(2, Platillo.getPrecio());
			preparedStatement.setString(3, Platillo.getDescripcion());
			preparedStatement.executeUpdate();
			
			Conexion.close();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al registrar el cliente en la base de datos.");
		}
	}

	public boolean actualizarPlatillo(OPlatillo Platillo) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "UPDATE platillos SET nombre = ?, precio = ?, descripcion = ? WHERE id_platillo = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setString(1, Platillo.getNombre());
			statement.setDouble(2, Platillo.getPrecio());
			statement.setString(3, Platillo.getDescripcion());
			statement.setInt(4, Platillo.getId_platillo());
			int rowsUpdated = statement.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminarPlatillo(int IdPlatillo) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "DELETE FROM platillos WHERE id_platillo = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setInt(1, IdPlatillo);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<OPlatillo> obtenerTodosLosPlatillos() {
		Conexion = ConexionBDSQLServer.GetConexion();
		List<OPlatillo> Platillos = new ArrayList<>();
		String sql = "SELECT * FROM platillos";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int idPlatillo = resultSet.getInt("id_platillo");
				String nombre = resultSet.getString("nombre");
				Double precio = resultSet.getDouble("precio");
				String descripcion = resultSet.getString("descripcion");
				
				OPlatillo Platillo = new OPlatillo();
				Platillo.setId_platillo(idPlatillo);
				Platillo.setNombre(nombre);
				Platillo.setDescripcion(descripcion);
				Platillo.setPrecio(precio);
				Platillos.add(Platillo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Platillos;
	}
}
