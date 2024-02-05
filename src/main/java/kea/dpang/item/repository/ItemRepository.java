package kea.dpang.item.repository;

import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE " +
            "(:category IS NULL OR i.category = :category) AND " +
            "(:subCategory IS NULL OR i.subCategory = :subCategory) AND " +
            "(:sellerId IS NULL OR i.sellerId = :sellerId) AND " +
            "(:minPrice = 0 OR i.itemPrice >= :minPrice) AND " +
            "(:maxPrice = 2000000 OR i.itemPrice <= :maxPrice) AND " +
            "(i.itemName LIKE %:keyword%)")
//            "(i.itemName LIKE %:keyword% OR i.description LIKE %:keyword%)")
    Page<Item> filterItems(
            @Param("category") Category category,
            @Param("subCategory") SubCategory subCategory,
            @Param("sellerId") Long sellerId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("keyword") String keyword,
            Pageable pageable);

    Page<Item> findByCategoryAndSubCategoryAndPriceBetweenAndNameContainingAndSellerId(
            Category category,
            SubCategory subCategory,
            Double minPrice,
            Double maxPrice,
            String keyword,
            Long sellerId,
            Pageable pageable);

}

