package course.spring.spring_6_rest_mvc.services;

import course.spring.spring_6_rest_mvc.model.BeerCSVRecord;

import java.io.File;
import java.util.List;

public interface BeerCsvService {

    List<BeerCSVRecord> convertCSV(File csvFile);

}
