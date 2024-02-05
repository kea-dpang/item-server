package kea.dpang.item.service;

import kea.dpang.item.base.SuccessResponse;
import kea.dpang.item.dto.review.CreateReviewRequestDto;
import kea.dpang.item.dto.review.PersonalReviewDto;
import kea.dpang.item.dto.review.ReviewDto;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Review;
import kea.dpang.item.exception.ItemNotFoundException;
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
import java.util.Optional;

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
        // 상품 조회
        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new ItemNotFoundException(dto.getItemId()));

        // 리뷰 생성
        Review review = Review.from(dto, item);

        // 리뷰 저장
        reviewRepository.save(review);
        log.info("새로운 리뷰 등록 완료. 리뷰 ID: {}", review.getId());
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
        // 사용자 서버로부터 사용자 정보 조회
        ResponseEntity<SuccessResponse<UserDetailDto>> responseEntity = userServiceFeignClient.getReviewer(reviewerId);

        // 사용자 이름 가져오기
        String name = Optional.ofNullable(responseEntity.getBody())
                .map(SuccessResponse::getData)
                .map(UserDetailDto::getName)
                .orElseThrow(() -> new RuntimeException("사용자 정보 조회에 실패하였습니다."));

        // 사용자 리뷰 리스트 조회
        Page<Review> reviews = reviewRepository.findByReviewerId(reviewerId, pageable);

        // 리뷰 리스트를 PersonalReviewDto로 변환
        return reviews.getContent().stream()
                .map(review -> PersonalReviewDto.of(review, name))
                .toList();
    }

}
