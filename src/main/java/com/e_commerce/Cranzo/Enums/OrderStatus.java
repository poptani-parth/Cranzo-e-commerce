package com.e_commerce.Cranzo.Enums;

public enum OrderStatus {
    PENDING,   // order placed, not yet confirmed
    CONFIRMED,   // payment received, processing
    SHIPPED,     // dispatched from warehouse
    DELIVERED,   // received by customer
    CANCELLED,  // cancelled before shipment
    RETURNED       // returned after delivery
}
