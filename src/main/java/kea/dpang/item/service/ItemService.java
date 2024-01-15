package kea.dpang.item.service;

import kea.dpang.item.dto.CreateItemDto;
import kea.dpang.item.dto.ItemDetailDto;

public interface ItemService {

    /**
     * 주어진 ID에 해당하는 상품의 정보를 조회합니다.
     *
     * @param itemId 조회할 상품의 ID
     * @return 조회된 상품의 상세 정보가 담긴 Detail DTO
     */
    ItemDetailDto getItem(Long itemId);

    /**
     * 새로운 상품을 등록합니다.
     *
     * @param createItemDto 등록할 상품의 정보가 담긴 DTO
     * @return 등록된 상품의 정보가 담긴 Detail DTO
     */
    ItemDetailDto createItem(CreateItemDto createItemDto);

}
