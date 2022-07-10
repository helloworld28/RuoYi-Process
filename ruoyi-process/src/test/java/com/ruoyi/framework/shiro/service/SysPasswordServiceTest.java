package com.ruoyi.framework.shiro.service;

import org.apache.shiro.crypto.hash.Md5Hash;


class SysPasswordServiceTest {

    public static void main(String[] args) {
        System.out.println(new Md5Hash("admin123456111111").toHex().toString());
    }
}