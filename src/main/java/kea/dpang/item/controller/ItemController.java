package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kea.dpang.item.base.BaseResponse;
import kea.dpang.item.base.SuccessResponse;
import kea.dpang.item.dto.Item.ItemCreateDto;
import kea.dpang.item.dto.Item.ItemResponseDto;
import kea.dpang.item.dto.Item.ItemUpdateDto;
import kea.dpang.item.dto.Review.ReviewResponseDto;
import kea.dpang.item.dto.Stock.StockManageDto;
import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.SubCategory;
import kea.dpang.item.service.ItemServiceImpl;
import kea.dpang.item.service.ReviewServiceImpl;
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
@Tag(name = "Item API", description = "상품 관련 API 입니다.")
@RequestMapping("/api/items")
@Slf4j
public class ItemController {

    private final ItemServiceImpl itemService;
    private final ReviewServiceImpl reviewService;

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

    // Todo: 상품 조회 <- 필터링
    @GetMapping("/list")
    @Operation(summary = "상품 리스트 조회", description = "상품 리스트를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<SuccessResponse<Page<ItemResponseDto>>> getItemList(
            @RequestParam(defaultValue = "전체") Category category,
            @RequestParam(defaultValue = "전체") SubCategory subCategory,
            @RequestParam(defaultValue = "0") Double minPrice,
            @RequestParam(defaultValue = "10000000") Double maxPrice,
            @RequestParam(defaultValue = "") String keyword,
            // Todo: 판매처 필터링
            Pageable pageable
    ) {
        // TODO("상품 리스트 조회 API 구현 필요");
//        Page<ItemResponseDto> items = itemService.getItemList(pageable);
//        log.info("상품 리스트 조회 완료. 페이지 번호: {}", pageable.getPageNumber());
//        return new ResponseEntity<>(
//                new SuccessResponse<>(HttpStatus.OK.value(), "상품 리스트가 조회되었습니다.", items),
//                HttpStatus.OK
//        );
        return null;
    }


    @GetMapping("/{itemId}")
    @Operation(summary = "상품 상세 정보 조회", description = "상품 ID를 통해 상세한 상품 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<ItemResponseDto>> getItem(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId) {
        ItemResponseDto item = itemService.getItem(itemId);
        log.info("상품 상세 정보 조회 완료. 상품 ID: {}", item.getItemId());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품 상세 정보가 조회되었습니다.", item),
                HttpStatus.OK

        );
    }

    @GetMapping("/{itemId}/reviews")
    @Operation(summary = "상품별 리뷰 리스트 조회", description = "상품별로 리뷰 리스트를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ReviewResponseDto>>> getReviewList(@PathVariable @Parameter(description = "상품ID", example = "1") Long itemId, Pageable pageable) {
        List<ReviewResponseDto> reviews = reviewService.getReviewList(itemId, pageable);
        log.info("상품별 리뷰 리스트 조회 완료. 상품 ID: {}, 페이지 번호: {}", itemId, pageable.getPageNumber());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "상품별 리뷰 리스트가 조회되었습니다.", reviews),
                HttpStatus.OK
        );
    }

    @GetMapping("/popular/list")
    @Operation(summary = "인기 상품 리스트 조회", description = "지정된 상품 ID 리스트에 대한 인기 상품 정보를 페이지 정보에 따라 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ItemResponseDto>>> getPopularItems(Pageable pageable) {
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


    // Todo: List로 변경됨에 따라 DTO 및 URL 수정 필요
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

}