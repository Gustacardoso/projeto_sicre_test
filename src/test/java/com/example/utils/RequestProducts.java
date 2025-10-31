package com.example.utils;

public record RequestProducts(
        String title,
        String description,
        int price,
        double discountPercentage,
        double rating,
        int stock,
        String brand,
        String category,
        String thumbnail) {
}
