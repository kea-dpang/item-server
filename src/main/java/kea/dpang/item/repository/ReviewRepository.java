package kea.dpang.item.repository;

import kea.dpang.item.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByReviewerId(Long reviewerId, Pageable pageable);

    Page<Review> findByItemId(Long itemId, Pageable pageable);

    Page<Review> findByReviewerIdAndCreatedTimeBetween(Long reviewerId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
