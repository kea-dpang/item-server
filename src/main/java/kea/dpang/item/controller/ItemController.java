package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import kea.dpang.item.base.*;
import kea.dpang.item.dto.Item.*;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.SubCategory;
import kea.dpang.item.feign.dto.ItemInquiryDto;
import kea.dpang.item.dto.Stock.StockManageDto;
import kea.dpang.item.feign.dto.ItemSimpleListDto;
import kea.dpang.item.service.ItemServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
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

    @PostMapping
    @Operation(summary = "상품 등록", description = "상품 정보를 시스템에 추가합니다.")
    public ResponseEntity<BaseResponse> createItem(@RequestBody ItemCreateDto itemCreateDto) {
        itemService.createItem(itemCreateDto);
        log.info("새로운 상품 등록 완료.");
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "상품이 등록되었습니다."),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/card/list")
    @Operation(summary = "상품 카드 리스트 조회", description = "페이지 정보에 따라 상품 카드 리스트를 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ItemCardDto>>> getItemCard(Pageable pageable) {
        List<ItemCardDto> items = itemService.getItemCard(pageable);
        log.info("상품 카드 리스트 조회 완료. 페이지: {}", pageable.getPageNumber());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 카드 리스트가 조회되었습니다.", items),
                HttpStatus.OK
        );
    }

    @GetMapping("/manage/list")
    @Operation(summary = "상품 관리 리스트 조회", description = "페이지 정보에 따라 관리자용 상품 리스트를 조회합니다.")
    public ResponseEntity<SuccessResponse<Page<ItemManageListDto>>> getItemManageList(Pageable pageable) {
        Page<ItemManageListDto> items = itemService.getItemManageList(pageable);
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

//    @GetMapping("/{itemId}/reviews")
//    @Operation(summary = "상품별 리뷰 리스트 조회", description = "상품별로 리뷰 리스트를 페이지 정보에 따라 조회합니다.")
//    public ResponseEntity<SuccessResponse<List<ReviewResponseDto>>> getReviewList(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId, Pageable pageable) {
//        List<ReviewResponseDto> reviews = reviewService.getReviewList(itemId, pageable);
//        log.info("상품별 리뷰 리스트 조회 완료. 상품 ID: {}, 페이지 번호: {}", itemId, pageable.getPageNumber());
//        return new ResponseEntity<>(
//                new SuccessResponse<>(HttpStatus.OK.value(), "상품별 리뷰 리스트가 조회되었습니다.", reviews),
//                HttpStatus.OK
//        );
//    }

    @GetMapping("/popular/list")
    @Operation(summary = "인기 상품 리스트 조회", description = "지정된 상품 ID 리스트에 대한 인기 상품 정보를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<SuccessResponse<List<PopularItemDto>>> getPopularItems(@RequestParam List<Long> itemIdList, Pageable pageable) {
        List<PopularItemDto> popularItems = itemService.getPopularItems();
        log.info("인기 상품 목록 조회 완료. 조회된 인기 상품 수: {}", popularItems.size());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(),"인기 상품 리스트가 조회되었습니다.", popularItems),
                HttpStatus.OK
        );
    }

    @GetMapping("/filter")
    @Operation(summary = "상품 필터링 조회", description = "주어진 필터 조건에 따라 상품을 조회합니다.")
    public ResponseEntity<SuccessResponse<Page<ItemCardDto>>> filterItems(
            @RequestParam(defaultValue = "전체") Category category,
            @RequestParam(defaultValue = "전체") SubCategory subCategory,
            @RequestParam(defaultValue = "전체") List<String> sellerNames,
            @RequestParam(defaultValue = "0") Double minPrice,
            @RequestParam(defaultValue = "10000000") Double maxPrice,
            @RequestParam(defaultValue = "") String keyword,
            Pageable pageable) {
        Page<ItemCardDto> items = itemService.filterItems(category, subCategory, sellerNames, minPrice, maxPrice, keyword, pageable);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 필터링이 완료되었습니다.", items),
                HttpStatus.OK
        );
    }

    @PutMapping("/{itemId}/update")
    @Operation(summary = "상품 수정", description = "상품 ID에 해당하는 상품 정보를 수정합니다.")
    public ResponseEntity<BaseResponse> updateItem(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId, @RequestBody ItemUpdateDto itemUpdateDto) {
        itemService.updateItem(itemId, itemUpdateDto);
        log.info("상품 정보 업데이트 완료");
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "상품이 수정되었습니다."),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping
    @Operation(summary = "상품 삭제", description = "상품 ID에 해당하는 상품 정보를 삭제합니다.")
    public ResponseEntity<BaseResponse> deleteItem(@RequestBody @Parameter(description = "상품ID", example = "1") List<Long> itemId) {
        itemService.deleteItem(itemId);
        log.info("상품 정보 삭제 완료. 상품 ID 리스트: {}", itemId);
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.NO_CONTENT.value(), "상품이 삭제되었습니다."),
                HttpStatus.NO_CONTENT
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

    @PutMapping("/{itemId}/stock/{quantity}/update")
    @Operation(summary = "재고 수량 변경", description = "재고 수량을 변경합니다.")
    public ResponseEntity<SuccessResponse<StockManageDto>> changeStock(
            @PathVariable @Parameter(description = "상품ID", example = "1") Long itemId,
            @PathVariable @Parameter(description = "재고 수량 입력", example = "100") int quantity
    ) {
        StockManageDto stockManageDto = itemService.changeStock(itemId, quantity);
        log.info("재고 수량 변경 완료. 상품 ID: {}", itemId);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 재고 수량이 변경되었습니다.", stockManageDto),
                HttpStatus.OK
        );
    }

    /* feign */
    // 이벤트(Event)
    @GetMapping("/findName")
    @Operation(summary = "(BE) 이벤트 상품명 조회", description = "이벤트에 들어갈 상품명을 조회합니다.")
    public ResponseEntity<SuccessResponse<String>> getEventItemName(@RequestParam Long itemId) {
        String itemName = itemService.getItemName(itemId);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품명이 조회되었습니다.", itemName),
                HttpStatus.OK
        );
    }

    // 주문(Order)
    @GetMapping("/inquiryItem")
    @Operation(summary = "(BE) 상품 정보 조회", description = "상품 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<ItemInquiryDto>> getInquiryItem(@RequestParam Long itemId) {
        Item item = itemService.getItemInquiry(itemId);
        ItemInquiryDto itemInquiryDto = new ItemInquiryDto(item);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품명이 조회되었습니다.", itemInquiryDto),
                HttpStatus.OK
        );
    }

    // 유저(User) - 장바구니 및 위시리스트에 전달할 상품 정보 리스트
    @GetMapping("/cart/inquiryItem")
    @Operation(summary = "(BE) 상품 요약 정보 조회", description = "상품의 일부 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ItemSimpleListDto>>> getCartInquiryItem(@RequestParam List<Long> itemIds) {
        List<ItemSimpleListDto> data = itemService.getCartItemsInquiry(itemIds);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 목록 전달 성공", data),
                HttpStatus.OK
        );
    }

}