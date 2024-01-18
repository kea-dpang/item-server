package kea.dpang.item.service;

import kea.dpang.item.dto.*;
import kea.dpang.item.entity.Item;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.repository.ItemRepository;
import kea.dpang.item.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;
    private static final String ITEM_VIEW_COUNT_KEY = "item:viewCount";
    private final StringRedisTemplate redisTemplate;

    // 상품 등록
    @Override
    @Transactional
    public ItemDetailDto createItem(CreateItemDto dto) {

        Item item = Item.builder()
                .itemName(dto.getItemName())
                .category(dto.getCategory())
                .subCategory(dto.getSubCategory())
                .itemPrice(dto.getItemPrice())
                .discountPrice(dto.getDiscountPrice())
                .eventPrice(dto.getEventPrice())
                .vendor(dto.getVendor())
                .tags(dto.getTags())
                .brand(dto.getBrand())
                .minStock(dto.getMinStock())
                .maxStock(dto.getMaxStock())
                .itemImage(dto.getItemImage())
                .images(dto.getImages())
                .build();

        return new ItemDetailDto(itemRepository.save(item));
    }

    // 상품 조회
    @Override
    @Transactional(readOnly = true)
    public ItemDetailDto getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .map(ItemDetailDto::new)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    @Override
    @Transactional
    public ItemDetailDto getPopularItems(Long itemId, Double score) {
        Set<ZSetOperations.TypedTuple<String>> items = redisTemplate.opsForZSet().reverseRangeWithScores(ITEM_VIEW_COUNT_KEY, 0, -1);

        List<PopularItemDto> popularItems = new ArrayList<>();
        if (items != null) {
            for (ZSetOperations.TypedTuple<String> item : items) {
                itemId = Long.valueOf(item.getValue());
                score = item.getScore();

                // Placeholder for getting the item name (e.g., querying the database)
                String itemName = "Item " + itemId;

                popularItems.add(new PopularItemDto(itemId, itemName, score));
            }
        }
        Long finalItemId = itemId;
        return itemRepository.findById(itemId)
                .map(ItemDetailDto::new)
                .orElseThrow(() -> new ItemNotFoundException(finalItemId));
    }

    // 상품 수정
    @Override
    @Transactional
    public ItemDetailDto updateItem(Long itemId, UpdateItemDto dto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        item.updateInformation(dto.getItemName(), dto.getCategory(), dto.getSubCategory(), dto.getItemPrice(), dto.getDiscountPrice(), dto.getEventPrice(), dto.getVendor(), dto.getTags(), dto.getMinStock(), dto.getMaxStock(), dto.getItemImage(), dto.getImages());
        return new ItemDetailDto(itemRepository.save(item));
    }

    // 상품 삭제
    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ItemNotFoundException(itemId);
        }
        itemRepository.deleteById(itemId);
    }
}

//    @Override
//    @Transactional
//    public ItemThumbnailDto convertToItemThumbnailDTO(Item item) {
//        List<Review> reviews = reviewRepository.findByItemId(item.getItemId());
//
//        return new ItemThumbnailDto(
//                item.getItemId(),
//                item.getItemName(),
//                item.getBrand(),
//                item.getItemPrice(),
//                item.getDiscountPrice(),
//                item.getWishlistCheck(),
//                item.getItemImage(),
//                item.getNumberReview()
//        );
//    }
