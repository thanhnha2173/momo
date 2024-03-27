package com.example.do_an.utils;

public interface TransactionObserver {
    void notifyRechargeSuccess();
    void notifyRechargeFailure(String errorMessage);
}

