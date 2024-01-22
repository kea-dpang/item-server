package kea.dpang.item.repository;

import kea.dpang.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findCartItemsByItemId(List<Long> itemId);

    Item findCartItemByItemId(Long itemId);
}

