package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBDSQLServer;
import objetos.OEmpleados;

public class CEmpleados {
public static Connection Conexion = null;
	
	public boolean registrarEmpleado(OEmpleados empleado) {

		Conexion = ConexionBDSQLServer.GetConexion();
		try {
			String sql = "INSERT INTO empleados (nombre, telefono, correo) VALUES (?, ?, ?)";
			PreparedStatement preparedStatement = Conexion.prepareStatement(sql);
			preparedStatement.setString(1, empleado.getNombre());
			preparedStatement.setString(2, empleado.getTelefono());
			preparedStatement.setString(3, empleado.getCorreo());
			preparedStatement.executeUpdate();

			Conexion.close();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al registrar el empleado en la base de datos.");
		}
	}

	public boolean actualizarEmpleado(OEmpleados empleado) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "UPDATE empleados SET nombre = ?, telefono = ?, correo = ? WHERE id_empleado = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setString(1, empleado.getNombre());
			statement.setString(2, empleado.getTelefono());
			statement.setString(3, empleado.getCorreo());
			statement.setInt(4, empleado.getIdEmpleado());
			int rowsUpdated = statement.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminarEmpleado(int idempleado) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "DELETE FROM empleados WHERE id_empleado = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setInt(1, idempleado);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<OEmpleados> obtenerTodosLosEmpleados() {
		Conexion = ConexionBDSQLServer.GetConexion();
		List<OEmpleados> Empleados = new ArrayList<>();
		String sql = "SELECT * FROM empleados";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int idEmpleados = resultSet.getInt("id_empleado");
				String nombre = resultSet.getString("nombre");
				String telefono = resultSet.getString("telefono");
				String correo = resultSet.getString("correo");
				OEmpleados empleado = new OEmpleados();
				empleado.setIdEmpleado(idEmpleados);
				empleado.setNombre(nombre);
				empleado.setTelefono(telefono);
				empleado.setCorreo(correo);
				Empleados.add(empleado);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Empleados;
	}
}
