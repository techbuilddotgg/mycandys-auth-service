package com.feri.sua.auth.user.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SaveUserDto {
    @Hidden
    private String email;
    @Hidden
    private String id;
    private String name;
    private String city;
    private String country;
    private String address;
    private String postalCode;
    private String phone;
}
