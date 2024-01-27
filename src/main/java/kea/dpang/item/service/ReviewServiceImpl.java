package kea.dpang.item.service;

import kea.dpang.item.dto.*;
import kea.dpang.item.entity.Review;

import kea.dpang.item.exception.ReviewNotFoundException;
import kea.dpang.item.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 등록
    @Override
    @Transactional
    public ReviewResponseDto createReview(ReviewCreateDto dto) {
        Review review = Review.from(dto);
        return new ReviewResponseDto(reviewRepository.save(review));
    }

    // 리뷰 조회
    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDto getReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .map(ReviewResponseDto::new)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    // 리뷰 수정
    @Override
    @Transactional
    public ReviewResponseDto updateReview(Long reviewId, ReviewUpdateDto dto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));

        review.updateInformation(dto);
        return new ReviewResponseDto(reviewRepository.save(review));
    }

    // 리뷰 삭제
    @Override
    @Transactional
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(reviewId));
        reviewRepository.delete(review);
    }

}
