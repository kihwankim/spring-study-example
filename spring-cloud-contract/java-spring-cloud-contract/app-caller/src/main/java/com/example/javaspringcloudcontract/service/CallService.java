package com.example.javaspringcloudcontract.service;

import com.example.javaspringcloudcontract.external.CalleeCaller;
import com.example.javaspringcloudcontract.presentation.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallService {
    private final CalleeCaller calleeCaller;

    public ResponseBody callData(int num) {

        ResponseEntity<String> calleeResult = calleeCaller.callCalleeApplication(num);

        return new ResponseBody(calleeResult.getStatusCode().value(), calleeResult.getBody());
    }
}
