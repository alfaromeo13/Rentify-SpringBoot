package com.example.rentify.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserWithRolesDTO extends UserDTO {
    private List<RoleDTO> roles;
}
