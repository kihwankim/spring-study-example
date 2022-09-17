package com.example.javaspringcloudcontract.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "calleeCaller",
        url = "${callee.base-url}",
        primary = false
)
public interface CalleeCaller {

    @GetMapping("/api/v1/callee")
    ResponseEntity<String> callCalleeApplication(@RequestParam int num);
}
