package com.fortmin.proshopping;

import com.fortmin.proshopping.EMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "paqueteendpoint", namespace = @ApiNamespace(ownerDomain = "fortmin.com", ownerName = "fortmin.com", packagePath = "proshopping"))
public class PaqueteEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listPaquete")
	public CollectionResponse<Paquete> listPaquete(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Paquete> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Paquete as Paquete");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Paquete>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Paquete obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Paquete> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getPaquete")
	public Paquete getPaquete(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		Paquete paquete = null;
		try {
			paquete = mgr.find(Paquete.class, id);
		} finally {
			mgr.close();
		}
		return paquete;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param paquete the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertPaquete")
	public Paquete insertPaquete(Paquete paquete) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsPaquete(paquete)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(paquete);
		} finally {
			mgr.close();
		}
		return paquete;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param paquete the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updatePaquete")
	public Paquete updatePaquete(Paquete paquete) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsPaquete(paquete)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(paquete);
		} finally {
			mgr.close();
		}
		return paquete;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removePaquete")
	public void removePaquete(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			Paquete paquete = mgr.find(Paquete.class, id);
			mgr.remove(paquete);
		} finally {
			mgr.close();
		}
	}

	private boolean containsPaquete(Paquete paquete) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Paquete item = mgr.find(Paquete.class, paquete.getClave());
			if (item == null) {
				contains = false;
			}
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static EntityManager getEntityManager() {
		return EMF.get().createEntityManager();
	}

}
