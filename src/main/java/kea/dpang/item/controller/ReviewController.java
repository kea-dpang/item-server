package kea.dpang.item.controller;

import kea.dpang.item.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface ReviewController {

    /**
     * 리뷰 정보를 시스템에 추가합니다.
     *
     * @param reviewCreateDto 리뷰 생성 요청 데이터
     * @return 생성된 리뷰 정보
     */
    ResponseEntity<ReviewResponseDto> createReview(ReviewCreateDto reviewCreateDto);

    /**
     * 리뷰를 조회합니다.
     *
     * @param reviewId 조회할 리뷰의 ID
     * @return 조회된 리뷰 정보
     */
    ResponseEntity<ReviewResponseDto> getReview(Long reviewId);

    /**
     * 기존 리뷰 정보를 업데이트합니다.
     *
     * @param reviewUpdateDto 리뷰 수정 요청 데이터
     * @return 수정된 리뷰 정보
     */
    ResponseEntity<ReviewResponseDto> updateReview(Long reviewId, ReviewUpdateDto reviewUpdateDto);

    /**
     * 리뷰 정보를 시스템에서 제거합니다.
     *
     * @param reviewId 삭제할 리뷰의 ID
     */
    ResponseEntity<Void> deleteReview(Long reviewId);
}

