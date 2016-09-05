/**
 * Copyright (c) 2016, adar.w (adar-w@outlook.com) 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.smoe.rda.core;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

final class JDBC {
	
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	
	private static String url;

	private static String un;

	private static String pw;
	
	private static DataSource ds;
	
	private static boolean useDS;
	
	static void init(DataSource ds) {
		loadDriver();
		
		JDBC.ds = ds;
		
		useDS = true;
	}

	static void init(String url, String un, String pw) {
		loadDriver();

		JDBC.url = url;
		JDBC.un = un;
		JDBC.pw = pw;
	}

	private static void loadDriver() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("No driver: %s.", DRIVER));
		}
	}

	static Connection connection() throws Exception {
		return useDS ? ds.getConnection() : DriverManager.getConnection(url, un, pw); 
	}
}