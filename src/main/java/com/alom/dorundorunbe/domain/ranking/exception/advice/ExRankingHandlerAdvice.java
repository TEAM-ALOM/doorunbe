package com.alom.dorundorunbe.domain.ranking.exception.advice;

import com.alom.dorundorunbe.domain.ranking.exception.RankingNotFoundException;
import com.alom.dorundorunbe.domain.ranking.exception.UserAlreadyJoinedRankingException;
import com.alom.dorundorunbe.domain.ranking.exception.UserNotFoundException;
import com.alom.dorundorunbe.domain.ranking.exception.common.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExRankingHandlerAdvice {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResult> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResult.of("404", e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyJoinedRankingException.class)
    public ResponseEntity<ErrorResult> handleUserAlreadyJoinedRankingException(UserAlreadyJoinedRankingException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResult.of("409", e.getMessage()));
    }

    @ExceptionHandler(RankingNotFoundException.class)
    public ResponseEntity<ErrorResult> handleRankingNotFoundException(RankingNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResult.of("404", e.getMessage()));
    }

}
