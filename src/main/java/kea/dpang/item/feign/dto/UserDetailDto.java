package kea.dpang.item.feign.dto;

import lombok.Getter;

import java.time.LocalDate;

/**
 * 사용자 서버로부터 받아오는 유저 상세 정보
 */
@Getter
public class UserDetailDto {
    private Long userId;
    private Long employeeNumber;
    private String name;
    private String email;
    private LocalDate joinDate;
}
