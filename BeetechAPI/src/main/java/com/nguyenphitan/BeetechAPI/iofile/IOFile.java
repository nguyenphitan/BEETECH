package com.nguyenphitan.BeetechAPI.iofile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.nguyenphitan.BeetechAPI.entity.Product;

public class IOFile {
	public static String TYPE = "text/csv";
	static String[] HEADERs = { "id", "name", "price", "quantity", "photos" };

	public static boolean hasCSVFormat(MultipartFile file) {
		if (!TYPE.equals(file.getContentType())) {
			return false;
		}
		return true;
	}

	public static List<Product> csvProducts(InputStream is) {
		try (
				BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "SHIFT-JIS"));
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			) {
			List<Product> products = new ArrayList<Product>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				Product product = new Product(Long.parseLong(csvRecord.get("id")), csvRecord.get("name"),
						Long.parseLong(csvRecord.get("price")), Long.parseLong(csvRecord.get("quantity")),
						csvRecord.get("photos"));

				products.add(product);
			}
			return products;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}