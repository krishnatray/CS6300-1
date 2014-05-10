package com.team25project3;

import com.team25project3.EMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Api(name = "taskinfoendpoint", namespace = @ApiNamespace(ownerDomain = "team25project3.com", ownerName = "team25project3.com", packagePath = ""))
public class TaskInfoEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listTaskInfo", path="taskinfo1")
	public CollectionResponse<TaskInfo> listTaskInfo(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<TaskInfo> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select from TaskInfo as TaskInfo");
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<TaskInfo>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (TaskInfo obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<TaskInfo> builder().setItems(execute)
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
	@ApiMethod(name = "listTaskInfoSync", path="taskinfo2")
	public CollectionResponse<TaskInfo> listTaskInfoSync(
			@Named("synctime") String synctime,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<TaskInfo> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select o from TaskInfo o where o.lastupdated > :synctime");
			query.setParameter("synctime", synctime);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<TaskInfo>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (TaskInfo obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<TaskInfo> builder().setItems(execute)
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
	@ApiMethod(name = "listTaskInfoSyncUser", path="taskinfo3")
	public CollectionResponse<TaskInfo> listTaskInfoSyncUser(
			@Named("synctime") String synctime,
			@Named("uID") String uID,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		EntityManager mgr = null;
		Cursor cursor = null;
		List<TaskInfo> execute = null;

		try {
			mgr = getEntityManager();
			Query query = mgr.createQuery("select o from TaskInfo o where o.userid = :uid");
			//query.setParameter("synctime", synctime);
			query.setParameter("uid", uID);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
			}

			if (limit != null) {
				query.setFirstResult(0);
				query.setMaxResults(limit);
			}

			execute = (List<TaskInfo>) query.getResultList();
			cursor = JPACursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			/*try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			for (TaskInfo obj : execute)
			{
				mgr.refresh(obj);
			}
		} finally {
			mgr.close();
		}

		return CollectionResponse.<TaskInfo> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getTaskInfo")
	public TaskInfo getTaskInfo(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		TaskInfo taskinfo = null;
		try {
			taskinfo = mgr.find(TaskInfo.class, id);
		} finally {
			mgr.close();
		}
		return taskinfo;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param taskinfo the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertTaskInfo")
	public TaskInfo insertTaskInfo(TaskInfo taskinfo) {
		EntityManager mgr = getEntityManager();
		try {
			if (containsTaskInfo(taskinfo)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.persist(taskinfo);
		} finally {
			mgr.close();
		}
		return taskinfo;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param taskinfo the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateTaskInfo")
	public TaskInfo updateTaskInfo(TaskInfo taskinfo) {
		EntityManager mgr = getEntityManager();
		try {
			if (!containsTaskInfo(taskinfo)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.persist(taskinfo);
		} finally {
			mgr.close();
		}
		return taskinfo;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeTaskInfo")
	public void removeTaskInfo(@Named("id") String id) {
		EntityManager mgr = getEntityManager();
		try {
			TaskInfo taskinfo = mgr.find(TaskInfo.class, id);
			mgr.remove(taskinfo);
		} finally {
			mgr.close();
		}
	}

	private boolean containsTaskInfo(TaskInfo taskinfo) {
		EntityManager mgr = getEntityManager();
		boolean contains = true;
		try {
			TaskInfo item = mgr.find(TaskInfo.class, taskinfo.getId());
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
