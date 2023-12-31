package com.example.instantorder.Repository;


public final class IpAddress {
    private final String ip;
    IpAddress()
    {
        //ifconfig | grep "inet " on terminal to find
        this.ip = "10.51.98.34";
    }
    String getIp()
    {
        return this.ip;
    }
}
