package com.fx.feed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/accepting")
public class AcceptingController {

    private final AcceptingState state;

    public AcceptingController(AcceptingState state) { this.state = state; }

    @GetMapping
    public Map<String, Boolean> get() {
        return Map.of("accepting", state.isAccepting());
    }

    @PostMapping
    public Map<String, Boolean> set(@RequestBody AcceptingRequest request) {
        return Map.of("accepting", state.set(request.accepting()));
    }

    public record AcceptingRequest(boolean accepting) {}
}
