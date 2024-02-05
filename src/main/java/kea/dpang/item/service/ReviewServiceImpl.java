package kea.dpang.item.service;

import kea.dpang.item.base.SuccessResponse;
import kea.dpang.item.dto.review.CreateReviewRequestDto;
import kea.dpang.item.dto.review.PersonalReviewDto;
import kea.dpang.item.dto.review.ReviewDto;
import kea.dpang.item.entity.Review;
import kea.dpang.item.feign.UserServiceFeignClient;
import kea.dpang.item.feign.dto.UserDetailDto;
import kea.dpang.item.repository.ItemRepository;
import kea.dpang.item.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private final UserServiceFeignClient userServiceFeignClient;

    // 리뷰 등록
    @Override
    @Transactional
    public void createReview(CreateReviewRequestDto dto) {
        Review review = Review.from(dto, itemRepository);
        new ReviewDto(reviewRepository.save(review));
        log.info("새로운 리뷰 등록 완료. 리뷰 ID: {}", review.getReviewId());
    }

    // 상품별 리뷰 리스트 조회
    @Override
    @Transactional
    public List<ReviewDto> getReviewList(Long itemId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByItemIdItemId(itemId, pageable);
        return reviews.stream()
                .map(ReviewDto::new)
                .toList();
    }

    // 사용자별 리뷰 리스트 조회
    @Override
    @Transactional
    public List<PersonalReviewDto> getPersonalReviewList(Long reviewerId, Pageable pageable) {
        ResponseEntity<SuccessResponse<UserDetailDto>> responseEntity = userServiceFeignClient.getReviewer(reviewerId);
        String name = responseEntity.getBody().getData().getName();
        Page<Review> reviews = reviewRepository.findByReviewerId(reviewerId, pageable);
        return reviews.stream()
                .map(review -> new PersonalReviewDto(review, name))
                .toList();
    }

}
