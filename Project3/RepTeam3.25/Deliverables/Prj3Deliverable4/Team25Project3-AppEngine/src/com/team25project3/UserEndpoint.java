package com.team25project3;

import com.team25project3.EMF;

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

@Api(name = "userendpoint", namespace = @ApiNamespace(ownerDomain = "team25project3.com", ownerName = "team25project3.com", packagePath = ""))
public class UserEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listRegUserInfo", path="userinfo1")
	public CollectionResponse<User> listRegUserInfo(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<User> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select from User as User");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<User>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (User obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<User> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	
	
	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listRegUserInfoSync", path="userinfo2")
	public CollectionResponse<User> listRegUserInfoSync(
			@Named("synctime") String synctime,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<User> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr
					.createQuery("select o from User o where o.lastupdated > :synctime");
			query.setParameter("synctime", synctime);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<User>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (User obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<User> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}
	


	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getRegUserInfo")
	public User getRegUserInfo(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		User reguserinfo = null;
		try {
			reguserinfo = mgr.find(User.class, id);
		} finally {
			mgr.close();
		}
		return reguserinfo;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param reguserinfo the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertRegUserInfo")
	public User insertRegUserInfo(User reguserinfo) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsRegUserInfo(reguserinfo)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(reguserinfo);
		} finally {
			mgr.close();
		}
		return reguserinfo;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param reguserinfo the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateRegUserInfo")
	public User updateRegUserInfo(User reguserinfo) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsRegUserInfo(reguserinfo)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(reguserinfo);
		} finally {
			mgr.close();
		}
		return reguserinfo;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeRegUserInfo")
	public void removeRegUserInfo(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			User reguserinfo = mgr.find(User.class, id);
			mgr.remove(reguserinfo);
		} finally {
			mgr.close();
		}
	}

	private boolean containsRegUserInfo(User reguserinfo) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			User item = mgr.find(User.class, reguserinfo.getId());
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
