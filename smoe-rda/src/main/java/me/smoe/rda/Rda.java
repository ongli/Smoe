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
package me.smoe.rda;

import java.io.Serializable;
import java.util.List;

import javax.sql.DataSource;

import me.smoe.rda.common.PageAndOrder;
import me.smoe.rda.exception.RdaException;
import me.smoe.rda.handler.impl.RdaHandler;

public final class Rda {
	
	private static final RdaHandler handler;
	static {
		handler = new RdaHandler();
	}

	private static boolean init;

	public static void to(DataSource ds) {
		JDBC.init(ds);
		
		init = true;
	}

	public static void to(String url, String un, String pw) {
		JDBC.init(url, un, pw);
		
		init = true;
	}

	public static <T> Be<T> at(Class<T> clazz) {
		return new Be<T>(clazz);
	}

	public static class Be<T> {
		
		private Class<T> clazz;
		
		private PageAndOrder pageAndOrder = new PageAndOrder();
		
		Be(Class<T> clazz) {
			if (init) {
				this.clazz = clazz;
			} else {
				throw new RuntimeException("Rda Need init.");
			}
		}
		
		public Be<T> limit(int offset) {
			pageAndOrder.setLimit(new int[]{offset});
			
			return this;
		}

		public Be<T> limit(int offset, int rows) {
			pageAndOrder.setLimit(new int[]{offset, rows});
			
			return this;
		}
		
		public Be<T> orderBy(String field) {
			return orderBy(field, true);
		}

		public Be<T> orderBy(String field, boolean isAsc) {
			pageAndOrder.getOrderBy().put(field, isAsc);
			
			return this;
		}

		public void save(T entity) {
			handler.save(entity);
		}
		
		public void modify(T entity) {
			handler.modify(entity);
		}

		public T findOne(Serializable id) throws RdaException {
			return handler.findOne(clazz, id);
		}

		public List<T> find() throws RdaException {
			return handler.find(clazz, pageAndOrder);
		}

		public List<T> find(T entity) throws RdaException {
			return handler.find(entity, pageAndOrder);
		}

		public List<T> find(Iterable<? extends Serializable> ids) throws RdaException {
			return handler.find(clazz, ids);
		}

		public List<T> findAll() throws RdaException {
			return handler.findAll(clazz);
		}

		public void delete(Serializable id) throws RdaException {
			handler.delete(clazz, id);
		}

		public void delete(Iterable<? extends Serializable> ids) throws RdaException {
			handler.delete(clazz, ids);
		}

		public void deleteAll() throws RdaException {
			handler.deleteAll(clazz);
		}

		public long count() throws RdaException {
			return handler.count(clazz, null);
		}

		public long count(T entity) throws RdaException {
			return handler.count(clazz, entity);
		}

		public boolean exists(Serializable id) throws RdaException {
			return handler.exists(clazz, id);
		}

		public Long build(String sql) throws RdaException {
			// TODO
			return null;
		}

		public Class<T> getClazz() {
			return clazz;
		}
	}
}
