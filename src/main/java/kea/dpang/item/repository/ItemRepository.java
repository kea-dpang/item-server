package kea.dpang.item.repository;

import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.SubCategory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // itemId 필드가 itemIds 리스트에 포함된 모든 Item 엔티티를 조회합니다.
    List<Item> findAllByItemIdIn(List<Long> itemId);

    @Query("SELECT i FROM Item i WHERE " +
            "(:category = '전체' OR i.category = :category) AND " +
            "(:subCategory = '전체' OR i.subCategory = :subCategory) AND " +
            "(:minPrice = 0 OR i.itemPrice >= :minPrice) AND " +
            "(:maxPrice = 2000000 OR i.itemPrice <= :maxPrice) AND " +
            "(i.itemName LIKE %:keyword%)")
//            "(i.itemName LIKE %:keyword% OR i.description LIKE %:keyword%)")
    Page<Item> filterItems(
            @Param("category") Category category,
            @Param("subCategory") SubCategory subCategory,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("keyword") String keyword,
            Pageable pageable);

}

