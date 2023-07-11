package service;

import java.util.List;

import javax.persistence.Persistence;

import dao.PlaceDao;
import model.Place;

public class PlaceService {
	private PlaceDao placeDao;

	public PlaceService() {
		try {
			placeDao = new PlaceDao(Persistence.createEntityManagerFactory("Football"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public List<Place> getAllPlaces() {
		return placeDao.findAll();
	}

	/// for login
	public Place findPlace(Integer id) throws Exception {
		List<Place> place = placeDao.find(id);
		if (place.size() == 0) {
			throw new Exception("Location not found!");
		}
		Place u = place.get(0);
		return u;
	}

	public Place findDate(String date) throws Exception {
		List<Place> place = placeDao.findByDate(date);
		if (place.size() == 0) {
			throw new Exception("Location not found!");
		}
		Place u = place.get(0);
		return u;
	}
}
