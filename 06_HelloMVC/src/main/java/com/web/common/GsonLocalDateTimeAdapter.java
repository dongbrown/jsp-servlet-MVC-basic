package com.web.common;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonLocalDateTimeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

	@Override
	public LocalDate deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		//json으로 넘어온 날짜형식을 LocaDate로 변환해주는 메소드
		// json String -> LocalDate로 변환
		return LocalDate.parse(arg0.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
		
		
	}

	@Override
	public JsonElement serialize(LocalDate arg0, Type arg1, JsonSerializationContext arg2) {
		// java객체를 jsonString으로 변환
		return new JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE.format(arg0));
	}
	
	
}
