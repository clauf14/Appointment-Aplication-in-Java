package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the location database table.
 * 
 */
@Entity
@NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int locationId;

	private byte active;

	private String adress;

	private String description;

	private String name;

	private int price;

	// bi-directional many-to-one association to Place
	@OneToMany(mappedBy = "location")
	private List<Place> places;

	public Location() {
	}

	public int getLocationId() {
		return this.locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public byte getActive() {
		return this.active;
	}

	public void setActive(byte active) {
		this.active = active;
	}

	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return this.price + " ron/hour";
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<Place> getPlaces() {
		return this.places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}

	public Place addPlace(Place place) {
		getPlaces().add(place);
		place.setLocation(this);

		return place;
	}

	public Place removePlace(Place place) {
		getPlaces().remove(place);
		place.setLocation(null);

		return place;
	}

	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", active=" + active + ", adress=" + adress + ", description="
				+ description + ", name=" + name + ", price=" + price + "]";
	}

}