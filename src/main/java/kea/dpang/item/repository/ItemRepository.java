package kea.dpang.item.repository;

import kea.dpang.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemId(Long id);
    List<Item> findByItemNameContaining(String itemName);
}

