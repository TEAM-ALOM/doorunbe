package com.alom.dorundorunbe.domain.user.dto;

import com.alom.dorundorunbe.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 정보 DTO")
public record UserInfoDto(
        @Schema(description = "사용자 ID", example = "1") Long id,
        @Schema(description = "닉네임", example = "닉네임두") String nickName,
        @Schema(description = "사용자 티어 이름", example = "스타터") String tier) {

    public static UserInfoDto of(User user){
        return new UserInfoDto(user.getId(), user.getNickname(), user.getTier().getName());
    }
}
