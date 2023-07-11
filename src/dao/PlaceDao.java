package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import model.Place;

public class PlaceDao extends GenericDao<Place> {
	private EntityManagerFactory factory;

	public PlaceDao(EntityManagerFactory factory) {
		super(Place.class);
		this.factory = factory;
	}

	@Override
	public EntityManager getEntityManager() {
		try {
			return factory.createEntityManager();
		} catch (Exception ex) {
			System.out.println("The entity manager cannot be created!");
			return null;
		}
	}

	public List<Place> find(Integer id) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Place> q = cb.createQuery(Place.class);

		Root<Place> c = q.from(Place.class);
		ParameterExpression<Integer> paramName = cb.parameter(Integer.class);
		q.select(c).where(cb.equal(c.get("placesId"), paramName));
		TypedQuery<Place> query = em.createQuery(q);
		query.setParameter(paramName, id);

		List<Place> results = query.getResultList();
		return results;
	}

	public List<Place> findByDate(String date) {
		EntityManager em = getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Place> q = cb.createQuery(Place.class);

		Root<Place> c = q.from(Place.class);
		ParameterExpression<String> paramName = cb.parameter(String.class);
		q.select(c).where(cb.equal(c.get("date"), paramName));
		TypedQuery<Place> query = em.createQuery(q);
		query.setParameter(paramName, date);

		List<Place> results = query.getResultList();
		return results;
	}
}
