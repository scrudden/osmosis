package com.bretth.osm.conduit.mysql.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.bretth.osm.conduit.ConduitRuntimeException;


/**
 * Provides iterator like behaviour for reading way tags from a database.
 * 
 * @author Brett Henderson
 */
public class WayTagReader extends EntityReader<WayTag> {
	private static final String SELECT_SQL =
		"SELECT id, k, v FROM way_tags ORDER BY id";
	
	
	/**
	 * Creates a new instance.
	 * 
	 * @param host
	 *            The server hosting the database.
	 * @param database
	 *            The database instance.
	 * @param user
	 *            The user name for authentication.
	 * @param password
	 *            The password for authentication.
	 */
	public WayTagReader(String host, String database, String user, String password) {
		super(host, database, user, password);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected WayTag createNextValue(ResultSet resultSet) {
		long wayId;
		String key;
		String value;
		
		try {
			wayId = resultSet.getLong("id");
			key = resultSet.getString("k");
			value = resultSet.getString("v");
			
		} catch (SQLException e) {
			throw new ConduitRuntimeException("Unable to read way tag fields.", e);
		}
		
		return new WayTag(wayId, key, value);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected String getQuerySql() {
		return SELECT_SQL;
	} 
}
