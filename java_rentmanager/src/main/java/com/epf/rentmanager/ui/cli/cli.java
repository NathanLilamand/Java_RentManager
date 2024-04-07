package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class cli {
    public static void main(String[] args) throws ServiceException {
        while (true) {
            ApplicationContext context = new
                    AnnotationConfigApplicationContext(AppConfiguration.class);
            ClientService clientService = context.getBean(ClientService.class);
            VehicleService vehicleService = context.getBean(VehicleService.class);
            ReservationService reservationService = context.getBean(ReservationService.class);
            System.out.println("Bienvenue, ici la voix !\nVous pouvez créer ('C'), supprimer ('S'), afficher ('A') ou arrêter l'interface ('STOP') !");
            Scanner scanner = new Scanner(System.in);
            String entered = scanner.nextLine();
            while (!entered.equalsIgnoreCase("C") && !entered.equalsIgnoreCase("c") &&
                    !entered.equalsIgnoreCase("S") && !entered.equalsIgnoreCase("s") &&
                    !entered.equalsIgnoreCase("A") && !entered.equalsIgnoreCase("a")&&
                    !entered.equalsIgnoreCase("STOP") && !entered.equalsIgnoreCase("stop")) {
                System.out.println("Les différentes valeurs possible sont 'C', 'S', 'A' et 'STOP' !");
                entered = scanner.nextLine();
            }
            if (entered.equalsIgnoreCase("C") || entered.equalsIgnoreCase("c")) {
                System.out.println("Voulez-vous créer un client ('C'), un véhicule ('V') ou bien une réservation ('R') ?");
                scanner = new Scanner(System.in);
                String type = scanner.nextLine();
                while (!type.equalsIgnoreCase("C") && !type.equalsIgnoreCase("c") &&
                        !type.equalsIgnoreCase("V") && !type.equalsIgnoreCase("v") &&
                        !type.equalsIgnoreCase("R") && !type.equalsIgnoreCase("r")) {
                    System.out.println("Les différentes valeurs possibles sont 'C', 'V' et 'R' !");
                    type = scanner.nextLine();
                }
                if (type.equalsIgnoreCase("C") || type.equalsIgnoreCase("c")) {
                    System.out.println("Veuillez renseigner un nom :");
                    scanner = new Scanner(System.in);
                    String nom = scanner.nextLine();
                    while (nom.isEmpty() || !nom.matches("\\p{L}+")) {
                        if (nom.isEmpty()) {
                            System.out.println("Un nom ne peut pas être vide, veuillez saisir un nom valide !");
                            nom = scanner.nextLine();
                        } else if (!nom.matches("\\p{L}+")) {
                            System.out.println("Un nom ne peut être composé que de lettres !");
                            nom = scanner.nextLine();
                        }
                    }

                    System.out.println("Veuillez renseigner un prénom :");
                    String prenom = scanner.nextLine();
                    while (prenom.isEmpty() || !prenom.matches("\\p{L}+")) {
                        if (prenom.isEmpty()) {
                            System.out.println("Un prénom ne peut pas être vide, veuillez saisir un prénom valide !");
                            prenom = scanner.nextLine();
                        } else if (!prenom.matches("\\p{L}+")) {
                            System.out.println("Un prénom ne peut être composé que de lettres !");
                            prenom = scanner.nextLine();
                        }
                    }

                    System.out.println("Veuillez entrer votre adresse mail :");
                    String mail = scanner.nextLine();
                    String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
                    Pattern pattern = Pattern.compile(regex);
                    while (!pattern.matcher(mail).matches()) {
                        System.out.println("Cela n'est pas une adresse mail valide !");
                        mail = scanner.nextLine();
                    }

                    System.out.println("Veuillez entrer votre date de naissance :");
                    String naissance1 = scanner.nextLine();
                    LocalDate naissance = null;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    try {
                        naissance = LocalDate.parse(naissance1, formatter);
                        System.out.println("Date saisie : " + naissance);
                    } catch (Exception e) {
                        System.out.println("Format de date incorrect. Veuillez utiliser le format dd/MM/yyyy.");
                    }
                    try {
                        int taille = 0;
                        List<Client> bdd = clientService.findAll();
                        if (bdd != null) {
                            taille = bdd.size() + 1;
                        }
                        int id = taille;
                        long result = clientService.create(new Client(id, nom, prenom, mail, naissance));
                        if (result==1) {
                            System.out.println("Le client "+prenom+" "+nom+" a bien été créé !");
                        }
                    } catch (ServiceException e) {
                        throw new ServiceException(e);
                    }
                } else if (type.equalsIgnoreCase("V") || type.equalsIgnoreCase("v")) {
                    System.out.println("Veuillez renseigner un constructeur :");
                    scanner = new Scanner(System.in);
                    String constructeur = scanner.nextLine();
                    while (constructeur.isEmpty() || !constructeur.matches("\\p{L}+")) {
                        if (constructeur.isEmpty()) {
                            System.out.println("Un constructeur automobile ne peut pas être vide, veuillez saisir un " +
                                    "constructeur valide !");
                            constructeur = scanner.nextLine();
                        } else if (!constructeur.matches("\\p{L}+")) {
                            System.out.println("Un constructeur ne peut être composé que de lettres !");
                            constructeur = scanner.nextLine();
                        }
                    }

                    System.out.println("Veuillez renseigner un modèle :");
                    String modele = scanner.nextLine();
                    while (modele.isEmpty()) {
                        System.out.println("Un modèle de voiture ne peut pas être vide, veuillez saisir un modèle valide !");
                        modele = scanner.nextLine();
                    }

                    System.out.println("Veuillez entrer le nombre de places du véhicule :");
                    String nb_placesInt = scanner.nextLine();
                    while (!nb_placesInt.matches("[0-9]+")) {
                        System.out.println("Cela n'est pas un nombre de places valide !");
                        nb_placesInt = scanner.nextLine();
                    }
                    int nb_places = Integer.parseInt(nb_placesInt);

                    try {
                        int taille = 0;
                        List<Vehicle> bdd = vehicleService.findAll();
                        if (bdd != null) {
                            taille = bdd.size()+1;
                        }
                        int id = taille;
                        long result = vehicleService.create(new Vehicle(id, constructeur, modele, nb_places));
                        if (result==1) {
                            System.out.println("Le véhicule "+constructeur+" "+modele+" a bien été créé !");
                        }
                    } catch (ServiceException e) {
                        throw new ServiceException(e);
                    }
                } else if (type.equalsIgnoreCase("R") || type.equalsIgnoreCase("r")) {
                    System.out.println("Veuillez renseigner un identifiant client :");
                    scanner = new Scanner(System.in);
                    String idClient = scanner.nextLine();
                    while (idClient.isEmpty() || !idClient.matches("[0-9]+")) {
                        if (idClient.isEmpty()) {
                            System.out.println("Un identifiant ne peut pas être vide, veuillez saisir un identifiant valide !");
                            idClient = scanner.nextLine();
                        } else if (!idClient.matches("[0-9]+")) {
                            System.out.println("Un identifiant ne peut être composé que de chiffres !");
                            idClient = scanner.nextLine();
                        }
                    }
                    int idC = Integer.parseInt(idClient);

                    System.out.println("Veuillez renseigner un identifiant véhicule :");
                    scanner = new Scanner(System.in);
                    String idVehicule = scanner.nextLine();
                    while (idVehicule.isEmpty() || !idVehicule.matches("[0-9]+")) {
                        if (idVehicule.isEmpty()) {
                            System.out.println("Un identifiant ne peut pas être vide, veuillez saisir un identifiant valide !");
                            idVehicule = scanner.nextLine();
                        } else if (!idVehicule.matches("[0-9]+")) {
                            System.out.println("Un identifiant ne peut être composé que de chiffres !");
                            idVehicule = scanner.nextLine();
                        }
                    }
                    int idV = Integer.parseInt(idVehicule);

                    System.out.println("Veuillez entrer la date de début :");
                    String debut = scanner.nextLine();
                    String regex = "\\d{2}/\\d{2}/\\d{4}";
                    Pattern pattern = Pattern.compile(regex);
                    while (!pattern.matcher(debut).matches()) {
                        System.out.println("Cela n'est pas une date valide, veuillez saisir une date de type dd/MM/yyyy !");
                        debut = scanner.nextLine();
                    }
                    LocalDate Debut = null;
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    try {
                        Debut = LocalDate.parse(debut, formatter);
                        System.out.println("Date saisie : " + debut);
                    } catch (Exception e) {
                        System.out.println("Format de date incorrect. Veuillez utiliser le format dd/MM/yyyy.");
                    }

                    System.out.println("Veuillez entrer la date de fin :");
                    String fin = scanner.nextLine();
                    String regex2 = "\\d{2}/\\d{2}/\\d{4}";
                    Pattern pattern2 = Pattern.compile(regex2);
                    while (!pattern2.matcher(fin).matches()) {
                        System.out.println("Cela n'est pas une date valide, veuillez saisir une date de type dd/MM/yyyy !");
                        fin = scanner.nextLine();
                    }
                    LocalDate Fin = null;
                    DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    try {
                        Fin = LocalDate.parse(fin, formatter2);
                        System.out.println("Date saisie : " + fin);
                    } catch (Exception e) {
                        System.out.println("Format de date incorrect. Veuillez utiliser le format dd/MM/yyyy.");
                    }
                    try {
                        int taille = 0;
                        List<Reservation> bdd = reservationService.findAll();
                        if (bdd != null) {
                            taille = bdd.size() + 1;
                        }
                        int id = taille;
                        long result = reservationService.create(new Reservation(id, idC, idV, Debut, Fin));
                        if (result==1) {
                            System.out.println("La réservation du "+debut+" au "+fin+" a bien été créé !");
                        }
                    } catch (ServiceException e) {
                        throw new ServiceException(e);
                    }
                }
            } else if (entered.equalsIgnoreCase("S") || entered.equalsIgnoreCase("s")) {
                System.out.println("Voulez-vous supprimer un client ('C'), un véhicule ('V') ou bien une réservation ('R') ?");
                scanner = new Scanner(System.in);
                String type = scanner.nextLine();
                while (!type.equalsIgnoreCase("C") && !type.equalsIgnoreCase("c") &&
                        !type.equalsIgnoreCase("V") && !type.equalsIgnoreCase("v") &&
                        !type.equalsIgnoreCase("R") && !type.equalsIgnoreCase("r")){
                    System.out.println("Les différentes valeurs possibles sont 'C', 'V' et 'R' !");
                    type = scanner.nextLine();
                }
                if (type.equalsIgnoreCase("C") || type.equalsIgnoreCase("c")) {
                    System.out.println("Pour supprimer un client, veuillez saisir son identifiant :");
                    scanner = new Scanner(System.in);
                    String identifiant = scanner.nextLine();

                    while (!identifiant.matches("[0-9]+")) {
                        System.out.println("Cela n'est pas un identifiant valide !");
                        identifiant = scanner.nextLine();
                    }
                    int id = Integer.parseInt(identifiant);
                    Client monClient = clientService.findById(id);
                    long result = 0;
                    try {
                        result = clientService.delete(monClient);
                    } catch (ServiceException e) {
                        System.out.println(e);
                    }
                    if (result==1) {
                        System.out.println("Le client "+monClient.getPrenom()+" "+monClient.getNom()+" a bien été supprimé !");
                    }

                } else if (type.equalsIgnoreCase("V") || type.equalsIgnoreCase("v")) {
                    System.out.println("Pour supprimer un véhicule, veuillez saisir son identifiant :");
                    scanner = new Scanner(System.in);
                    String identifiant = scanner.nextLine();

                    while (!identifiant.matches("[0-9]+")) {
                        System.out.println("Cela n'est pas un identifiant valide !");
                        identifiant = scanner.nextLine();
                    }
                    int id = Integer.parseInt(identifiant);
                    Vehicle monVehicule = vehicleService.findById(id);
                    long result = 0;
                    try {
                        result = vehicleService.delete(monVehicule);
                    } catch (ServiceException e) {
                        System.out.println(e);
                    }
                    if (result==1) {
                        System.out.println("Le véhicule "+monVehicule.getConstructeur()+" "+monVehicule.getModele()+" a bien été supprimé !");
                    }
                } else if (type.equalsIgnoreCase("R") || type.equalsIgnoreCase("r")) {
                    System.out.println("Pour supprimer une réservation, veuillez saisir son identifiant :");
                    scanner = new Scanner(System.in);
                    String identifiant = scanner.nextLine();

                    while (!identifiant.matches("[0-9]+")) {
                        System.out.println("Cela n'est pas un identifiant valide !");
                        identifiant = scanner.nextLine();
                    }
                    int id = Integer.parseInt(identifiant);
                    Reservation maResa = reservationService.findById(id);
                    long result = 0;
                    try {
                        result = reservationService.delete(maResa);
                    } catch (ServiceException e) {
                        System.out.println(e);
                    }
                    if (result==1) {
                        System.out.println("La réservation du "+maResa.getDebut()+" au "+maResa.getFin()+" a bien été supprimée !");
                    }
                }
            } else if (entered.equalsIgnoreCase("A") || entered.equalsIgnoreCase("a")) {
                System.out.println("Voulez-vous afficher les clients ('C'), les véhicules ('V') ou bien les réservations ('R') ?");
                scanner = new Scanner(System.in);
                String type = scanner.nextLine();
                while (!type.equalsIgnoreCase("C") && !type.equalsIgnoreCase("c") &&
                        !type.equalsIgnoreCase("V") && !type.equalsIgnoreCase("v") &&
                        !type.equalsIgnoreCase("R") && !type.equalsIgnoreCase("r")) {
                    System.out.println("Les différentes valeurs possibles sont 'C', 'V' et 'R' !");
                    type = scanner.nextLine();
                }
                if (type.equalsIgnoreCase("C") || type.equalsIgnoreCase("c")) {
                    try {
                        List<Client> lesClients = clientService.findAll();
                        System.out.println(lesClients);
                    } catch (ServiceException e) {
                        System.out.println(e);
                    }
                } else if (type.equalsIgnoreCase("V") || type.equalsIgnoreCase("v")) {
                    try {
                        List<Vehicle> lesVehicules = vehicleService.findAll();
                        System.out.println(lesVehicules);
                    } catch (ServiceException e) {
                        System.out.println(e);
                    }
                } else if (type.equalsIgnoreCase("R") || type.equalsIgnoreCase("r")) {
                    System.out.println("Voulez-vous afficher les toutes les réservations ('T'), les réservations d'un" +
                            " client ('C') ou bien les réservations d'un véhicule ('V') ?");
                    scanner = new Scanner(System.in);
                    String typeResa = scanner.nextLine();
                    while (!typeResa.equalsIgnoreCase("T") && !typeResa.equalsIgnoreCase("t") &&
                            !typeResa.equalsIgnoreCase("C") && !typeResa.equalsIgnoreCase("c") &&
                            !typeResa.equalsIgnoreCase("V") && !typeResa.equalsIgnoreCase("v")) {
                        System.out.println("Les différentes valeurs possibles sont 'T', 'C' et 'V' !");
                        typeResa = scanner.nextLine();
                    }
                    if (typeResa.equalsIgnoreCase("T") || typeResa.equalsIgnoreCase("t")) {
                        try {
                            List<Reservation> lesResas = reservationService.findAll();
                            System.out.println(lesResas);
                        } catch (ServiceException e) {
                            System.out.println(e);
                        }
                    } else if (typeResa.equalsIgnoreCase("C") || typeResa.equalsIgnoreCase("c")) {
                        System.out.println("Pour afficher les réservations d'un client, veuillez saisir son identifiant :");
                        scanner = new Scanner(System.in);
                        String identifiant = scanner.nextLine();

                        while (!identifiant.matches("[0-9]+")) {
                            System.out.println("Cela n'est pas un identifiant valide !");
                            identifiant = scanner.nextLine();
                        }
                        int id = Integer.parseInt(identifiant);
                        List<Reservation> resas;
                        try {
                            resas = reservationService.findByClientId(id);
                            System.out.println(resas);
                        } catch (ServiceException e) {
                            System.out.println(e);
                        }
                    } else if (typeResa.equalsIgnoreCase("V") || typeResa.equalsIgnoreCase("v")) {
                        System.out.println("Pour afficher les réservations d'un véhicule, veuillez saisir son identifiant :");
                        scanner = new Scanner(System.in);
                        String identifiant = scanner.nextLine();

                        while (!identifiant.matches("[0-9]+")) {
                            System.out.println("Cela n'est pas un identifiant valide !");
                            identifiant = scanner.nextLine();
                        }
                        int id = Integer.parseInt(identifiant);
                        List<Reservation> resas;
                        try {
                            resas = reservationService.findByVehicleId(id);
                            System.out.println(resas);
                        } catch (ServiceException e) {
                            System.out.println(e);
                        }
                    }
                }
            } else if (entered.equalsIgnoreCase("STOP") || entered.equalsIgnoreCase("stop")) {
                break;
            }
        }
    }
}