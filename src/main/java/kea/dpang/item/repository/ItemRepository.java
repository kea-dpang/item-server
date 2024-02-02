package kea.dpang.item.repository;

import kea.dpang.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // itemId 필드가 itemIds 리스트에 포함된 모든 Item 엔티티를 조회합니다.
    List<Item> findAllByItemIdIn(List<Long> itemIds);
}

