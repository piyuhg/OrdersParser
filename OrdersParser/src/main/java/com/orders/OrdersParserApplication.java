package com.orders;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.converters.CsvOrderConverter;
import com.orders.converters.JsonOrderConverter;
import com.orders.models.CsvOrder;
import com.orders.models.JsonOrder;
import com.orders.models.Order;
import com.orders.parsers.CsvOrderParser;
import com.orders.parsers.JsonOrderParser;

/**
 * @author PIYUSH GUPTA
 *
 */
@SpringBootApplication
public class OrdersParserApplication implements CommandLineRunner {

	private static final String FILE_EXTENSION_JSON = ".json";
	private static final String FILE_EXTENSION_CSV = ".csv";

	public static void main(String[] args) {
		SpringApplication.run(OrdersParserApplication.class, args);
	}

	@Override
	public void run(String... fileNames) throws Exception {

		final int threadPoolSize = fileNames.length;

		// create thread pool of size equals to numbers of file name arguments for
		// parsing
		ExecutorService parsingThreadpool = Executors.newFixedThreadPool(threadPoolSize);
		// create thread pool of size equals to numbers of file name arguments for
		// conversion
		ExecutorService conversionThreadPool = Executors.newFixedThreadPool(threadPoolSize);

		// <fileName,future> map to put and get future objects from thread pool by
		// filename
		@SuppressWarnings("rawtypes")
		LinkedHashMap<String, Future> parsingFutureMap = new LinkedHashMap<>(threadPoolSize);
		LinkedHashMap<String, Future<List<Order>>> conversionFutureMap = new LinkedHashMap<>(threadPoolSize);

		// final output
		final List<Order> orders = new ArrayList<>();

		// Iterate over the file names given as arguments
		// If filename is of type FILE_EXTENSION_CSV or FILE_EXTENSION_JSON,
		// then create respective parser callables and submit them along with
		// putting to future maps
		for (String fileName : fileNames) {
			if (fileName.endsWith(FILE_EXTENSION_CSV)) {
				Callable<List<CsvOrder>> parseCsvOrdersCallable = getParseCsvOrdersCallable(fileName);
				parsingFutureMap.put(fileName, parsingThreadpool.submit(parseCsvOrdersCallable));
			} else if (fileName.endsWith(FILE_EXTENSION_JSON)) {
				Callable<List<JsonOrder>> parseJsonOrdersCallable = getParseJsonOrdersCallable(fileName);
				parsingFutureMap.put(fileName, parsingThreadpool.submit(parseJsonOrdersCallable));
			}
		}

		// shutdown the parsing thread pool as new task needs to be executed now
		parsingThreadpool.shutdown();

		// Iterate over the parsing future maps keys and get list of parsed
		// csv and json order objects. Further call respective converters
		// on separate threads
		for (String fileName : parsingFutureMap.keySet()) {
			if (fileName.endsWith(FILE_EXTENSION_CSV)) {
				@SuppressWarnings("unchecked")
				List<CsvOrder> csvOrders = (List<CsvOrder>) parsingFutureMap.get(fileName).get();
				if (!CollectionUtils.isEmpty(csvOrders)) {
					Callable<List<Order>> convertCsvOrdersCallable = getConvertCsvOrdersCallable(csvOrders, fileName);
					conversionFutureMap.put(fileName, conversionThreadPool.submit(convertCsvOrdersCallable));
				}
			} else if (fileName.endsWith(FILE_EXTENSION_JSON)) {
				@SuppressWarnings("unchecked")
				List<JsonOrder> jsonOrders = (List<JsonOrder>) parsingFutureMap.get(fileName).get();
				if (!CollectionUtils.isEmpty(jsonOrders)) {
					Callable<List<Order>> convertJsonOrdersCallable = getConvertJsonOrdersCallable(jsonOrders,
							fileName);
					conversionFutureMap.put(fileName, conversionThreadPool.submit(convertJsonOrdersCallable));
				}
			}
		}

		// shutdown the conversion thread pool as new task needs to be executed now
		conversionThreadPool.shutdown();

		// Iterate over the conversion future map and add the orders to final output
		// list
		for (String fileName : conversionFutureMap.keySet()) {
			List<Order> convertedOrders = conversionFutureMap.get(fileName).get();
			if (!CollectionUtils.isEmpty(convertedOrders)) {
				orders.addAll(convertedOrders);
			}
		}

		// write the list to console line by line
		writeToConsole(orders);

	}

	private Callable<List<CsvOrder>> getParseCsvOrdersCallable(String fileName) {
		return () -> new CsvOrderParser().parse(fileName);
	}

	private Callable<List<JsonOrder>> getParseJsonOrdersCallable(String fileName) {
		return () -> new JsonOrderParser().parse(fileName);
	}

	private Callable<List<Order>> getConvertJsonOrdersCallable(List<JsonOrder> jsonOrders, String fileName) {
		return () -> new JsonOrderConverter().convert(jsonOrders, fileName);
	}

	private Callable<List<Order>> getConvertCsvOrdersCallable(List<CsvOrder> csvOrders, String fileName) {
		return () -> new CsvOrderConverter().convert(csvOrders, fileName);
	}

	/**
	 * writes orders line by line on console ordered by id of every order
	 * @param orders
	 */
	private void writeToConsole(List<Order> orders) {
		ObjectMapper mapper = new ObjectMapper();
		orders.stream().sorted(Comparator.comparingLong(Order::getId)).forEach(order -> {
			try {
				System.out.println(mapper.writeValueAsString(order));
			} catch (Exception e) {
			}
		});
	}

}
