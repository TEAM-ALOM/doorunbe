package com.alom.dorundorunbe.mypage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UserPasswordChangeDTO {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;
}
