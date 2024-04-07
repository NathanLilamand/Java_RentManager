package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;

import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	

	private ClientDao() {}

	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) AS count FROM CLIENT;";
	
	public long create(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY);
			preparedStatement.setString(1, client.getNom()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			preparedStatement.setString(2, client.getPrenom()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			preparedStatement.setString(3, client.getEmail()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			Date date = Date.valueOf(client.getNaissance());
			preparedStatement.setDate(4, date);

			preparedStatement.execute();


			return 1;
		} catch (SQLException error){
			error.printStackTrace();
			return 0;
		}
	}



	public long delete(Client client) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setInt(1, client.getId()); // ATTENTION /!\ : l’indice commence par 1, contrairement aux tableaux
			preparedStatement.execute();


			return 1;
		} catch (SQLException error){
			error.printStackTrace();
			return 0;
		}
	}

	public Client findById(int id) throws DaoException {

		try {
			Connection connexion = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connexion.prepareStatement(FIND_CLIENT_QUERY);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
			ResultSet result = preparedStatement.getResultSet();
			if (result.next()) {
				return new Client(result.getInt("id"), result.getString("nom"), result.getString("prenom"), result.getString("email"), result.getDate("naissance").toLocalDate());
			} else {
				System.out.println("Aucun client ne possède cet identifiant !");
				return null;
			}
		} catch (SQLException error) {
			error.printStackTrace();
			return null;
		}

	}

	public List<Client> findAll() throws DaoException {


		try {
			Connection connection = ConnectionManager.getConnection();

			// Utilize a Statement for the select query
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY) ;
			preparedStatement.execute();

			ArrayList<Client> clients = new ArrayList<>();
			ResultSet resultSet = preparedStatement.getResultSet();

			// Process the result set and create Client objects
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				String prenom = resultSet.getString("prenom");
				String email = resultSet.getString("email");
				Date naissance = resultSet.getDate("naissance");

				// Create a Client object
				Client client = new Client(id, nom, prenom, email, naissance.toLocalDate());
				// Add the client to the list
				clients.add(client);
			}

			return clients;
		} catch (SQLException error) {
			// Log the error
			error.printStackTrace(); // You should use a logger here
			return null;
		}


	}
	public int count() throws DaoException{
		int count = 0;
		try {
			Connection connection = ConnectionManager.getConnection();

			// Utilize a Statement for the select query
			PreparedStatement preparedStatement = connection.prepareStatement(COUNT_CLIENTS_QUERY);
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
