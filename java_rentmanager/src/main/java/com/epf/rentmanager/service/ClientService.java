package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ClientDao;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;


	private ClientService(ClientDao clientDao){
		this.clientDao = clientDao;
	}



	
	
	public long create(Client client) throws ServiceException {
		// TODO: créer un client
		try {
			return clientDao.create(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}
    }

	public Client findById(int id) throws ServiceException {
		// TODO: récupérer un client par son id
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}
	}

	public long delete(Client client) throws ServiceException {
			try {
				return clientDao.delete(client);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
	}

	public List<Client> findAll() throws ServiceException {
		// TODO: récupérer tous les clients
		try {
			return clientDao.findAll();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}

	}

	public int count() throws ServiceException {
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la création ou de la mise à jour du client.", e);
		}
	}
	
}
