package com.stockmanagement.StockManagement.dto;

public record AuthRequest(
        String username,
        String password
) {
}
