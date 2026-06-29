package com.e_commerce.Cranzo.Entity;

import com.e_commerce.Cranzo.Enums.PaymentMethod;
import com.e_commerce.Cranzo.Enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;    // CREDIT_CARD, UPI, COD, WALLET

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;
    // PENDING, SUCCESS, FAILED, REFUNDED

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private String transactionId;   // from payment gateway

    private LocalDateTime paidAt;
}
