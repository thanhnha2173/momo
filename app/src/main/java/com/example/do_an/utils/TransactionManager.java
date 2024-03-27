package com.example.do_an.utils;

import java.util.ArrayList;
import java.util.List;

public class TransactionManager {
    private static TransactionManager instance;
    private List<TransactionObserver> observers;

    private TransactionManager() {
        observers = new ArrayList<>();
    }

    public static synchronized TransactionManager getInstance() {
        if (instance == null) {
            instance = new TransactionManager();
        }
        return instance;
    }

    public void registerObserver(TransactionObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(TransactionObserver observer) {
        observers.remove(observer);
    }

    public void notifyRechargeSuccess() {
        for (TransactionObserver observer : observers) {
            observer.notifyRechargeSuccess();
        }
    }

    public void notifyRechargeFailure(String errorMessage) {
        for (TransactionObserver observer : observers) {
            observer.notifyRechargeFailure(errorMessage);
        }
    }
}
