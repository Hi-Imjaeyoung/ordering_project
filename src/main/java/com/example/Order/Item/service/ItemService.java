package com.example.Order.Item.service;

import com.example.Order.Item.domain.Item;
import com.example.Order.Item.dto.ItemSaveDto;
import com.example.Order.Item.repo.ItemRepo;
import com.example.Order.Member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private final ItemRepo itemRepo;
    public ItemService(ItemRepo itemRepo){
        this.itemRepo = itemRepo;
    }

    public void createItem(ItemSaveDto itemSaveDto){
        Item item = Item.builder().name(itemSaveDto.getName())
                .price(itemSaveDto.getPrice())
                .imagePath(itemSaveDto.getImagePath())
                .stockQuantity(itemSaveDto.getStockQuantity())
                .build();
        itemRepo.save(item);
    }
}
