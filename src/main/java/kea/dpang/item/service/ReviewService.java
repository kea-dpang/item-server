package kea.dpang.item.service;

import kea.dpang.item.dto.review.CreateReviewRequestDto;
import kea.dpang.item.dto.review.PersonalReviewDto;
import kea.dpang.item.dto.review.ReviewDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {
    /**
     * 새로운 리뷰을 등록합니다.
     *
     * @param createReviewRequestDto 등록할 리뷰의 정보가 담긴 DTO
     * @return 등록된 리뷰의 정보가 담긴 Detail DTO
     */
    void createReview(CreateReviewRequestDto createReviewRequestDto);

    /**
     * 상품별 리뷰을 조회합니다.
     *
     * @param pageable 페이지 정보
     * @param itemId   조회할 상품의 ID
     */
    List<ReviewDto> getReviewList(Long itemId, Pageable pageable);

    /**
     * 새로운 리뷰을 등록합니다.
     *
     * @param pageable   페이지 정보
     * @param reviewerId 조회할 리뷰 작성자(사용자)의 ID
     */
    List<PersonalReviewDto> getPersonalReviewList(Long reviewerId, Pageable pageable);

}
