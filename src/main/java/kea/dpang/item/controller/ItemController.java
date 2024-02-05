package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kea.dpang.item.base.BaseResponse;
import kea.dpang.item.base.SuccessResponse;
import kea.dpang.item.dto.item.*;
import kea.dpang.item.dto.review.ReviewDto;
import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.SubCategory;
import kea.dpang.item.service.ItemService;
import kea.dpang.item.service.ReviewService;
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
@Tag(name = "item API", description = "상품 관련 API 입니다.")
@RequestMapping("/api/items")
@Slf4j
public class ItemController {

    private final ItemService itemService;

    private final ReviewService reviewService;

    @PostMapping
    @Operation(summary = "상품 등록", description = "상품 정보를 시스템에 추가합니다.")
    public ResponseEntity<BaseResponse> createItem(@RequestBody CreateItemRequestDto dto) {
        itemService.createItem(dto);

        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "상품이 등록되었습니다."),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(summary = "상품 리스트 조회", description = "상품 리스트를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<SuccessResponse<Page<ItemDetailDto>>> getItemList(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) SubCategory subCategory,
            @RequestParam(required = false, defaultValue = "0") Double minPrice,
            @RequestParam(required = false, defaultValue = "10000000") Double maxPrice,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long sellerId,
            Pageable pageable
    ) {
        // 카테고리 따로, 브랜드 따로. 같이 들어오는 경우는 없다 (by 프런트 유지연)

        Page<ItemDetailDto> items = itemService.getItemList(category, subCategory, minPrice, maxPrice, keyword, sellerId, pageable);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 리스트가 조회되었습니다.", items),
                HttpStatus.OK
        );
    }

    @GetMapping("/list")
    @Operation(summary = "상품 리스트 조회", description = "상품 리스트를 리스트 정보에 따라 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ItemDto>>> getItemList(
            @RequestBody GetItemListRequestDto dto
    ) {
        List<ItemDto> itemList = itemService.getItemList(dto.getItemIds());

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 리스트가 조회되었습니다.", itemList),
                HttpStatus.OK
        );
    }


    @GetMapping("/{itemId}/detail")
    @Operation(summary = "상품 상세 정보 조회", description = "상품 ID를 통해 상세한 상품 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<ItemDetailDto>> getItem(
            @PathVariable @Parameter(description = "상품ID", example = "1") Long itemId
    ) {
        ItemDetailDto item = itemService.getItem(itemId);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 상세 정보가 조회되었습니다.", item),
                HttpStatus.OK
        );
    }

    @GetMapping("/{itemId}/reviews")
    @Operation(summary = "상품별 리뷰 리스트 조회", description = "상품별로 리뷰 리스트를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ReviewDto>>> getReviewList(
            @PathVariable @Parameter(description = "상품ID", example = "1") Long itemId,
            Pageable pageable
    ) {
        List<ReviewDto> reviews = reviewService.getReviewList(itemId, pageable);

        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품별 리뷰 리스트가 조회되었습니다.", reviews),
                HttpStatus.OK
        );
    }

    @GetMapping("/popular/list")
    @Operation(summary = "인기 상품 리스트 조회", description = "지정된 상품 ID 리스트에 대한 인기 상품 정보를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ItemDetailDto>>> getPopularItems(Pageable pageable) {
        // Todo: 인기 상품 리스트 조회 API 구현 필요
//        List<PopularItemDto> popularItems = itemService.getPopularItems();
//        log.info("인기 상품 목록 조회 완료. 조회된 인기 상품 수: {}", popularItems.size());
//        return new ResponseEntity<>(
//                new SuccessResponse<>(HttpStatus.OK.value(), "인기 상품 리스트가 조회되었습니다.", popularItems),
//                HttpStatus.OK
//        );
        return null;
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "상품 수정", description = "상품 ID에 해당하는 상품 정보를 수정합니다.")
    public ResponseEntity<BaseResponse> updateItem(
            @PathVariable @Parameter(description = "상품ID", example = "1") Long itemId, @RequestBody UpdateItemRequestDto dto
    ) {
        itemService.updateItem(itemId, dto);
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "상품이 수정되었습니다."),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping
    @Operation(summary = "상품 삭제", description = "상품 ID에 해당하는 상품 정보를 삭제합니다.")
    public ResponseEntity<BaseResponse> deleteItem(
            @RequestBody DeleteItemRequestDto dto
    ) {
        itemService.deleteItem(dto.getItemIds());

        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.NO_CONTENT.value(), "상품이 삭제되었습니다."),
                HttpStatus.NO_CONTENT
        );
    }


    @PutMapping("/stock")
    @Operation(summary = "재고 수량 변경", description = "재고 수량을 변경합니다.")
    public ResponseEntity<BaseResponse> changeStock(
            @RequestBody @Parameter(description = "재고 변경 정보") List<UpdateStockRequestDto> dto
    ) {
        itemService.changeStock(dto);

        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.OK.value(), "상품 재고 수량이 변경되었습니다."),
                HttpStatus.OK
        );
    }


}