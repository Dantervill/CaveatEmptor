package com.senla.internship.caveatemptor.service;

import com.senla.internship.caveatemptor.dto.ItemDto;
import com.senla.internship.caveatemptor.exceptions.DataNotFoundException;
import com.senla.internship.caveatemptor.exceptions.ItemNotFoundException;
import com.senla.internship.caveatemptor.model.Item;
import com.senla.internship.caveatemptor.repositories.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ModelMapper mapper;
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ModelMapper mapper, ItemRepository itemRepository) {
        this.mapper = mapper;
        this.itemRepository = itemRepository;
    }

    public ItemDto getItemById(Long id) {
        String msg = String.format("Item with id %d not found", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(msg));
        return mapper.map(item, ItemDto.class);
    }

    public void saveItem(ItemDto itemDto) {
        Item item = mapper.map(itemDto, Item.class);
        itemRepository.save(item);
    }

    public ItemDto updateItem(Long id, ItemDto dto) {
        String msg = String.format("Item with id %d not found", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(msg));
        item.setName(dto.getName());
        item.setInitialPrice(dto.getInitialPrice());
        item.setBuyNowPrice(dto.getBuyNowPrice());
        item.setAuctionEnd(dto.getAuctionEnd());
        item.setMetricWeight(dto.getMetricWeight());
        item = itemRepository.save(item);
        return mapper.map(item, ItemDto.class);
    }

    public List<ItemDto> getAllItems() {
        List<Item> items = itemRepository.findAll();

        if (items.size() < 1) {
            String msg = "Data not fount";
            throw new DataNotFoundException(msg);
        }

        return items
                .stream()
                .map(item -> mapper.map(item, ItemDto.class))
                .collect(Collectors.toList());
    }
}
