package kea.dpang.item.dto.review;

import kea.dpang.item.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewDto {

    private Long reviewId;
    private Long reviewerId;
    private String reviewerName;
    private LocalDateTime createdTime;
    private Long itemId;
    private String content;
    private Double rating;

    public static ReviewDto of(Review review, String reviewerName) {
        return new ReviewDto(
                review.getReviewId(),
                review.getReviewerId(),
                reviewerName,
                review.getCreatedTime(),
                review.getItem().getId(),
                review.getContent(),
                review.getRating()
        );
    }
}

