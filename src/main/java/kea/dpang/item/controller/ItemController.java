package kea.dpang.item.controller;

import kea.dpang.item.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemController {

    /**
     * 상품 정보를 시스템에 추가합니다.
     *
     * @param itemCreateDto 상품 생성 정보
     */
    ResponseEntity<ItemResponseDto> createItem(ItemCreateDto itemCreateDto);

    /**
     * 상품 리스트를 조회합니다. (프론트엔드 용)
     *
     * @param pageable 페이지 정보
     */
    ResponseEntity<List<ItemThumbnailDto>> getItemListForFrontend(Pageable pageable);

    /**
     * 상품 리스트를 조회합니다. (백엔드 서비스 용)
     *
     * @param itemIdList 상품 ID 리스트
     */
    ResponseEntity<List<ItemThumbnailDto>> getItemListForBackend(List<Long> itemIdList);

    /**
     * 상품 ID를 통해 상세한 상품 정보를 조회합니다.
     *
     * @param itemId 상품 ID
     */
    ResponseEntity<ItemResponseDto> getItem(Long itemId);

    /**
     * 인기 상품을 조회합니다.
     *
     * @param itemIdList 인기 상품 ID 리스트
     * @param pageable   페이지 정보
     */
    ResponseEntity<List<PopularItemDto>> getPopularItems(List<Long> itemIdList, Pageable pageable);

    /**
     * 기존 상품 정보를 업데이트합니다.
     *
     * @param itemId        상품 ID
     * @param itemUpdateDto 수정할 상품 정보
     */
    ResponseEntity<ItemResponseDto> updateItem(Long itemId, ItemUpdateDto itemUpdateDto);

    /**
     * 상품 정보를 시스템에서 제거합니다.
     *
     * @param itemId 상품 ID
     */
    ResponseEntity<Void> deleteItem(Long itemId);
}
