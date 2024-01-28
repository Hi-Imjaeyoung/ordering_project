package com.example.Order.Item.controller;

import com.example.Order.Item.dto.ItemSaveDto;
import com.example.Order.Item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    //TODO : CRUD생성

    @Autowired
    private final ItemService itemService;
    public ItemController(ItemService itemService){
        this.itemService =itemService;
    }

    @PostMapping("item/new")
    public void createItem(@RequestBody ItemSaveDto itemSaveDto){
        itemService.createItem(itemSaveDto);
    }

}
