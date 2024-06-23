package com.api.wiveService.WineService.domain.user.dto;

import com.api.wiveService.WineService.domain.user.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private String status;
    private UserRole role;
}
