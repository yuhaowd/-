package com.zshs.messagecenter.enums;


import lombok.Data;


public enum TemplateEnum {

    // 示例
    mail_1(1001, "您好${userName}，您卡号为${cardNumber} ，地址为${buildingName} ，请注意查收！");


    private int number;

    private String content;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    TemplateEnum(int number, String content) {
        this.number = number;
        this.content = content;
    }
}
