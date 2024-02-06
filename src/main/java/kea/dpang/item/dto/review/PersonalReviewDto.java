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
    private Long itemId;
    private String itemName;
    private String itemThumbnailImage;
    private String content;
    private Double rating;
    private LocalDateTime createdTime;

    public static PersonalReviewDto of(Review review, String reviewerName) {
        return new PersonalReviewDto(
                review.getReviewId(),
                review.getReviewerId(),
                reviewerName,
                review.getItem().getId(),
                review.getItem().getName(),
                review.getItem().getThumbnailImage(),
                review.getContent(),
                review.getRating(),
                review.getCreatedTime()
        );
    }
}
