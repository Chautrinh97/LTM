package com.ctv.model.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceHelper {
	private static DataSource dataSource;

	private DataSourceHelper() {
	}

	public static DataSource getDataSource() {
		if (dataSource == null) {
			Context context;
			try {
				context = new InitialContext();
				dataSource = (DataSource) context.lookup("java:comp/env/jdbc/ctv");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return dataSource;
	}
}
