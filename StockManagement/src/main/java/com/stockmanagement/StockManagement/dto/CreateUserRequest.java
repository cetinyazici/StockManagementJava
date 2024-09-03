package com.stockmanagement.StockManagement.dto;

import com.stockmanagement.StockManagement.model.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record CreateUserRequest(
        String name,
        String username,
        String password,
        Set<Role> authorities
) {
}
