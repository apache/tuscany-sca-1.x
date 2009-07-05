package org.apache.tuscany.sca.domain.search.impl;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.tuscany.sca.domain.search.Result;

public abstract class ResultImpl implements Result {

	private static final long serialVersionUID = 7084570994751217396L;

	private Result container;

	private HashMap<String, Result> contents;

	private String name;

	public ResultImpl() {
		// empty constructor
	}

	public ResultImpl(String name) {
		this.name = name;
	}

	public Result getContainer() {
		return this.container;
	}

	public Map<String, Result> getContents() {

		if (this.contents == null) {
			return Collections.emptyMap();
		}

		return Collections.unmodifiableMap(this.contents);

	}

	public String getName() {
		return this.name;
	}

	public void setContainer(Result container) {

		if (container != this.container) {
			
			if (this.container != null) {
				this.container.removeContent(this);
			}

			this.container = container;

			if (container != null) {
				container.addContent(this);
			}

		}

	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Result) {
			Result artifactResult = (Result) obj;

			if (artifactResult.getName() == this.name || this.name != null
					&& this.name.equals(artifactResult.getName())) {

				if (artifactResult.getContainer() == this.container
						|| this.container != null
						&& this.container.equals(artifactResult.getContainer())) {

					Map<String, Result> contents = artifactResult.getContents();

					if (this.contents == null) {
						return contents.isEmpty();

					} else if (this.contents.equals(contents)) {
						return true;
					}

				}

			}

		}

		return false;

	}

	public void addContent(Result artifactResult) {
		internalGetContents().put(artifactResult.getName(), artifactResult);
		
		if (artifactResult.getContainer() != this) {
			artifactResult.setContainer(this);
		}

	}

	private HashMap<String, Result> internalGetContents() {

		if (this.contents == null) {
			this.contents = new HashMap<String, Result>();
		}

		return this.contents;

	}

	public void removeContent(Result artifactResult) {

		if (this.contents != null) {
			this.contents.remove(artifactResult);

			artifactResult.setContainer(null);

			if (this.contents.isEmpty()) {
				this.contents = null;
			}

		}

	}

	@Override
	public int hashCode() {
		int hash = 11;

		hash = hash * 31
				+ (this.container == null ? 7 : this.container.hashCode());
		hash = hash
				* 31
				+ (this.contents == null || this.contents.isEmpty() ? 13
						: this.contents.hashCode());
		hash = hash * 31 + (this.name == null ? 17 : this.name.hashCode());

		return hash;

	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("<");
		Result container = getContainer();
		
		sb.append(getClass().getName()).append(" name='").append(getName())
				.append("' container='").append(container != null ? container.getName() : null).append("'>\n");

		Method[] methods = getClass().getMethods();

		for (Method method : methods) {
			String methodName = method.getName();

			if (method.getReturnType() != void.class) {

				if (method.getParameterTypes().length == 0) {

					if (methodName.startsWith("get")
							&& !"getName".equals(methodName)
							&& !"getContainer".equals(methodName)) {

						try {
							Object returnedObj = method.invoke(this);

							sb.append('\t');

							if (returnedObj instanceof Map<?, ?>) {

								sb.append("<collection type='").append(
										returnedObj.getClass()
												.getGenericInterfaces()[1])
										.append("'>\n");

								for (Object obj : ((Map<?, ?>) returnedObj).values()) {

									sb.append("\t\t").append(obj.toString())
											.append("\n");

								}

								sb.append("\t</collection>\n");

							} else if (returnedObj instanceof Collection<?>) {

								sb.append("<collection type='").append(
										returnedObj.getClass()
												.getGenericInterfaces()[0])
										.append("'>\n");

								for (Object obj : (Collection<?>) returnedObj) {

									sb.append("\t\t").append(obj.toString())
											.append("\n");

								}

								sb.append("\t</collection>\n");

							} else if (returnedObj.getClass().isArray()) {

								sb.append("<array type='").append(
										returnedObj.getClass()
												.getComponentType())
										.append("'>\n");
								
								int length = Array.getLength(returnedObj);

								for (int i = 0 ; i < length ; i++) {

									sb.append("\t\t").append(Array.get(returnedObj, i).toString())
											.append("\n");

								}

								sb.append("\t</array>\n");

							} else {
								
								sb.append('\t').append(returnedObj).append('\n');
								
							}

						} catch (Throwable e) {
							// ignore exceptions and don't print the object
						}

					}

				}

			}

		}
		
		sb.append("</").append(getClass().getName()).append(">");

		return sb.toString();

	}
	
}
