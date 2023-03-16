package com.example.rentify.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserWithRolesDTO extends UserDTO {
    private List<RoleDTO> roles;
}
