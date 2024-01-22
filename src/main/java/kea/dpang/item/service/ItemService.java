package kea.dpang.item.service;

import kea.dpang.item.dto.CreateItemDto;
import kea.dpang.item.dto.ItemDetailDto;
import kea.dpang.item.dto.ItemThumbnailDto;
import kea.dpang.item.dto.UpdateItemDto;

public interface ItemService {

    /**
     * 주어진 ID에 해당하는 상품의 정보를 조회합니다.
     *
     * @param itemId 조회할 상품의 ID
     * @return 조회된 상품의 상세 정보가 담긴 Detail DTO
     */
    ItemDetailDto getItem(Long itemId);

    /**
     * 상품 목록을 조회합니다.
     *
     * @param itemId 조회할 상품의 ID
     */
    ItemThumbnailDto getItemThumbnail(Long itemId);


    /**
     * 새로운 상품을 등록합니다.
     *
     * @param createItemDto 등록할 상품의 정보가 담긴 DTO
     * @return 등록된 상품의 정보가 담긴 Detail DTO
     */
    ItemDetailDto createItem(CreateItemDto createItemDto);

    /**
     * 상품의 정보를 업데이트합니다.
     *
     * @param itemId 업데이트할 상품의 ID
     * @param updateItemDto 업데이트할 상품의 정보가 담긴 DTO
     * @return 업데이트된 상품의 정보가 담긴 Detail DTO
     */
    ItemDetailDto updateItem(Long itemId, UpdateItemDto updateItemDto);

    /**
     * 주어진 ID에 해당하는 상품을 삭제합니다.
     *
     * @param itemId 삭제할 상품의 ID
     */
    void deleteItem(Long itemId);

    ItemDetailDto getPopularItems(Long itemId, Double score);

    void incrementItemViewCount(Long itemId);
}
