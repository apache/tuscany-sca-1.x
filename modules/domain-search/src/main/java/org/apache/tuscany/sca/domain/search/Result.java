package org.apache.tuscany.sca.domain.search;

import java.io.Serializable;
import java.util.Map;

public interface Result extends Serializable {
	
	String getValue();
	
	void setValue(String value);
	
	Result getContainer();
	
	Map<String, Result> getContents();
	
	void addContent(Result artifactResult);
	
	void removeContent(Result artifactResult);
	
	void setContainer(Result container);
	
	String getField();
	
	void setField(String field);

}
