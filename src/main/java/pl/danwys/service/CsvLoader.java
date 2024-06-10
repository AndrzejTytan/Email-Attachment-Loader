package pl.danwys.service;

import org.springframework.stereotype.Service;
import pl.danwys.entity.ItemTimeSerie;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class CsvLoader implements AttachmentLoader {
    @Override
    public List<ItemTimeSerie> parse(byte[] attachmentBytes, LocalDateTime dateReceived, String sender) {
        String[] attachmentRows = new String(attachmentBytes, StandardCharsets.UTF_8)
                .split("\\r?\\n|\\r"); // covers newline for all operating systems

        for (String row : attachmentRows) {
            System.out.println(row);
        }

        // csv columns from which data must be extracted depend on TimeSeriesSupplier

        List<ItemTimeSerie> itemTimeSeries = new ArrayList<>();
        Iterator<String> iterator = Arrays.stream(attachmentRows).iterator();
        iterator.next(); // skip header row
        while (iterator.hasNext()) {
            ItemTimeSerie itemTimeSerie = new ItemTimeSerie();

        }

        // persist both ProcessTimeSeriesEmail and ItemTimeSeries - separate services

        return null;
    }
}
