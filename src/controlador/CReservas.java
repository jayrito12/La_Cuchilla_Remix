package controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import conexion.ConexionBDSQLServer;
import objetos.OReservas;

public class CReservas {
	public static Connection Conexion = null;

	public List<String> obtenerClientes() {
		List<String> Clientes = new ArrayList<>();
		Conexion = ConexionBDSQLServer.GetConexion();
		try {
			String sql = "SELECT nombre FROM clientes";
			Statement Statement = Conexion.createStatement();
			ResultSet resultSet = Statement.executeQuery(sql);

			while (resultSet.next()) {
				Clientes.add(resultSet.getString("nombre"));
			}
			resultSet.close();
			Statement.close();
			Conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Clientes;
	}

	public List<OReservas> obtenerTodosLasReservas() {
		Conexion = ConexionBDSQLServer.GetConexion();
		List<OReservas> Reservas = new ArrayList<>();
		String sql = "SELECT * FROM reservas";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int idReserva = resultSet.getInt("id_reserva");
				int idCliente = resultSet.getInt("id_cliente");
				Date fecha = resultSet.getDate("fecha_hora_reserva");

				OReservas reservas = new OReservas();
				reservas.setId_reserva(idReserva);
				reservas.setId_cliente(idCliente);
				reservas.setFechaReserva(fecha);
				Reservas.add(reservas);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Reservas;
	}

	public String obtenerNombreCliente(int id) {
		String nombre = "";
		Conexion = ConexionBDSQLServer.GetConexion();
		try {
			String sql = "SELECT nombre FROM clientes WHERE id_cliente = " + id;
			Statement Statement = Conexion.createStatement();
			ResultSet resultSet = Statement.executeQuery(sql);

			while (resultSet.next()) {
				nombre = (resultSet.getString("nombre"));
			}
			resultSet.close();
			Statement.close();
			Conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nombre;
	}
	
	public int obteneridCliente(String nombre) {
		int id = 0;
		Conexion = ConexionBDSQLServer.GetConexion();
		try {
			String sql = "SELECT id_cliente FROM clientes WHERE nombre = '" + nombre + "'";
			Statement Statement = Conexion.createStatement();
			ResultSet resultSet = Statement.executeQuery(sql);

			while (resultSet.next()) {
				id = (resultSet.getInt("id_cliente"));
			}
			resultSet.close();
			Statement.close();
			Conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

	public boolean registrarReserva(OReservas reserva) {

		Conexion = ConexionBDSQLServer.GetConexion();
		try {
			String sql = "INSERT INTO reservas (id_cliente, fecha_hora_reserva) VALUES (?, ?)";
			PreparedStatement preparedStatement = Conexion.prepareStatement(sql);
			preparedStatement.setInt(1, reserva.getId_cliente());
			preparedStatement.setDate(2, reserva.getFechaReserva());
			preparedStatement.executeUpdate();

			Conexion.close();
			preparedStatement.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error al registrar el cliente en la base de datos.");
		}
	}

	public boolean actualizarReserva(OReservas reserva) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "UPDATE reservas SET fecha_hora_reserva = ? WHERE id_reserva = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setDate(1, reserva.getFechaReserva());
			statement.setInt(2, reserva.getId_reserva());
			int rowsUpdated = statement.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean eliminarReserva(int IReservas) {
		Conexion = ConexionBDSQLServer.GetConexion();
		String sql = "DELETE FROM reservas WHERE id_reserva = ?";
		try {
			PreparedStatement statement = Conexion.prepareStatement(sql);
			statement.setInt(1, IReservas);
			int rowsDeleted = statement.executeUpdate();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
