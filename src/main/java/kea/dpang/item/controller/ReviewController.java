package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import kea.dpang.item.base.*;
import kea.dpang.item.dto.Review.ReviewCreateDto;
import kea.dpang.item.dto.Review.ReviewPersonalListDto;
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
@Tag(name="Review API", description = "리뷰 관련 API 입니다.")
@RequestMapping("/api/reviews")
@Slf4j
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @PostMapping
    @Operation(summary = "리뷰 등록", description = "리뷰 정보를 시스템에 추가합니다.")
    public ResponseEntity<BaseResponse> createReview(@RequestBody ReviewCreateDto reviewCreateDto) {
        reviewService.createReview(reviewCreateDto);
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "리뷰가 등록되었습니다."),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{reviewerId}/reviewlist")
    @Operation(summary = "사용자별 리뷰 리스트 조회", description = "사용자 정보에 따라 리뷰 리스트를 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ReviewPersonalListDto>>> getReviewPersonalList(@PathVariable @Parameter(description = "리뷰 작성자 ID", example = "1") Long reviewerId, Pageable pageable) {
        List<ReviewPersonalListDto> reviews = reviewService.getReviewPersonalList(reviewerId, pageable);
        log.info("리뷰 리스트 조회 완료. 작성자 ID: {}, 페이지 번호: {}", reviewerId, pageable.getPageNumber());
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "리뷰 리스트가 조회되었습니다.", reviews),
                HttpStatus.OK
        );
    }

//    @GetMapping("/{reviewId}")
//    @Operation(summary = "상품별 리뷰 조회", description = "리뷰를 조회합니다.")
//    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable @Parameter(description = "리뷰ID", example = "1") Long reviewId) {
//        ReviewResponseDto review = reviewService.getReview(reviewId);
//        log.info("리뷰 정보 조회 완료. 리뷰 ID: {}", review.getReviewId());
//
//        return ResponseEntity.ok(review);
//    }

//    @PutMapping("/{reviewId}")
//    @Operation(summary = "리뷰 수정", description = "기존 리뷰 정보를 업데이트합니다.")
//    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable @Parameter(description = "리뷰ID", example = "1") Long reviewId, @RequestBody ReviewUpdateDto reviewUpdateDto) {
//        ReviewResponseDto review = reviewService.updateReview(reviewId, reviewUpdateDto);
//        log.info("리뷰 정보 업데이트 완료. 리뷰 ID: {}", review.getReviewId());
//
//        return ResponseEntity.ok(review);
//    }

//    @DeleteMapping("/{reviewId}")
//    @Operation(summary = "리뷰 삭제", description = "리뷰 정보를 시스템에서 제거합니다.")
//    public ResponseEntity<Void> deleteReview(@PathVariable @Parameter(description = "리뷰ID", example = "1") Long reviewId) {
//        reviewService.deleteReview(reviewId);
//        log.info("리뷰 삭제 완료. 리뷰 ID: {}", reviewId);
//
//        return ResponseEntity.noContent().build();
//    }
}
