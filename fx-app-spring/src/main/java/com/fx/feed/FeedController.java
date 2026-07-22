package com.fx.feed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feed;

    public FeedController(FeedService feed) { this.feed = feed; }

    @PostMapping("/rates")
    public ResponseEntity<Void> receive(@RequestBody IncomingBatch batch) {
        feed.handle(batch);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
