package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;

	private VehicleService(VehicleDao vehicleDao){
		this.vehicleDao = vehicleDao;
	}
	

	
	
	public long create(Vehicle vehicle) throws ServiceException {
		// TODO: créer un véhicule
		try {
			return vehicleDao.create(vehicle);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException {

			try {
				return vehicleDao.delete(vehicle);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
	}


	public Vehicle findById(int id) throws ServiceException {
		// TODO: récupérer un véhicule par son id
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return vehicleDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}
	}

	public int count() throws ServiceException {
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}
	}
	
}
