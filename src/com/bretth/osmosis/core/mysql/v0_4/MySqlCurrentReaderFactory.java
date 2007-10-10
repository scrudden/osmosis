package com.bretth.osmosis.core.mysql.v0_4;

import java.util.Map;

import com.bretth.osmosis.core.database.DatabaseLoginCredentials;
import com.bretth.osmosis.core.database.DatabasePreferences;
import com.bretth.osmosis.core.database.DatabaseTaskManagerFactory;
import com.bretth.osmosis.core.pipeline.common.TaskManager;
import com.bretth.osmosis.core.pipeline.v0_4.RunnableSourceManager;


/**
 * The task manager factory for a database reader.
 * 
 * @author Brett Henderson
 */
public class MySqlCurrentReaderFactory extends DatabaseTaskManagerFactory {
	private static final String ARG_READ_ALL_USERS = "readAllUsers";
	private static final boolean DEFAULT_READ_ALL_USERS = false;
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected TaskManager createTaskManagerImpl(String taskId, Map<String, String> taskArgs, Map<String, String> pipeArgs) {
		DatabaseLoginCredentials loginCredentials;
		DatabasePreferences preferences;
		boolean readAllUsers;
		
		// Get the task arguments.
		loginCredentials = getDatabaseLoginCredentials(taskId, taskArgs);
		preferences = getDatabasePreferences(taskId, taskArgs);
		readAllUsers = getBooleanArgument(taskId, taskArgs, ARG_READ_ALL_USERS, DEFAULT_READ_ALL_USERS);
		
		return new RunnableSourceManager(
			taskId,
			new MySqlCurrentReader(loginCredentials, preferences, readAllUsers),
			pipeArgs
		);
	}
}