package kea.dpang.item.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private Long productId; // 간단한 예제를 위해 직접 연결

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Double rating;

    // ... 기본 생성자, getter 및 setter

    // 생성자, getter, setter 등은 생략하였습니다.
}
