package com.maplebox.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class StringTrimModule extends SimpleModule {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1318285886448319605L;

	public StringTrimModule() {
        addDeserializer(String.class, new StdScalarDeserializer<String>(String.class) {
            /**
			 * 
			 */
			private static final long serialVersionUID = -4725417149170514249L;

			@Override
            public String deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException,
                    JsonProcessingException {
                return jsonParser.getValueAsString().trim();
			}
        });
	}
}
