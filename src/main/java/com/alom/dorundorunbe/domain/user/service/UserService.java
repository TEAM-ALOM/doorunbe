package com.alom.dorundorunbe.domain.user.service;

import com.alom.dorundorunbe.domain.auth.dto.AuthUserDto;
import com.alom.dorundorunbe.domain.user.domain.User;
import com.alom.dorundorunbe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    public Boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        user.updateIsDeleted(true);
    }

    public User registerOrLogin(AuthUserDto dto) {
        return userRepository.findByEmail(dto.email())
                .orElseGet(() -> {
                    User user = User.builder()
                            .email(dto.email())
                            .isDeleted(false)
                            .nickname(dto.email())
                            .cash(0L)
                            .build();

                    return userRepository.save(user);
                });
    }
}