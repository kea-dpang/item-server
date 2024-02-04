package kea.dpang.item.service;

import kea.dpang.item.dto.item.ItemCreateDto;
import kea.dpang.item.dto.item.ItemResponseDto;
import kea.dpang.item.dto.item.ItemUpdateDto;

import java.util.List;

public interface ItemService {

    /**
     * 새로운 상품을 등록합니다.
     *
     * @param itemCreateDto 등록할 상품의 정보가 담긴 DTO
     * @return 등록된 상품의 정보가 담긴 Detail DTO
     */
    void createItem(ItemCreateDto itemCreateDto);

    /**
     * 주어진 ID에 해당하는 상품의 정보를 조회합니다.
     *
     * @param itemId 조회할 상품의 ID
     * @return 조회된 상품의 상세 정보가 담긴 Detail DTO
     */
    ItemResponseDto getItem(Long itemId);

    // Todo: 필터링 상품 리스트 조회

    // Todo: 인기 상품 리스트 조회

    /**
     * 상품의 정보를 업데이트합니다.
     *
     * @param itemId        업데이트할 상품의 ID
     * @param itemUpdateDto 업데이트할 상품의 정보가 담긴 DTO
     * @return 업데이트된 상품의 정보가 담긴 Detail DTO
     */
    void updateItem(Long itemId, ItemUpdateDto itemUpdateDto);

    /**
     * 주어진 ID에 해당하는 상품을 삭제합니다.
     *
     * @param itemIds 삭제할 상품의 ID 리스트
     */
    void deleteItem(List<Long> itemIds);

    /**
     * 주어진 ID에 해당하는 상품의 재고 수량을 업데이트 합니다.
     *
     * @param itemId   재고 수량을 수정시킬 상품의 ID
     * @param quantity 수정시킬 재고 수량
     */
    void changeStock(Long itemId, int quantity);
}
