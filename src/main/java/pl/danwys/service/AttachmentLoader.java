package pl.danwys.service;

import pl.danwys.entity.TimeSeriesSupplier;

import java.time.LocalDateTime;

public interface AttachmentLoader {
    void parse(byte[] attachmentBytes, LocalDateTime dateReceived, TimeSeriesSupplier timeSeriesSupplier);
}
