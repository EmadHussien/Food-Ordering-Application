package com.FoodOrderingApp.foodorder.payment;

import com.FoodOrderingApp.foodorder.customer.Customer;
import com.FoodOrderingApp.foodorder.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="payments")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Payment {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "receipt_url")
    private String receiptUrl;

    @Column(nullable = false , name = "total_amount")
    private Long totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;


    @ManyToOne(fetch = FetchType.LAZY ,cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JsonIgnore
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @OneToOne(cascade = {
            CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH
    })
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "created_at", nullable = false , updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;


    public Payment(String paymentMethod, String transactionId, Long totalAmount, PaymentStatus status , String receiptUrl) {
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.receiptUrl = receiptUrl;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED
    }

}
