package com.epf.rentmanager.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.h2.tools.DeleteDbFiles;

import com.epf.rentmanager.persistence.ConnectionManager;

public class FillDatabase {


    public static void main(String[] args) throws Exception {
        try {
            DeleteDbFiles.execute("~", "RentManagerDatabase", true);
            insertWithPreparedStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	private static void insertWithPreparedStatement() throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement createPreparedStatement = null;



        List<String> createTablesQueries = new ArrayList<>();
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Client(id INT primary key auto_increment, client_id INT, nom VARCHAR(100), prenom VARCHAR(100), email VARCHAR(100), naissance DATETIME)");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Vehicle(id INT primary key auto_increment, constructeur VARCHAR(100), modele VARCHAR(100), nb_places TINYINT(255))");
        createTablesQueries.add("CREATE TABLE IF NOT EXISTS Reservation(id INT primary key auto_increment, client_id INT, foreign key(client_id) REFERENCES Client(id), vehicle_id INT, foreign key(vehicle_id) REFERENCES Vehicle(id), debut DATETIME, fin DATETIME)");

        try {
            connection.setAutoCommit(false);

            for (String createQuery : createTablesQueries) {
            	createPreparedStatement = connection.prepareStatement(createQuery);
	            createPreparedStatement.executeUpdate();
	            createPreparedStatement.close();
            }


            // Remplissage de la base avec des Vehicules et des Clients
            Statement stmt = connection.createStatement();
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES('Renault', 'Espace',6)");
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES('Peugeot', '3008',4)");
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES('Seat', 'ibiza',5)");
            stmt.execute("INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES('RangeRover', 'Velar', 1)");
            
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Lilamand', 'Nathan', 'nathan.test1@gmail.com', '2001-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Morin', 'anais', 'sabrina.morin@email.com', '2002-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Afleck', 'titouan', 'steeve.afleck@email.com', '2003-01-22')");
            stmt.execute("INSERT INTO Client(nom, prenom, email, naissance) VALUES('Rousseau', 'julie', 'jacques.rousseau@email.com', '2004-01-22')");
                    
            connection.commit();
            System.out.println("Success!");
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }
}
