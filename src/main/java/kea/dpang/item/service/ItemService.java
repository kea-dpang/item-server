package kea.dpang.item.service;

import kea.dpang.item.dto.item.*;
import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    /**
     * 새로운 상품을 등록합니다.
     *
     * @param dto 등록할 상품의 정보가 담긴 DTO
     */
    void createItem(CreateItemRequestDto dto);

    ItemDto getItemInfo(Long itemId);

    /**
     * 주어진 ID에 해당하는 상품의 정보를 조회합니다.
     *
     * @param itemId 조회할 상품의 ID
     * @return 조회된 상품의 상세 정보가 담긴 Detail DTO
     */
    ItemDetailDto getItemDetailInfo(Long itemId);

    List<ItemDto> getItemList(List<Long> itemIds);

    Page<ItemDetailDto> getItemList(
            Category category,
            SubCategory subCategory,
            Double minPrice,
            Double maxPrice,
            String keyword,
            Long sellerId,
            Pageable pageable
    );

    // Todo: 인기 상품 리스트 조회

    /**
     * 상품의 정보를 업데이트합니다.
     *
     * @param itemId 업데이트할 상품의 ID
     * @param dto    업데이트할 상품의 정보가 담긴 DTO
     */
    void updateItem(Long itemId, UpdateItemRequestDto dto);

    /**
     * 주어진 ID에 해당하는 상품을 삭제합니다.
     *
     * @param itemIds 삭제할 상품의 ID 리스트
     */
    void deleteItem(List<Long> itemIds);

    /**
     * 주어진 ID에 해당하는 상품의 재고 수량을 업데이트 합니다.
     *
     * @param dto 업데이트할 상품의 재고 정보가 담긴 DTO
     */
    void changeStock(List<UpdateStockRequestDto> dto);
}
