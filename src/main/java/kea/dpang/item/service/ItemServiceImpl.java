package kea.dpang.item.service;

import kea.dpang.item.dto.*;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Review;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.exception.ReviewNotFoundException;
import kea.dpang.item.feign.SellerFeignClient;
import kea.dpang.item.feign.dto.ItemIdsRequestDto;
import kea.dpang.item.feign.dto.ItemSimpleListDto;
import kea.dpang.item.repository.ItemRepository;

import kea.dpang.item.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final SellerFeignClient sellerFeignClient;
    private static final String ITEM_VIEW_COUNT_KEY = "item:viewCount";
    private final StringRedisTemplate redisTemplate;

    // 상품 등록
    @Override
    @Transactional
    public ItemResponseDto createItem(ItemCreateDto dto) {
        Item item = Item.from(dto);
        return new ItemResponseDto(itemRepository.save(item));
    }

    // 상품 상세 정보 조회
    @Override
    @Transactional(readOnly = true)
    public ItemResponseDto getItem(Long itemId) {
        String sellerName = getSellerName(itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        return new ItemResponseDto(item, sellerName);
    }

    // 상품 카드 리스트 조회
    @Override
    @Transactional
    public List<ItemCardDto> getItemCard(Pageable pageable) {
        Page<Item> items = itemRepository.findAll(pageable);
        return items.stream()
                .map(ItemCardDto::new)
                .collect(Collectors.toList());
    }

    // 관리자용 상품 리스트 조회
    @Override
    @Transactional
    public List<ItemManageListDto> getItemManageList(Pageable pageable) {
        Page<Item> items = itemRepository.findAll(pageable);
        return items.stream()
                .map(ItemManageListDto::new)
                .collect(Collectors.toList());
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
        }).toList();
    }

    // 상품 수정
    @Override
    @Transactional
    public ItemResponseDto updateItem(Long itemId, ItemUpdateDto dto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        item.updateInformation(dto);
        return new ItemResponseDto(itemRepository.save(item));
    }

    // 상품 삭제
    @Override
    @Transactional
    public void deleteItem(List<Long> itemIds) {
        for (Long itemId:itemIds){

            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ItemNotFoundException(itemId));
            itemRepository.delete(item);
        }

    }

    // 재고 수량 조회
    @Override
    @Transactional
    public int getStockQuantity(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        return item.getStockQuantity();
    }

    // 재고 수량 증가
    @Override
    @Transactional
    public int increaseStock(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        item.increaseStock(quantity);
        return item.getStockQuantity();
    }

    // 재고 수량 감소
    @Override
    @Transactional
    public int decreaseStock(Long itemId, int quantity) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
        item.decreaseStock(quantity);
        return item.getStockQuantity();
    }

    /* feign */
    // 이벤트 - 상품명 조회
    public String getItemName(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId)).getItemName();
    }

    // 주문 - 상품 정보 조회
    @Override
    @Transactional(readOnly = true)
    public Item getItemInquiry(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));
    }

    // 판매처 - 판매처명 조회
    @Override
    @Transactional(readOnly = true)
    public String getSellerName(Long sellerId) {
        return sellerFeignClient.getSeller(sellerId).getBody().getData();
    }

    // 장바구니, 위시리스트 - 백엔드용 상품 리스트 조회
    @Override
    @Transactional
    public List<ItemSimpleListDto> getCartItemsInquiry(ItemIdsRequestDto requestDto) {
        List<Item> items = itemRepository.findAll();
        return items.stream()
                .map(ItemSimpleListDto::new)
                .collect(Collectors.toList());
    }
}