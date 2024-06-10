package pl.danwys.service;

import org.springframework.stereotype.Service;
import pl.danwys.entity.ItemDetail;
import pl.danwys.entity.ItemTimeSerie;
import pl.danwys.entity.ProcessedTimeSeriesEmail;
import pl.danwys.entity.TimeSeriesSupplier;
import pl.danwys.repository.ItemDetailRepository;
import pl.danwys.repository.ItemTimeSerieRepository;
import pl.danwys.repository.ProcessedTimeSeriesEmailRepository;
import pl.danwys.repository.TimeSeriesSupplierRepository;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class CsvLoader implements AttachmentLoader {
    private final ProcessedTimeSeriesEmailRepository processedTimeSeriesEmailRepository;
    private final TimeSeriesSupplierRepository timeSeriesSupplierRepository;
    private final ItemTimeSerieRepository itemTimeSerieRepository;
    private final ItemDetailRepository itemDetailRepository;

    public CsvLoader(ProcessedTimeSeriesEmailRepository processedTimeSeriesEmailRepository,
                     TimeSeriesSupplierRepository timeSeriesSupplierRepository, ItemTimeSerieRepository itemTimeSerieRepository, ItemDetailRepository itemDetailRepository) {
        this.processedTimeSeriesEmailRepository = processedTimeSeriesEmailRepository;
        this.timeSeriesSupplierRepository = timeSeriesSupplierRepository;
        this.itemTimeSerieRepository = itemTimeSerieRepository;
        this.itemDetailRepository = itemDetailRepository;
    }

    @Override
    public void parse(byte[] attachmentBytes, LocalDateTime dateReceived, TimeSeriesSupplier timeSeriesSupplier) {


        ProcessedTimeSeriesEmail sourceEmail =
                new ProcessedTimeSeriesEmail(timeSeriesSupplier, dateReceived, LocalDateTime.now());
        sourceEmail = processedTimeSeriesEmailRepository.save(sourceEmail);

        int priceValueColumnIndex = timeSeriesSupplier.getFileColumnIndexPriceValue();
        int priceDateColumnIndex = timeSeriesSupplier.getFileColumnIndexPriceDate();
        int identifierCodeColumnIndex = timeSeriesSupplier.getFileColumnIndexIdentifierCode();


        String[] csvRows = new String(attachmentBytes, StandardCharsets.UTF_8)
                .split("\\r?\\n|\\r"); // covers newline for all operating systems
        // csv columns from which data must be extracted depend on TimeSeriesSupplier
        Iterator<String> iterator = Arrays.stream(csvRows).iterator();
        iterator.next(); // skip header row
        while (iterator.hasNext()) {
            String[] row = iterator.next().split(",");

            Optional<ItemDetail> itemDetailO = itemDetailRepository.findItemDetailByIdentifierCode(row[identifierCodeColumnIndex]);
            if (itemDetailO.isEmpty()) {
                // TODO notify admin of new/missing item detail sent to mailbox
                continue;
            }
            ItemDetail itemDetail = itemDetailO.get();
            BigDecimal priceValue = new BigDecimal(row[priceValueColumnIndex]);
            LocalDate priceDate = LocalDate.parse(row[priceDateColumnIndex], DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            ItemTimeSerie itemTimeSerie = new ItemTimeSerie(itemDetail, sourceEmail, priceValue, priceDate);
            itemTimeSerieRepository.save(itemTimeSerie);
        }
    }
}
