package com.roc.message;

import com.roc.asm.message.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@EqualsAndHashCode(callSuper = true)
@Data
@ToString
@Slf4j
public class LoginRequestMessage extends Message {
    private String username;
    private String password;
    private String nickname;

    public LoginRequestMessage() {
    }

    public LoginRequestMessage(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
    @Override
    public int getMessageType(){
        return 0;
    }
}
