package pl.danwys.service;

import pl.danwys.entity.ItemTimeSerie;

import java.time.LocalDateTime;
import java.util.List;

public interface AttachmentLoader {
    List<ItemTimeSerie> parse(byte[] attachmentBytes, LocalDateTime dateReceived, String sender);
}
