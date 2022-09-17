package com.example.javaspringcloudcontract.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CalleController {
    @GetMapping("/api/v1/callee")
    public ResponseEntity<String> calleeFunction(@RequestParam int num) {
        NumberType type = NumberType.findNumberType(num);
        if (type == NumberType.ODD) return ResponseEntity.ok("odd val java");

        return ResponseEntity.ok("even val java");
    }

    private enum NumberType {
        ODD, EVEN;

        public static NumberType findNumberType(int num) {
            if (num % 2 == 0) {
                log.info("even");
                return EVEN;
            } else {
                log.info("odd");
                return ODD;
            }
        }
    }
}

