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

@Api(name = "comercioendpoint", namespace = @ApiNamespace(ownerDomain = "fortmin.com", ownerName = "fortmin.com", packagePath = "proshopping"))
public class ComercioEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listComercio")
	public CollectionResponse<Comercio> listComercio(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<Comercio> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from Comercio as Comercio");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<Comercio>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Comercio obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Comercio> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getComercio")
	public Comercio getComercio(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		Comercio comercio = null;
		try {
			comercio = mgr.find(Comercio.class, id);
		} finally {
			mgr.close();
		}
		return comercio;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param comercio the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertComercio")
	public Comercio insertComercio(Comercio comercio) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsComercio(comercio)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(comercio);
		} finally {
			mgr.close();
		}
		return comercio;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param comercio the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateComercio")
	public Comercio updateComercio(Comercio comercio) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsComercio(comercio)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(comercio);
		} finally {
			mgr.close();
		}
		return comercio;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeComercio")
	public void removeComercio(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			Comercio comercio = mgr.find(Comercio.class, id);
			mgr.remove(comercio);
		} finally {
			mgr.close();
		}
	}

	private boolean containsComercio(Comercio comercio) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			Comercio item = mgr.find(Comercio.class, comercio.getNombre());
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
