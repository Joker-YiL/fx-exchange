package com.fx.feed;

import com.fx.api.repo.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeedService {

    private static final Logger log = LoggerFactory.getLogger(FeedService.class);

    private final RateRepository rates;
    private final AckClient ackClient;
    private final AcceptingState accepting;

    public FeedService(RateRepository rates, AckClient ackClient, AcceptingState accepting) {
        this.rates = rates;
        this.ackClient = ackClient;
        this.accepting = accepting;
    }

    public void handle(IncomingBatch batch) {
        if (!accepting.isAccepting()) {
            log.info("Declined feed batch {} because ACCEPTING is OFF", batch.batchId());
            ackClient.ack(batch.batchId(), "DECLINED");
            return;
        }

        try {
            for (IncomingRate rate : batch.rates()) {
                rates.insert(rate.base().toUpperCase(), rate.quote().toUpperCase(), rate.rate());
            }
            log.info("Stored {} rates from feed batch {}", batch.rates().size(), batch.batchId());
            ackClient.ack(batch.batchId(), "ACCEPTED");
        } catch (RuntimeException e) {
            log.error("Could not store feed batch {}", batch.batchId(), e);
            ackClient.ack(batch.batchId(), "DECLINED");
        }
    }
}
