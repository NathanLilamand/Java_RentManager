package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private ReservationDao reservationDao;


    private ReservationService(ReservationDao reservationDao){
        this.reservationDao = reservationDao;
    }




    public long create(Reservation reservation) throws ServiceException {

        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long delete(Reservation reservation) throws ServiceException {
         try {
            return ReservationDao.delete(reservation);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }

    }

    public Reservation findById(int id) throws ServiceException {

        try {
         return ReservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Reservation> findByClientId(int clientid) throws ServiceException {

        try {
         return reservationDao.findResByClientId(clientid);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public List<Reservation> findByVehicleId(int vehicle_id) throws ServiceException {

        try {
           return reservationDao.findResByVehicleId(vehicle_id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public List<Reservation> findAll() throws ServiceException {

        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    public int count() throws ServiceException {
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
        }
    }
}
