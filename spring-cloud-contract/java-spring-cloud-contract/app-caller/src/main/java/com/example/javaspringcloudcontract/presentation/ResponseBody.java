package com.example.javaspringcloudcontract.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseBody {
    private int code;
    private String result;
}
