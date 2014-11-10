package com.sanbeso.dao;

public enum DaoMessagesKeys {

	OBJECT_NOT_FOUND		("object.not.found"),
    MULTIPLE_OBJECTS_FOUND	("multiple.objects.found"),
    SEARCH_FAIL				("search.error"),
    DELETE_FAIL				("delete.error"),
    UPDATE_FAIL				("update.error"),
    ADD_FAIL 				("add.error");

    private String value;
    
    DaoMessagesKeys(String content) {
    	value = content;
    }

	public String getValue() {
		return value;
	}
}
