package kea.dpang.item.service;

import kea.dpang.item.dto.ReviewCreateDto;
import kea.dpang.item.dto.ReviewResponseDto;
import kea.dpang.item.dto.ReviewUpdateDto;
import kea.dpang.item.entity.Review;

public interface ReviewService {
    /**
     * 주어진 ID에 해당하는 리뷰의 정보를 조회합니다.
     *
     * @param reviewId 조회할 리뷰의 ID
     * @return 조회된 리뷰의 정보가 담긴 Detail DTO
     */
    ReviewResponseDto getReview(Long reviewId);




    /**
     * 새로운 리뷰을 등록합니다.
     *
     * @param reviewCreateDto 등록할 리뷰의 정보가 담긴 DTO
     * @return 등록된 리뷰의 정보가 담긴 Detail DTO
     */
    ReviewResponseDto createReview(ReviewCreateDto reviewCreateDto);

    /**
     * 리뷰의 정보를 업데이트합니다.
     *
     * @param reviewId 업데이트할 리뷰의 ID
     * @param reviewUpdateDto 업데이트할 리뷰의 정보가 담긴 DTO
     * @return 업데이트된 리뷰의 정보가 담긴 Detail DTO
     */
    ReviewResponseDto updateReview(Long reviewId, ReviewUpdateDto reviewUpdateDto);

    /**
     * 주어진 ID에 해당하는 리뷰을 삭제합니다.
     *
     * @param reviewId 삭제할 리뷰의 ID
     */
    void deleteReview(Long reviewId);
}
