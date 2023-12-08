package com.feri.sua.auth.user.dto;

import com.feri.sua.auth.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserByIdResponseDto {

    private String id;
    private String email;
    private String name;
    private String city;
    private String country;
    private String address;
    private String postalCode;
    private String phone;
    private Role role;

}
