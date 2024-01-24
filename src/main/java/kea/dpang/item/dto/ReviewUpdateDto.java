package kea.dpang.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewUpdateDto {
    private Long reviewId;
    private String content;
    private Double rating;
}