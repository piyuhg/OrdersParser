package com.orders.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.models.JsonOrder;

public class JsonOrderParser {

	public List<JsonOrder> parse(String fileName) {
		final ObjectMapper mapper = new ObjectMapper();
		List<JsonOrder> jsonOrders = null;
		try (FileReader fileReader = new FileReader(new File(fileName));
				BufferedReader br = new BufferedReader(fileReader);) {
			jsonOrders = new ArrayList<>();
			String line;
			while ((line = br.readLine()) != null) {
				JsonOrder jsonOrder = mapper.readValue(line, JsonOrder.class);
				jsonOrders.add(jsonOrder);
			}

		} catch (Exception e) {
			//silently catch all the exceptions 
		}

		return jsonOrders;
	}

}
