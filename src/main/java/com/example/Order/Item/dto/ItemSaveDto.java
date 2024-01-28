package com.example.Order.Item.dto;

import lombok.Data;

@Data
public class ItemSaveDto {
    private String name;
    private int price;
    private String stockQuantity;
    private String imagePath;
}
