package com.epf.rentmanager.ui.servlet;


import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cars/create")
public class VehicleCreateServlet extends HttpServlet{

    @Autowired
    VehicleService vehicleService;
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
            // Rediriger vers la JSP pour afficher les données
            request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
        } catch (Exception e) {
            // Gérer les erreurs
            e.printStackTrace(); // Vous pouvez remplacer ceci par un message d'erreur plus approprié
        }

    }
    protected void doPost(HttpServletRequest request, HttpServletResponse
            response) throws ServletException, IOException {
            // traitement du formulaire (appel à la méthode de sauvegarde)
        try {

            String constructeur = request.getParameter("manufacturer");
            String modele = request.getParameter("modele");
            int nb_places = Integer.parseInt(request.getParameter("seats"));
            // Création de l'objet Vehicle
            int countvoiture = vehicleService.count();

            Vehicle vehicle = new Vehicle(countvoiture,constructeur, modele, nb_places);

            vehicleService.create(vehicle);

            // Succès de l'insertion
            response.sendRedirect(request.getContextPath()+"/cars");

        } catch (Exception e) {
            // Gestion des exceptions
            e.printStackTrace(); // À adapter selon votre gestion d'erreurs
            response.sendRedirect(request.getContextPath()+"/cars");
        }
    }


}
