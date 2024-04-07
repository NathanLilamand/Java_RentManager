package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private VehicleDao() {}

	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO VEHICLE(constructeur, modele, nb_places) VALUES(?, ?, ?);";

	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM VEHICLE WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM VEHICLE WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM VEHICLE;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) AS count FROM VEHICLE;";


	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNbPlaces());
			preparedStatement.execute();

			return 1;
		} catch (SQLException error){
			error.printStackTrace();
			return 0;
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setInt(1, vehicle.getId()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			preparedStatement.execute();


			return 1;
		} catch (SQLException error){
			error.printStackTrace();
			return 0;
		}
	}

	public Vehicle findById(int id) throws DaoException {
		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLE_QUERY);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			ResultSet result = preparedStatement.getResultSet();
			if (result.next()) {
				return new Vehicle(result.getInt("id"), result.getString("constructeur"), result.getString
						("modele"), result.getInt("nb_places"));
			} else {
				System.out.println("Aucun véhicule ne possède cet identifiant !");
				return null;
			}
		} catch (SQLException error) {
			error.printStackTrace();
			return null;
		}
	}

	public List<Vehicle> findAll() throws DaoException {

		try {
			Connection connection = ConnectionManager.getConnection();

			// Utilize a Statement for the select query
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			preparedStatement.execute();

			ArrayList<Vehicle> vehicles = new ArrayList<>();
			ResultSet result = preparedStatement.getResultSet();
			while (result.next()) {
				int id = result.getInt("id");
				String constructeur = result.getString("constructeur");
				String modele = result.getString("modele");
				int nb_places = result.getInt("nb_places");
				// Create a Client object
				Vehicle vehicle = new Vehicle(id, constructeur, modele, nb_places);
				// Add the client to the list
				vehicles.add(vehicle);
			}
			return vehicles;
		} catch (SQLException error) {
			error.printStackTrace();
			return null;
		}

		
	}

	public int count() throws DaoException{
        int count = 0;
		try {
			Connection connection = ConnectionManager.getConnection();

			// Utilize a Statement for the select query
			PreparedStatement preparedStatement = connection.prepareStatement(COUNT_VEHICLES_QUERY);
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
