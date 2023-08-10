package br.com.darlan.model

import jakarta.persistence.*
import java.util.*

@Entity(name = "book")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name = "author", nullable = false, length = 180)
    var author: String = "",

    @Column(name = "title", nullable = false, length = 250)
    var title: String = "",

    @Column(name = "launch_date", nullable = false)
    @Temporal(TemporalType.DATE)
    var lauchDate: Date? = null,

    @Column(name = "price", nullable = false)
    var price: Double? = null,

    @Transient
    var currency: String = "",

    @Transient
    var enviroment: String? = ""
)
