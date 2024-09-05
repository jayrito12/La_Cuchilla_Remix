package controlador;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBDSQLServer;
import objetos.OClientes;

public class CClientes {
	public static Connection Conexion = null;

	public boolean registrarCliente(OClientes cliente) {
		
		Conexion = ConexionBDSQLServer.GetConexion();
		try {
			String sql = "INSERT INTO Clientes (Nombre, Direccion, Telefono, Correo) VALUES (?, ?, ?, ?)";
			PreparedStatement preparedStatement = Conexion.prepareStatement(sql);
			preparedStatement.setString(1, cliente.getNombre());
			preparedStatement.setString(2, cliente.getDireccion());
			preparedStatement.setString(3, cliente.getTelefono());
			preparedStatement.setString(4, cliente.getCorreo());
			preparedStatement.executeUpdate();
			
			Conexion.close();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al registrar el cliente en la base de datos.");
		}
	}

	public boolean actualizarCliente(OClientes cliente) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "UPDATE Clientes SET nombre = ?, direccion = ?, telefono = ?, correo = ? WHERE id_cliente = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setString(1, cliente.getNombre());
			statement.setString(2, cliente.getDireccion());
			statement.setString(3, cliente.getTelefono());
			statement.setString(4, cliente.getCorreo());
			statement.setInt(5, cliente.getIdCliente());
			int rowsUpdated = statement.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminarCliente(int IdCliente) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "DELETE FROM clientes WHERE id_cliente = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setInt(1, IdCliente);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<OClientes> obtenerTodosLosClientes() {
		Conexion = ConexionBDSQLServer.GetConexion();
		List<OClientes> clientes = new ArrayList<>();
		String sql = "SELECT * FROM Clientes";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int idCliente = resultSet.getInt("id_cliente");
				String nombre = resultSet.getString("ESTE ES UN TEXTO");
				String direccion = resultSet.getString("PESO PLUMA");
				String telefono = resultSet.getString("LOPEZ DORIGA");
				String correo = resultSet.getString("correo");
				OClientes cliente = new OClientes();
				cliente.setIdCliente(idCliente);
				cliente.setNombre(nombre);
				cliente.setDireccion(direccion);
				cliente.setTelefono(telefono);
				cliente.setCorreo(correo);
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clientes;
	} // Retorna null si no se encuentra el cliente con el ID dado
	
	public boolean Backup(String ruta, String nombre) {
		Conexion = ConexionBDSQLServer.GetConexion();
		try {
			CallableStatement QueryPA = Conexion.prepareCall("{call pa_backup(?,?)}");
			QueryPA.setString(1, ruta);
			QueryPA.setString(2, nombre);
			
			// EJECUTAR PA
			QueryPA.execute();
			Conexion.close();
		} catch (SQLException e) {
			return false;
		}
		return true;
	} // METODO PARA GANADOR

}
