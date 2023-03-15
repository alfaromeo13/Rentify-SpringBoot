package com.example.rentify.dto;

import java.util.ArrayList;
import java.util.List;

public class UserWithRolesDTO extends UserDTO {
//ako ne bude radilo sa nasljedjivanjem onda dodaj sve atribute iz parent klase ovdje
    private List<RoleDTO> roles = new ArrayList<>();

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
