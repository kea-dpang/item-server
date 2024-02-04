package kea.dpang.item.service;

import kea.dpang.item.base.SuccessResponse;
import kea.dpang.item.dto.review.ReviewCreateDto;
import kea.dpang.item.dto.review.ReviewPersonalListDto;
import kea.dpang.item.dto.review.ReviewResponseDto;
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
import java.util.stream.Collectors;

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
    public void createReview(ReviewCreateDto dto) {
        Review review = Review.from(dto, itemRepository);
        new ReviewResponseDto(reviewRepository.save(review));
        log.info("새로운 리뷰 등록 완료. 리뷰 ID: {}", review.getReviewId());
    }

    // 상품별 리뷰 리스트 조회
    @Override
    @Transactional
    public List<ReviewResponseDto> getReviewList(Long itemId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findByItemIdItemId(itemId, pageable);
        return reviews.stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    // 사용자별 리뷰 리스트 조회
    @Override
    @Transactional
    public List<ReviewPersonalListDto> getReviewPersonalList(Long reviewerId, Pageable pageable) {
        ResponseEntity<SuccessResponse<UserDetailDto>> responseEntity = userServiceFeignClient.getReviewer(reviewerId);
        String name = responseEntity.getBody().getData().getName();
        Page<Review> reviews = reviewRepository.findByReviewerId(reviewerId, pageable);
        return reviews.stream()
                .map(review -> new ReviewPersonalListDto(review, name))
                .collect(Collectors.toList());
    }


//    // 리뷰 조회
//    @Override
//    @Transactional(readOnly = true)
//    public ReviewResponseDto getReview(Long reviewId) {
//        return reviewRepository.findById(reviewId)
//                .map(ReviewResponseDto::new)
//                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
//    }

//    // 리뷰 수정
//    @Override
//    @Transactional
//    public ReviewResponseDto updateReview(Long reviewId, ReviewUpdateDto dto) {
//        review review = reviewRepository.findById(reviewId)
//                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
//
//        review.updateInformation(dto);
//        return new ReviewResponseDto(reviewRepository.save(review));
//    }

//    // 리뷰 삭제
//    @Override
//    @Transactional
//    public void deleteReview(Long reviewId) {
//        review review = reviewRepository.findById(reviewId)
//                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
//        reviewRepository.delete(review);
//    }

}
