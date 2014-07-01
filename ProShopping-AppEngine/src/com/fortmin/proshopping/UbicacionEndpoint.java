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

@Api(name = "ubicacionendpoint", namespace = @ApiNamespace(ownerDomain = "fortmin.com", ownerName = "fortmin.com", packagePath = "proshopping"))
public class UbicacionEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listUbicacion")
	public CollectionResponse<Ubicacion> listUbicacion(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Ubicacion> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Ubicacion as Ubicacion");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Ubicacion>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Ubicacion obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Ubicacion> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getUbicacion")
	public Ubicacion getUbicacion(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		Ubicacion ubicacion = null;
		try {
			ubicacion = mgr.find(Ubicacion.class, id);
		} finally {
			mgr.close();
		}
		return ubicacion;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param ubicacion the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertUbicacion")
	public Ubicacion insertUbicacion(Ubicacion ubicacion) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsUbicacion(ubicacion)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(ubicacion);
		} finally {
			mgr.close();
		}
		return ubicacion;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param ubicacion the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateUbicacion")
	public Ubicacion updateUbicacion(Ubicacion ubicacion) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsUbicacion(ubicacion)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(ubicacion);
		} finally {
			mgr.close();
		}
		return ubicacion;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeUbicacion")
	public void removeUbicacion(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			Ubicacion ubicacion = mgr.find(Ubicacion.class, id);
			mgr.remove(ubicacion);
		} finally {
			mgr.close();
		}
	}

	private boolean containsUbicacion(Ubicacion ubicacion) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Ubicacion item = mgr.find(Ubicacion.class, ubicacion.getNombre());
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
