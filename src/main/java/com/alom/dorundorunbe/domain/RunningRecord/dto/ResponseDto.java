package com.alom.dorundorunbe.domain.RunningRecord.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> {
    private boolean isSuccess;
    private int code;
    private String message;
    private T result;
}
