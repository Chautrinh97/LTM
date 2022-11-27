package com.ctv.model.dao;

import java.sql.ResultSet;
import java.util.List;

interface GenericDAO<T> {

	/**
	 * Return null if no entity with the id was found
	 *
	 * @param id id of the entity
	 * @return entity
	 */
	T get(int id);

	/**
	 * @return list of entities, emmpty list if no was found
	 */
	List<T> getAll();

	/**
	 * @param t the entity to be persisted
	 * @return the persisted entity
	 */


	/**
	 * Map a result set row to an entity
	 */
	T map(ResultSet resultSet);
}
