package kea.dpang.item.service;

import kea.dpang.item.dto.*;
import kea.dpang.item.entity.Item;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.repository.ItemRepository;
import kea.dpang.item.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Item item = Item.from(dto);
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

    // 상품 리스트 조회
    @Override
    public List<ItemThumbnailDto> getItemList() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(ItemThumbnailDto::new)
                .collect(Collectors.toList());
    }

    // 상품 조회수 증가
    @Override
    @Transactional
    public void incrementItemViewCount(Long itemId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.increment(ITEM_VIEW_COUNT_KEY + ":" + itemId);
    }

    // 인기 상품 조회
    @Override
    @Transactional(readOnly = true)
    public List<PopularItemDto> getPopularItems() {
        // Redis에서 조회수를 기준으로 인기 상품 ID와 점수를 가져옴.
        Set<ZSetOperations.TypedTuple<String>> items = redisTemplate.opsForZSet()
                .reverseRangeWithScores(ITEM_VIEW_COUNT_KEY, 0, -1);

        // 가져온 데이터를 PopularItemDto 리스트로 변환.
        return items.stream().map(item -> {
            Long itemId = Long.valueOf(item.getValue());
            Double score = item.getScore();
            String itemName = "Item " + itemId;

            return new PopularItemDto(itemId, itemName, score);
        }).collect(Collectors.toList());
    }

    // 상품 수정
    @Override
    @Transactional
    public ItemDetailDto updateItem(Long itemId, UpdateItemDto dto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        item.updateInformation(dto);
        return new ItemDetailDto(itemRepository.save(item));
    }

    // 상품 삭제
    @Override
    @Transactional
    public void deleteItem(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        itemRepository.delete(item);
    }
  
    // =============================카트 관련 기능===============================

    // 상품 정보 목록 조회
    @Override
    public List<Item> getCartItems(List<Long> itemId) {
        return itemRepository.findCartItemsByItemId(itemId);
    }

    // 상품 정보 조회
    @Override
    public Item getCartItem(Long itemId) {
        return itemRepository.findCartItemByItemId(itemId);
    }

    // ==========================위시리스트 관련 기능==============================

    // 상품 정보 목록 조회
    @Override
    public List<Item> getWishlistItems(List<Long> itemId) {
        return null;
    }

    // 상품 정보 조회
    @Override
    public Item getWishlistItem(Long itemId) {
        return null;
    }
}
