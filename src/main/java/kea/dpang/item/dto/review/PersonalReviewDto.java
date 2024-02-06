package kea.dpang.item.dto.review;

import kea.dpang.item.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PersonalReviewDto {

    private Long reviewId;
    private Long reviewerId;
    private String reviewerName;
    private LocalDateTime createdTime;
    private Long itemId;
    private String itemImage;
    private String content;
    private Double rating;

    public static PersonalReviewDto of(Review review, String reviewerName) {
        return new PersonalReviewDto(
                review.getReviewId(),
                review.getReviewerId(),
                reviewerName,
                review.getCreatedTime(),
                review.getItem().getId(),
                review.getItem().getThumbnailImage(),
                review.getContent(),
                review.getRating()
        );
    }
}
