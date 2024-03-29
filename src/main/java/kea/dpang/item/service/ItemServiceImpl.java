package kea.dpang.item.service;

import kea.dpang.item.base.SuccessResponse;
import kea.dpang.item.dto.item.*;
import kea.dpang.item.entity.Category;
import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.SubCategory;
import kea.dpang.item.exception.ItemNotFoundException;
import kea.dpang.item.exception.SellerNotFoundException;
import kea.dpang.item.feign.SellerServiceFeignClient;
import kea.dpang.item.feign.dto.SellerDto;
import kea.dpang.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final SellerServiceFeignClient sellerServiceFeignClient;
    private static final String ITEM_VIEW_COUNT_KEY = "item:viewCount";
    private final StringRedisTemplate redisTemplate;

    // 상품 등록
    @Override
    @Transactional
    public void createItem(CreateItemRequestDto dto) {
        log.info("ItemCreateDto로부터 새로운 아이템 생성을 시작합니다 : {}", dto);

        try {
            // 판매처 정보를 찾는 부분
            ResponseEntity<SuccessResponse<SellerDto>> response = sellerServiceFeignClient.getSeller(dto.getSellerId());

            // 판매처 정보가 없을 경우 SellerNotFoundException 발생
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || response.getBody().getData() == null) {
                throw new SellerNotFoundException(dto.getSellerId());
            }

            Item item = dto.toItem();
            itemRepository.save(item);
            log.info("성공적으로 아이템이 생성되었습니다. 생성된 아이템의 ID는 : {}", item.getId());
        } catch (Exception e) {
            log.error("ItemCreateDto로부터 아이템 생성에 실패하였습니다. DTO 정보 : {}", dto, e);
            throw e;
        }
    }

    @Override
    public List<ItemDto> getItemList(List<Long> itemIds) {
        log.info("item ID 리스트로부터 아이템 리스트 조회를 시작합니다 : {}", itemIds);
        return itemRepository.findAllById(itemIds)
                .stream()
                .map(ItemDto::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemDto getItemInfo(Long itemId) {
        log.info("item ID로부터 아이템 조회를 시작합니다 : {}", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        log.info("아이템 조회가 성공적으로 완료되었습니다. 조회된 아이템의 ID는 : {}", item.getId());

        return new ItemDto(item);
    }

    // 인기 상품 조회
    @Override
    @Transactional(readOnly = true)
    public List<ItemDetailDto> getPopularItems(Pageable pageable) {
        // Redis에서 조회수를 기반으로 인기 상품 ID와 점수를 가져옴
        int count = 0;
        int redisOffset = 0;
        List<ItemDetailDto> popularItems = new ArrayList<>();
        while (count < pageable.getPageSize()) {
            Set<ZSetOperations.TypedTuple<String>> items = redisTemplate.opsForZSet()
                    .reverseRangeWithScores(ITEM_VIEW_COUNT_KEY, redisOffset, redisOffset + pageable.getPageSize() - 1);

            for (ZSetOperations.TypedTuple<String> item : items) {
                Long itemId = Long.valueOf(item.getValue());
                try {
                    Item foundItem = itemRepository.findById(itemId)
                            .orElseThrow(() -> new ItemNotFoundException(itemId));

                    // 판매자 이름을 가져옴
                    ResponseEntity<SuccessResponse<SellerDto>> sellerResponse = sellerServiceFeignClient.getSeller(foundItem.getSellerId());
                    SellerDto seller = sellerResponse.getBody().getData();
                    String sellerName = seller.getName();

                    popularItems.add(new ItemDetailDto(foundItem, sellerName));
                    count++;
                    if (count >= pageable.getPageSize()) {
                        break;
                    }
                } catch (ItemNotFoundException e) {
                    log.error("상품을 찾을 수 없습니다. 상품 ID: {}", itemId, e);
                }
            }
            redisOffset += pageable.getPageSize();
        }
        return popularItems;
    }

    // 조회수 증가 (인기 상품 조회용)
    @Override
    public void incrementViewCount(Long itemId) {
        redisTemplate.opsForZSet().incrementScore(ITEM_VIEW_COUNT_KEY, String.valueOf(itemId), 1);
    }

    // 신상품 조회
    @Override
    public List<ItemDto> getNewItems(Pageable pageable) {
        log.info("신상품 리스트 조회를 시작합니다.");
        Pageable sortedByCreatedTimeDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdTime").descending());
        return itemRepository.findBy(sortedByCreatedTimeDesc)
                .stream()
                .map(ItemDto::new)
                .toList();
    }

    // 상품 상세 정보 조회
    @Override
    @Transactional(readOnly = true)
    public ItemDetailDto getItemDetailInfo(Long itemId) {
        log.info("item ID로부터 아이템 조회를 시작합니다 : {}", itemId);

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        log.info("아이템 조회가 성공적으로 완료되었습니다. 조회된 아이템의 ID는 : {}", item.getId());

        log.info("판매자 이름 조회를 시작합니다 : {}", item.getSellerId());

        String sellerName = Objects.requireNonNull(sellerServiceFeignClient.getSeller(item.getSellerId()).getBody())
                .getData()
                .getName();

        log.info("판매자 이름 조회가 성공적으로 완료되었습니다. 조회된 판매자 이름은 : {}", sellerName);

        return new ItemDetailDto(item, sellerName);
    }

    // 상품 리스트 조회
    // ItemServiceImpl.java
    @Override
    public Page<ItemDetailDto> getItemList(Category category, SubCategory subCategory, Double minPrice, Double maxPrice, String keyword, Long sellerId, Pageable pageable) {
        // 정렬 조건 추가
        Sort sort = Sort.by(Sort.Direction.DESC, "createdTime");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        log.info("상품 리스트 조회를 시작합니다 : 카테고리 = {}, 서브카테고리 = {}, 판매자ID = {}, 최소가격 = {}, 최대가격 = {}, 키워드 = {}, 페이지 요청 정보 = {}", category, subCategory, sellerId, minPrice, maxPrice, keyword, pageable);
        Page<Item> items = filterItems(category, subCategory, sellerId, minPrice, maxPrice, keyword, pageable);

        log.info("상품 리스트를 ItemResponseDto로 변환합니다.");
        return items.map(item -> {
            log.info("판매자 이름 조회를 시작합니다 : 판매자ID = {}", item.getSellerId());
            String sellerName = Objects.requireNonNull(sellerServiceFeignClient.getSeller(item.getSellerId()).getBody())
                    .getData()
                    .getName();

            return new ItemDetailDto(item, sellerName);
        });
    }

    public Page<Item> filterItems(Category category, SubCategory subCategory, Long sellerId, Double minPrice, Double maxPrice, String keyword, Pageable pageable) {

        Specification<Item> spec = Specification.where(null);

        if (category != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category));
        }

        if (subCategory != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("subCategory"), subCategory));
        }

        if (sellerId != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("sellerId"), sellerId));
        }

        if (minPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("discountPrice"), minPrice));
        }

        if (maxPrice != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("discountPrice"), maxPrice));
        }

        if (keyword != null && !keyword.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + keyword + "%"));
        }

        return itemRepository.findAll(spec, pageable);
    }

    // 상품 수정
    @Override
    @Transactional
    public void updateItem(Long itemId, UpdateItemRequestDto dto) {
        log.info("item ID로부터 아이템 수정을 시작합니다 : {}", itemId);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(itemId));

        log.info("아이템 조회가 성공적으로 완료되었습니다. 조회된 아이템의 ID는 : {}", item.getId());

        log.info("ItemUpdateDto로부터 아이템 수정을 시작합니다 : {}", dto);
        item.update(dto);
    }

    // 상품 삭제
    @Override
    @Transactional
    public void deleteItem(List<Long> itemIds) {
        log.info("item ID 리스트로부터 아이템 삭제를 시작합니다 : {}", itemIds);

        for (Long itemId : itemIds) {
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ItemNotFoundException(itemId));

            log.info("아이템 삭제를 시작합니다 : {}", item.getId());
            itemRepository.delete(item);

            log.info("아이템 삭제가 성공적으로 완료되었습니다. 삭제된 아이템의 ID는 : {}", item.getId());
        }

    }

    // 재고 수량 증감
    @Override
    @Transactional
    public void changeStock(List<UpdateStockRequestDto> updateStockRequestDtos) {
        log.info("재고 수량 변경을 시작합니다.");

        for (UpdateStockRequestDto updateStockRequestDto : updateStockRequestDtos) {
            Long itemId = updateStockRequestDto.getItemId();
            int quantity = updateStockRequestDto.getQuantity();

            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new ItemNotFoundException(itemId));
            log.info("아이템 조회가 성공적으로 완료되었습니다. 조회된 아이템의 ID는 : {}", item.getId());

            log.info("재고 수량 변경을 시작합니다 : {}", item.getId());

            if (quantity > 0) {
                item.increaseStock(quantity);
            } else if (quantity < 0) {
                item.decreaseStock(-quantity);
            }

            log.info("재고 수량 변경이 성공적으로 완료되었습니다. 변경된 재고 수량은 : {}", item.getStockQuantity());
        }

        log.info("모든 아이템의 재고 수량 변경이 완료되었습니다.");
    }

    @Transactional
    public void updateItemDiscount(UpdateEventDiscountDto dto) {

        dto.getItemIds().forEach((itemId)->{
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(()-> new ItemNotFoundException(itemId));
            item.setDiscountRate(dto.getDiscountRate());
        });

    }

    @Transactional
    public void updateSellerDiscount(UpdateEventDiscountDto dto){
        List<Item> items = itemRepository.findAllBySellerId(dto.getSellerId());
        if(!items.isEmpty()) {
            items.forEach((item -> {
                item.setDiscountRate(dto.getDiscountRate());
            }));
        }
    }

    @Transactional
    public void deleteEventDiscount(Long eventId) {
        List<Item> items = itemRepository.findAllByEventId(eventId);
        if (!items.isEmpty()) {
            items.forEach((item -> {
                item.setDiscountRate(0);
                item.setEventId(null);
            }));
        }
    }


}