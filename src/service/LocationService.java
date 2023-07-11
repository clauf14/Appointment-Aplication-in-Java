package service;

import java.util.List;

import javax.persistence.Persistence;

import dao.LocationDao;
import model.Location;

public class LocationService {
	private LocationDao locationDao;

	public LocationService() {
		try {
			locationDao = new LocationDao(Persistence.createEntityManagerFactory("Football"));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public void addLocation(Location newLoc) {
		locationDao.create(newLoc);
	}

	public void updateLocation(Location updatedLoc) {
		locationDao.update(updatedLoc);
	}

	/// for login
	public Location findLocation(String name) throws Exception {
		List<Location> loc = locationDao.find(name);
		if (loc.size() == 0) {
			throw new Exception("Location not found!");
		}
		Location u = loc.get(0);
		return u;
	}
}
