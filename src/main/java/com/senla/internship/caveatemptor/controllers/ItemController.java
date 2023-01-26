package com.senla.internship.caveatemptor.controllers;

import com.senla.internship.caveatemptor.dto.ItemDto;
import com.senla.internship.caveatemptor.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items/{id}")
    public ItemDto getItem(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/items")
    public List<ItemDto> getItems() {
        return itemService.getAllItems();
    }

    @PostMapping("/items")
    public ResponseEntity<?> postItem(@RequestBody ItemDto itemDto) {
        itemService.saveItem(itemDto);
        return new ResponseEntity<>("Item saved", HttpStatus.OK);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long id, @RequestBody ItemDto dto) {
        ItemDto itemDto = itemService.updateItem(id, dto);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }
}
