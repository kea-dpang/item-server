package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import kea.dpang.item.base.*;
import kea.dpang.item.dto.*;
import kea.dpang.item.service.ItemServiceImpl;
import kea.dpang.item.service.ReviewServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="Item API", description = "상품 관련 API 입니다.")
@RequestMapping("/api/items")
@Slf4j
public class ItemController {

    private final ItemServiceImpl itemService;
    private final ReviewServiceImpl reviewService;

    @PostMapping
    @Operation(summary = "상품 등록", description = "상품 정보를 시스템에 추가합니다.")
    public ResponseEntity<BaseResponse> createItem(@RequestBody ItemCreateDto itemCreateDto) {
        ItemResponseDto item = itemService.createItem(itemCreateDto);
        log.info("새로운 상품 등록 완료. 상품 ID: {}", item.getItemId());
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "상품이 등록되었습니다."),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/cardlist")
    @Operation(summary = "상품 카드 리스트 조회", description = "페이지 정보에 따라 상품 카드 리스트를 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ItemCardDto>>> getItemCard(@RequestParam Pageable pageable) {
        List<ItemCardDto> items = itemService.getItemCard(pageable);
        log.info("상품 카드 리스트 조회 완료. 페이지: {}", pageable.getPageNumber());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 카드 리스트가 조회되었습니다.", items),
                HttpStatus.OK
        );
    }

//    @GetMapping("/list")
//    @Operation(summary = "백엔드용")
//    public ResponseEntity<List<ItemSimpleBackendDto>> getItemListForBackend(@RequestBody List<Long> itemId) {
//        List<ItemSimpleBackendDto> items = itemService.getItemListForBackend();
//        return ResponseEntity.ok(items);
//    }

    @GetMapping("/managelist")
    @Operation(summary = "상품 관리 리스트 조회", description = "페이지 정보에 따라 관리자용 상품 리스트를 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ItemManageListDto>>> getItemManageList(@RequestParam Pageable pageable) {
        List<ItemManageListDto> items = itemService.getItemManageList(pageable);
        log.info("상품 관리 리스트 조회 완료. 페이지: {}", pageable.getPageNumber());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 관리 리스트가 조회되었습니다.", items),
                HttpStatus.OK
        );
    }

    @GetMapping("/{itemId}")
    @Operation(summary = "상품 상세 정보 조회", description = "상품 ID를 통해 상세한 상품 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<ItemResponseDto>> getItem(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId) {
        ItemResponseDto item = itemService.getItem(itemId);
        log.info("상품 상세 정보 조회 완료. 상품 ID: {}", item.getItemId());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(),"상품 상세 정보가 조회되었습니다.", item),
                HttpStatus.OK
        );
    }

    @GetMapping("/popular")
    @Operation(summary = "인기 상품 리스트 조회", description = "지정된 상품 ID 리스트에 대한 인기 상품 정보를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<SuccessResponse<List<PopularItemDto>>> getPopularItems(@RequestParam List<Long> itemIdList, Pageable pageable) {
        List<PopularItemDto> popularItems = itemService.getPopularItems();
        log.info("인기 상품 목록 조회 완료. 조회된 인기 상품 수: {}", popularItems.size());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(),"인기 상품 리스트가 조회되었습니다.", popularItems),
                HttpStatus.OK
        );
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "상품 수정", description = "상품 ID에 해당하는 상품 정보를 수정합니다.")
    public ResponseEntity<BaseResponse> updateItem(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId, @RequestBody ItemUpdateDto itemUpdateDto) {
        ItemResponseDto item = itemService.updateItem(itemId, itemUpdateDto);
        log.info("상품 정보 업데이트 완료. 상품 ID: {}", item.getItemId());
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "상품이 수정되었습니다."),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{itemIds}")
    @Operation(summary = "상품 삭제", description = "상품 ID에 해당하는 상품 정보를 삭제합니다.")
    public ResponseEntity<BaseResponse> deleteItem(@PathVariable @Parameter(description = "상품ID", example = "1") List<Long> itemIds) {
        itemService.deleteItem(itemIds);
        log.info("상품 정보 삭제 완료. 상품 ID 리스트: {}", itemIds);
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.NO_CONTENT.value(), "상품이 삭제되었습니다."),
                HttpStatus.NO_CONTENT
        );
    }

    @GetMapping("/{itemId}/reviews")
    @Operation(summary = "상품별 리뷰 리스트 조회", description = "상품별로 페이지 정보에 따라 리뷰 리스트를 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ReviewResponseDto>>> getReviewList(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId, Pageable pageable) {
        List<ReviewResponseDto> reviews = reviewService.getReviewList(itemId, pageable);
        log.info("상품별 리뷰 리스트 조회 완료. 상품 ID: {}, 페이지 번호: {}", itemId, pageable.getPageNumber());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품별 리뷰 리스트가 조회되었습니다.", reviews),
                HttpStatus.OK
        );
    }

    @GetMapping("/{itemId}/stock")
    @Operation(summary = "재고 수량 조회", description = "재고 수량을 조회합니다.")
    public ResponseEntity<SuccessResponse<Integer>> getStockQuantity(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId) {
        int stockQuantity = itemService.getStockQuantity(itemId);
        log.info("재고 수량 조회 완료. 상품 ID: {}", itemId);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 재고 수량이 조회되었습니다.", stockQuantity),
                HttpStatus.OK
        );
    }

    @PutMapping("/{itemId}/increase/{quantity}")
    @Operation(summary = "재고 수량 증가", description = "재고 수량을 증가시킵니다.")
    public ResponseEntity<SuccessResponse<Integer>> increaseStock(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId, @PathVariable @Parameter(description = "재고 수량 입력", example = "100") int quantity) {
        int increaseStock = itemService.increaseStock(itemId, quantity);
        log.info("재고 수량 증가 완료. 상품 ID: {}", itemId);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 재고 수량이 증가되었습니다.", increaseStock),
                HttpStatus.OK
        );
    }

    @PutMapping("/{itemId}/decrease/{quantity}")
    @Operation(summary = "재고 수량 감소", description = "재고 수량을 감소시킵니다.")
    public ResponseEntity<SuccessResponse<Integer>> decreaseStock(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId, @PathVariable @Parameter(description = "재고 수량 입력", example = "100") int quantity) {
        int decreaseStock = itemService.decreaseStock(itemId, quantity);
        log.info("재고 수량 감소 완료. 상품 ID: {}", itemId);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 재고 수량이 조회되었습니다.", decreaseStock),
                HttpStatus.OK
        );
    }

    /* feign */
    // event-server
    @GetMapping("/findName")
    @Operation(summary = "이벤트쪽 상품명 조회", description = "이벤트에 들어갈 상품명을 조회합니다.")
    public ResponseEntity<SuccessResponse<String>> getEventItemName(@RequestParam Long itemId) {
        String itemName = itemService.getItemName(itemId);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품명이 조회되었습니다.", itemName),
                HttpStatus.OK
        );
    }
}