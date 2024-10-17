package vending_machine.domain

import jakarta.persistence.*

@Entity
data class Transaction @JvmOverloads constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id", nullable = false)
        val product: Product = Product(),
        val amountPaid: Int = 0,
        val productPrice: Int = 0,
        val coins: List<Coin> = emptyList(),
        val notes: List<BankNote> = emptyList()
)