package com.alom.dorundorunbe.domain.RunningRecord.controller;

import com.alom.dorundorunbe.domain.RunningRecord.service.RunningRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/records")
public class RunningRecordController {
    private final RunningRecordService runningRecordService;

}
