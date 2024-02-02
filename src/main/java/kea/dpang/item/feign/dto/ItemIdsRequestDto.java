package kea.dpang.item.feign.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemIdsRequestDto {
    private List<Long> itemIds;
}
