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

    private Role role;

}
