package com.acumulus.acumulusassignment.entities

import javax.persistence.*


@Entity
@Table(
    name = "Toppings"
)


class Toppings {
    constructor(topping: String) {
        this.topping = topping
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "topping")
    var topping: String

    @ManyToMany(mappedBy = "toppings")
    var toppings: MutableSet<UserAccount> = mutableSetOf()

}