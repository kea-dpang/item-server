package kea.dpang.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewResponseDto {
    private Long reviewId;
    private Long reviewerId;
    private Long itemId;
    private String content;
    private Double rating;
}

