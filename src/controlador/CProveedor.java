package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBDSQLServer;
import objetos.OProveedor;

public class CProveedor {
	public static Connection Conexion = null;
	
	public boolean registrarProveedor(OProveedor proveedor) {

		Conexion = ConexionBDSQLServer.GetConexion();
		try {
			String sql = "INSERT INTO proveedores (nombre, telefono, correo) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = Conexion.prepareStatement(sql);
			preparedStatement.setString(1, proveedor.getNombre());
			preparedStatement.setString(2, proveedor.getTelefono());
			preparedStatement.setString(3, proveedor.getCorreo());
			preparedStatement.executeUpdate();

			Conexion.close();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al registrar el cliente en la base de datos.");
		}
	}

	public boolean actualizarProveedor(OProveedor proveedor) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "UPDATE proveedores SET nombre = ?, telefono = ?, correo = ? WHERE id_proveedor = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setString(1, proveedor.getNombre());
			statement.setString(2, proveedor.getTelefono());
			statement.setString(3, proveedor.getCorreo());
			statement.setInt(4, proveedor.getIdProveedor());
			int rowsUpdated = statement.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminarProveedor(int idProvedor) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "DELETE FROM proveedores WHERE id_proveedor = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setInt(1, idProvedor);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<OProveedor> obtenerTodosLosProveedores() {
		Conexion = ConexionBDSQLServer.GetConexion();
		List<OProveedor> Proveedores = new ArrayList<>();
		String sql = "SELECT * FROM proveedores";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int idproveedor = resultSet.getInt("id_proveedor");
				String nombre = resultSet.getString("nombre");
				String telefono = resultSet.getString("telefono");
				String correo = resultSet.getString("correo");
				
				OProveedor proveedor = new OProveedor();
				proveedor.setIdProveedor(idproveedor);
				proveedor.setNombre(nombre);
				proveedor.setTelefono(telefono);
				proveedor.setCorreo(correo);
				Proveedores.add(proveedor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Proveedores;
	}
}
