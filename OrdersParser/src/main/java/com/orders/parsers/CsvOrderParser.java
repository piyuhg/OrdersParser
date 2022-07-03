package com.orders.parsers;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import com.orders.models.CsvOrder;

/**
 * @author PIYUSH GUPTA
 *
 */
public class CsvOrderParser {

	
	/**
	 * parse the cvs file to list of CsvOrder objects
	 * @param fileName
	 * @return
	 */
	public List<CsvOrder> parse(String fileName) {
		List<CsvOrder> csvOrders = null;
		try (FileReader fileReader = new FileReader(new File(fileName))) {
			csvOrders = new CsvToBeanBuilder<CsvOrder>(fileReader).withType(CsvOrder.class).build().parse();
		} catch (Exception e) {
			//silently catch all exceptions
		}
		return csvOrders;
	}

}
