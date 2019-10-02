package com.talytica.common.json;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class UNIXTimeDeserializer  extends JsonDeserializer<Date> {
 
	    @Override
	    public Date deserialize(JsonParser parser, DeserializationContext context) 
	            throws IOException, JsonProcessingException {
	        Long unixTimestamp = parser.getValueAsLong();
	        return new Date(unixTimestamp*1000);
	    }
}
