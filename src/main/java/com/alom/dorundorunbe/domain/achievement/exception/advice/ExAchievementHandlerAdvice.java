package com.alom.dorundorunbe.domain.achievement.exception.advice;

import com.alom.dorundorunbe.domain.achievement.exception.*;
import com.alom.dorundorunbe.domain.achievement.exception.common.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExAchievementHandlerAdvice {
    @ExceptionHandler(AchievementAlreadyExistsException.class)
    public ResponseEntity<ErrorResult> handleAchievementAlreadyExists(AchievementAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResult.of("409", e.getMessage()));
    }

    @ExceptionHandler(AchievementNotFoundException.class)
    public ResponseEntity<ErrorResult> handleAchievementNotFound(AchievementNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResult.of("404", e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResult> handleUserNotFound(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResult.of("404", e.getMessage()));
    }

    @ExceptionHandler(UserAchievementAlreadyClaimedException.class)
    public ResponseEntity<ErrorResult> handleUserAchievementAlreadyClaimed(UserAchievementAlreadyClaimedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResult.of("409", e.getMessage()));
    }

    @ExceptionHandler(UserAchievementNotFoundException.class)
    public ResponseEntity<ErrorResult> handleUserAchievementNotFound(UserAchievementNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResult.of("404", e.getMessage()));
    }

    @ExceptionHandler(AchievementConditionNotMetException.class)
    public ResponseEntity<ErrorResult> handleAchievementConditionNotMet(AchievementConditionNotMetException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResult.of("400", e.getMessage()));
    }

    @ExceptionHandler(RewardAlreadyClaimedException.class)
    public ResponseEntity<ErrorResult> handleRewardAlreadyClaimed(RewardAlreadyClaimedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResult.of("409", e.getMessage()));
    }
}
