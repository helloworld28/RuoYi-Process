package com.ruoyi.process.order;

public enum OrderStatus {
    WAITING_ACCEPT(10),
    WAITING_REPORT(20),
    WAITING_CONFIRM(30),
    WAITING_BUY_INSTOCK(40),
    WAITING_KEEPER_CONFIRM(45),
    WAITING_DELIVERY(50),
    DONE(100);


    private int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
