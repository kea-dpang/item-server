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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        log.info("새로운 리뷰 등록 완료. 리뷰 ID: {}", review.getReviewId());
    }


    // 상품별 리뷰 리스트 조회
    @Override
    @Transactional
    public List<ReviewDto> getReviewList(Long itemId, Pageable pageable) {
        // 정렬 조건 추가
        Sort sort = Sort.by(Sort.Direction.DESC, "createdTime");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<Review> reviews = reviewRepository.findByItemId(itemId, pageable);

        return reviews.stream().map(review -> {
            // 작성자 이름 가져오기
            ResponseEntity<SuccessResponse<UserDetailDto>> response = userServiceFeignClient.getReviewer(review.getReviewerId());
            UserDetailDto userDetailDto = response.getBody().getData();
            String reviewerName = userDetailDto.getName();

            // ReviewDto 생성
            return new ReviewDto(
                    review.getReviewId(),
                    review.getReviewerId(),
                    reviewerName, // 작성자 이름 설정
                    review.getCreatedTime(),
                    review.getItem().getId(),
                    review.getContent(),
                    review.getRating()
            );
        }).toList();
    }


    // 사용자별 리뷰 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public Page<PersonalReviewDto> getPersonalReviewList(Long reviewerId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        log.info("리뷰 리스트 조회를 시작합니다 : 시작 날짜: {}, 종료 날짜: {}, 리뷰어 ID: {}, 페이지 요청 정보: {}", startDate, endDate, reviewerId, pageable);

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

        Page<Review> reviews = reviewRepository.findByReviewerIdAndCreatedTimeBetween(reviewerId, startDateTime, endDateTime, pageable);

        log.info("리뷰 목록 조회 완료. 조회된 리뷰 건수: {}", reviews.getTotalElements());

        // 리뷰 목록이 비어있으면 빈 페이지를 반환한다.
        if (reviews.isEmpty()) {
            return Page.empty(pageable);
        }

        // 사용자 이름 가져오기
        ResponseEntity<SuccessResponse<UserDetailDto>> responseEntity = userServiceFeignClient.getReviewer(reviewerId);
        String name = Optional.ofNullable(responseEntity.getBody())
                .map(SuccessResponse::getData)
                .map(UserDetailDto::getName)
                .orElseThrow(() -> new RuntimeException("사용자 정보 조회에 실패하였습니다."));

        return reviews.map(review -> PersonalReviewDto.of(review, name));
    }


}
