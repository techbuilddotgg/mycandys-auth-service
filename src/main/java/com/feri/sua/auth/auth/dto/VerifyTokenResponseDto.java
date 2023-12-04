package com.feri.sua.auth.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VerifyTokenResponseDto {
    private String userId;
}
