package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the places database table.
 * 
 */
@Entity
@Table(name = "places")
@NamedQuery(name = "Place.findAll", query = "SELECT p FROM Place p")
public class Place implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int placesId;

	private int availablePlaces;

	private String date;

	// bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name = "locationId")
	private Location location;

	public Place() {
	}

	public int getPlacesId() {
		return this.placesId;
	}

	public void setPlacesId(int placesId) {
		this.placesId = placesId;
	}

	public String getAvailablePlaces() {
		return this.availablePlaces + " places";
	}

	public void setAvailablePlaces(int availablePlaces) {
		this.availablePlaces = availablePlaces;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Place [placesId=" + placesId + ", availablePlaces=" + availablePlaces + ", date=" + date + ", location="
				+ location + "]";
	}

}