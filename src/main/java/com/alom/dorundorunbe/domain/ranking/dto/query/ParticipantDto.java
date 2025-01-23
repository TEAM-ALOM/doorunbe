package com.alom.dorundorunbe.domain.ranking.dto.query;

import com.alom.dorundorunbe.domain.user.domain.User;
import lombok.Data;

@Data
public class ParticipantDto {

    private Long userId; // 사용자 ID
    private String nickname;

    public ParticipantDto(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
    }
}

