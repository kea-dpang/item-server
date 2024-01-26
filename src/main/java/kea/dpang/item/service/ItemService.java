package kea.dpang.item.service;

import kea.dpang.item.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    /**
     * 주어진 ID에 해당하는 상품의 정보를 조회합니다.
     *
     * @param itemId 조회할 상품의 ID
     * @return 조회된 상품의 상세 정보가 담긴 Detail DTO
     */
    ItemResponseDto getItem(Long itemId);

    /**
     * 상품 목록을 조회합니다.
     */
    List<ItemSimpleFrontendDto> getItemListForFrontend(Pageable pageable);

    List<ItemSimpleBackendDto> getItemListForBackend();


    /**
     * 새로운 상품을 등록합니다.
     *
     * @param itemCreateDto 등록할 상품의 정보가 담긴 DTO
     * @return 등록된 상품의 정보가 담긴 Detail DTO
     */
    ItemResponseDto createItem(ItemCreateDto itemCreateDto);

    /**
     * 상품의 정보를 업데이트합니다.
     *
     * @param itemId 업데이트할 상품의 ID
     * @param itemUpdateDto 업데이트할 상품의 정보가 담긴 DTO
     * @return 업데이트된 상품의 정보가 담긴 Detail DTO
     */
    ItemResponseDto updateItem(Long itemId, ItemUpdateDto itemUpdateDto);

    /**
     * 주어진 ID에 해당하는 상품을 삭제합니다.
     *
     * @param itemId 삭제할 상품의 ID
     */
    void deleteItem(Long itemId);

    /**
     * 인기 상품을 조회합니다.
     */
    List<PopularItemDto> getPopularItems();


    void increaseStock(Long itemId, int quantity);
    void decreaseStock(Long itemId, int quantity);

}
