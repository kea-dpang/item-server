package kea.dpang.item.service;

import kea.dpang.item.entity.Item;
import kea.dpang.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Optional<Item> getItemById(Long id) {
        // 상품 ID로 상품을 조회합니다.
        return itemRepository.findById(id);
    }
}

