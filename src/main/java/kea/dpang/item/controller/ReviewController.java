package kea.dpang.item.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kea.dpang.item.base.BaseResponse;
import kea.dpang.item.base.SuccessResponse;
import kea.dpang.item.dto.review.ReviewCreateDto;
import kea.dpang.item.dto.review.ReviewPersonalListDto;
import kea.dpang.item.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "review API", description = "리뷰 관련 API 입니다.")
@RequestMapping("/api")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    @Operation(summary = "리뷰 등록", description = "리뷰 정보를 시스템에 추가합니다.")
    public ResponseEntity<BaseResponse> createReview(@RequestBody ReviewCreateDto reviewCreateDto) {
        reviewService.createReview(reviewCreateDto);
        return new ResponseEntity<>(
                new BaseResponse(HttpStatus.CREATED.value(), "리뷰가 등록되었습니다."),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/reviewers/{reviewerId}/reviews")
    @Operation(summary = "사용자별 리뷰 리스트 조회", description = "사용자 정보에 따라 리뷰 리스트를 조회합니다.")
    public ResponseEntity<SuccessResponse<List<ReviewPersonalListDto>>> getReviewPersonalList(@PathVariable @Parameter(description = "리뷰 작성자 ID", example = "1") Long reviewerId, Pageable pageable) {
        List<ReviewPersonalListDto> reviews = reviewService.getReviewPersonalList(reviewerId, pageable);
        return new ResponseEntity<>(
                new SuccessResponse<>(HttpStatus.OK.value(), "리뷰 리스트가 조회되었습니다.", reviews),
                HttpStatus.OK
        );
    }

}
