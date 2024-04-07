package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;

import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {


	private ReservationDao() {
	}


	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATION_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) AS count FROM Reservation;";

	public long create(Reservation reservation) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(CREATE_RESERVATION_QUERY);
			preparedStatement.setInt(1, reservation.getClient_id());
			preparedStatement.setInt(2, reservation.getVehicle_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));

			preparedStatement.execute();
			return 1;
		} catch (SQLException error) {
			error.printStackTrace();
			return 0;
		}
	}

	public static long delete(Reservation reservation) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(DELETE_RESERVATION_QUERY);
			preparedStatement.setInt(1, reservation.getId());
			preparedStatement.execute();
			return 1;
		} catch (SQLException error) {
			error.printStackTrace();
			return 0;
		}
	}

	public static Reservation findById(int id) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATION_QUERY);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			ResultSet result = preparedStatement.getResultSet();
			if (result.next()) {
				return new Reservation(result.getInt("id"), result.getInt("client_id"), result.getInt
						("vehicle_id"), result.getDate("debut").toLocalDate(),
						result.getDate("fin").toLocalDate());
			} else {
				System.out.println("Aucune Réservation ne possède cet identifiant !");
				return null;
			}
		} catch (SQLException error) {
			error.printStackTrace();
			return null;
		}
	}

	public List<Reservation> findResByClientId(int client_id) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setInt(1, client_id);
			preparedStatement.execute();

			ArrayList<Reservation> listeRes = new ArrayList<>();
			ResultSet result = preparedStatement.getResultSet();
			while (result.next()) {
				listeRes.add(new Reservation(result.getInt("id"), result.getInt("client_id"),
						result.getInt("vehicle_id"), result.getDate("debut").toLocalDate(),
						result.getDate("fin").toLocalDate()));
			}
			return listeRes;
		} catch (SQLException error) {
			error.printStackTrace();
			return null;
		}
	}

	public List<Reservation> findResByVehicleId(int vehicle_id) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setLong(1, vehicle_id);
			preparedStatement.execute();

			ArrayList<Reservation> listeRes = new ArrayList<>();
			ResultSet result = preparedStatement.getResultSet();
			while (result.next()) {
				listeRes.add(new Reservation(result.getInt("id"), result.getInt("client_id"),
						Math.toIntExact(vehicle_id), result.getDate("debut").toLocalDate(),
						result.getDate("fin").toLocalDate()));
			}
			return listeRes;
		} catch (SQLException error) {
			error.printStackTrace();
			return null;
		}
	}

	public List<Reservation> findAll() throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_RESERVATIONS_QUERY);
			preparedStatement.execute();

			ArrayList<Reservation> Liste = new ArrayList<>();
			ResultSet result = preparedStatement.getResultSet();
			while (result.next()) {
				Liste.add(new Reservation(result.getInt("id"), result.getInt("client_id"), result.getInt("vehicle_id"), result.getDate("debut").toLocalDate(), result.getDate("fin").toLocalDate()));
			}
			return Liste;
		} catch (SQLException error) {
			error.printStackTrace();
			return null;
		}
	}

	public int count() throws DaoException {
		int count = 0;
		try {
			Connection connection = ConnectionManager.getConnection();

			// Utilize a Statement for the select query
			PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RESERVATIONS_QUERY);
			preparedStatement.execute();

			ResultSet resultSet = preparedStatement.getResultSet();

			// Retrieve the count value from the result set
			if (resultSet.next()) {
				count = resultSet.getInt("count");
			}
			return count;

		} catch (SQLException error) {
			error.printStackTrace();
			return count;
		}
	}
}
