package sdu.se06.auctioncommon.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class BidRequestDeserializer implements Deserializer<BidRequest> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No additional configuration required
    }

    @Override
    public BidRequest deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                return null;
            }
            return objectMapper.readValue(data, BidRequest.class);
        } catch (Exception e) {
            throw new SerializationException("Error deserializing BidRequest object", e);
        }
    }

    @Override
    public void close() {
        // No resources to be released
    }
}
