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

@WebServlet("/del")
public class VehicleDeleteServlet extends HttpServlet{

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
        // traitement du formulaire (appel à la méthode de sauvegarde)
        try {


            Vehicle vehicle = vehicleService.findById(Integer.parseInt(request.getParameter("id")));


            vehicleService.delete(vehicle);

            // Succès de l'insertion
            response.sendRedirect(request.getContextPath()+"/cars");

        } catch (Exception e) {
            // Gestion des exceptions
            e.printStackTrace(); // À adapter selon votre gestion d'erreurs
            response.sendRedirect(request.getContextPath()+"/cars");
        }
    }


}