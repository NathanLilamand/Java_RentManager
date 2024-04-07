package com.epf.rentmanager.ui.servlet;


import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/rents/create")
public class ReservationCreateServlet extends HttpServlet{

    @Autowired
    ClientService clientService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    ReservationService reservationService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        // affichage du formulaire
        try {

            // Récupérer la liste des clients
            List<Client> clients = clientService.findAll();
            // Récupérer la liste des véhicules
            List<Vehicle> vehicles = vehicleService.findAll();

            // Placer les listes dans les attributs de la requête
            request.setAttribute("clients", clients);
            request.setAttribute("vehicles", vehicles);
            // Rediriger vers la JSP pour afficher les données
            request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
        } catch (Exception e) {
            // Gérer les erreurs
            e.printStackTrace(); // Vous pouvez remplacer ceci par un message d'erreur plus approprié
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
        // traitement du formulaire (appel à la méthode de sauvegarde)
        try {


            int client_id = Integer.parseInt(request.getParameter("client"));
            int vehicle_id = Integer.parseInt(request.getParameter("car"));
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate debut = LocalDate.parse(request.getParameter("begin"), dateFormatter);
            LocalDate fin = LocalDate.parse(request.getParameter("end"), dateFormatter);

            // Création de l'objet Vehicle
            int countReservation = reservationService.count();

            Reservation reservation = new Reservation(countReservation, client_id, vehicle_id, debut, fin);

            reservationService.create(reservation);

            // Succès de l'insertion
            response.sendRedirect(request.getContextPath()+"/rents");

        } catch (Exception e) {
            // Gestion des exceptions
            e.printStackTrace(); // À adapter selon votre gestion d'erreurs
            response.sendRedirect(request.getContextPath()+"/rents");
        }
    }


}