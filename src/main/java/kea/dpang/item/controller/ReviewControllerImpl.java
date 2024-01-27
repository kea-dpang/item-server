package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import kea.dpang.item.dto.*;
import kea.dpang.item.service.ReviewServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
@Slf4j
public class ReviewControllerImpl implements ReviewController {

    private final ReviewServiceImpl reviewService;

    @Override
    @PostMapping
    @Operation(summary = "리뷰 등록", description = "리뷰 정보를 시스템에 추가합니다.")
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewCreateDto reviewCreateDto) {
        ReviewResponseDto review = reviewService.createReview(reviewCreateDto);
        log.info("새로운 리뷰 등록 완료. 리뷰 ID: {}", review.getReviewId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{reviewId}")
                .buildAndExpand(review.getReviewId())
                .toUri();

        return ResponseEntity.created(location).body(review);
    }

    public ResponseEntity<ItemResponseDto> createItem(@RequestBody ItemCreateDto itemCreateDto) {
        return null;
    }
    @Override
    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 조회", description = "리뷰를 조회합니다.")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable Long reviewId) {
        ReviewResponseDto review = reviewService.getReview(reviewId);
        log.info("리뷰 정보 조회 완료. 리뷰 ID: {}", review.getReviewId());

        return ResponseEntity.ok(review);
    }

    @Override
    @PutMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "기존 리뷰 정보를 업데이트합니다.")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable Long reviewId, @RequestBody ReviewUpdateDto reviewUpdateDto) {
        ReviewResponseDto review = reviewService.updateReview(reviewId, reviewUpdateDto);
        log.info("리뷰 정보 업데이트 완료. 리뷰 ID: {}", review.getReviewId());

        return ResponseEntity.ok(review);
    }

    @Override
    @Operation(summary = "리뷰 삭제", description = "리뷰 정보를 시스템에서 제거합니다.")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        log.info("리뷰 삭제 완료. 리뷰 ID: {}", reviewId);

        return ResponseEntity.noContent().build();
    }
}
