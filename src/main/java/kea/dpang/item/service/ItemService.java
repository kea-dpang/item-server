package kea.dpang.item.service;

import kea.dpang.item.dto.*;
import kea.dpang.item.entity.Item;
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
     * 상품 카드 목록을 페이지 정보에 따라 조회합니다.
     *
     * @param pageable 페이지 정보
     * @return 조회된 상품 카드 목록이 담긴 DTO 리스트
     */
    List<ItemCardDto> getItemCard(Pageable pageable);

    /**
     * 관리자용 상품 목록을 페이지 정보에 따라 조회합니다.
     *
     * @param pageable 페이지 정보
     * @return 조회된 상품 목록이 담긴 DTO 리스트
     */
    List<ItemManageListDto> getItemManageList(Pageable pageable);

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
     * @param itemIds 삭제할 상품의 ID 리스트
     */
    void deleteItem(List<Long> itemIds);

    /**
     * 인기 상품을 조회합니다.
     *
     * @return 조회된 인기 상품 목록이 담긴 DTO 리스트
     */
    List<PopularItemDto> getPopularItems();

    /**
     * 주어진 ID에 해당하는 상품의 재고 수량을 조회합니다.
     *
     * @param itemId 재고 수량을 조회할 상품의 ID
     * @return 조회된 재고 수량
     */
    int getStockQuantity(Long itemId);

    /**
     * 주어진 ID에 해당하는 상품의 재고 수량을 증가시킵니다.
     *
     * @param itemId 재고 수량을 증가시킬 상품의 ID
     * @param quantity 증가시킬 재고 수량
     */
     int increaseStock(Long itemId, int quantity);

    /**
     * 주어진 ID에 해당하는 상품의 재고 수량을 감소시킵니다.
     *
     * @param itemId 재고 수량을 감소시킬 상품의 ID
     * @param quantity 감소시킬 재고 수량
     */
    int decreaseStock(Long itemId, int quantity);


    /* feign */
    // 이벤트
    String getItemName(Long ItemId);

    // 주문
    Item getItemInquiry(Long itemId);

    // 판매처
    String getSellerName(Long SellerId);

    /**
     * 백엔드용 상품 목록 조회 기능 입니다.
     *
     * @return 조회된 모든 상품 목록이 담긴 DTO 리스트
     */
    List<ItemSimpleBackendDto> getItemListForBackend();

}
