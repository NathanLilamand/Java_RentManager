package com.epf.rentmanager.ui.servlet;

import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/users")
public class ClientListServlet extends HttpServlet{

    @Autowired
    ClientService clientService;
    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            // Récupérer la liste des véhicules depuis le service
            List<Client> clients = clientService.findAll();

            // Définir l'attribut "vehicles" dans la requête pour passer la liste des véhicules à la JSP
            request.setAttribute("clients", clients);

            // Rediriger vers la JSP pour afficher les données
            request.getRequestDispatcher("/WEB-INF/views/users/list.jsp").forward(request, response);
        } catch (Exception e) {
            // Gérer les erreurs
            e.printStackTrace(); // Vous pouvez remplacer ceci par un message d'erreur plus approprié
        }
    }
}
