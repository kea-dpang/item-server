package kea.dpang.item.service;

import kea.dpang.item.entity.Item;
import kea.dpang.item.entity.Review;
import kea.dpang.item.repository.ItemRepository;
import kea.dpang.item.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, ReviewRepository reviewRepository) {
        this.itemRepository = itemRepository;
        this.reviewRepository = reviewRepository;
    }

    public Optional<Item> getItemById(Long id) {
        // 상품 ID로 상품을 조회합니다.
        return itemRepository.findById(id);
    }

    public List<Item> searchProducts(String keyword) {
        List<Item> products = itemRepository.findByItemNameContaining(keyword);
        products.forEach(product -> {
            List<Review> reviews = reviewRepository.findById(product.getItemId());
            // product.calculateNumberReview((long) reviews.size());
        });
        return products;
    }

}

