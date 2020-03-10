package com.hellofresh.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.skyscreamer.jsonassert.JSONAssert;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class DeserizalizeUtil<T> {

	final private Class<T> myType;

	public DeserizalizeUtil(Class<T> myType) {
		this.myType = myType;
	}

	Gson gson = new Gson();

	public T deserzializeJSONFiletoModal(String jsonPath) throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		
		return gson.fromJson(new FileReader(System.getProperty("user.dir") + "\\src\\main\\resources\\JsonData\\JsonModalData\\" +jsonPath), myType);
	}
	
	public T deserzializeResponseEntityFiletoModal(String jsonPath) throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		return gson.fromJson(new FileReader(System.getProperty("user.dir") + "\\src\\main\\resources\\JsonData\\ErrorModalData\\" +jsonPath), myType);
	}
	
	public T deserzializeStringtoModal(String responseBody) {
		
		
		return gson.fromJson(responseBody, myType);
	}


}
