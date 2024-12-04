package org.example.feignclient;

import org.example.dto.external.EventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "eventClient", url = "http://51.250.41.207:8082")
public interface JSONPlaceHolderClientEvent {

    @GetMapping("/events")
    List<EventDto> getEvents();

    @GetMapping("/events/{eventId}")
    EventDto getEventById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("eventId") Long eventId);
}