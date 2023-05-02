package com.acumulus.acumulusassignment.entities

import javax.persistence.*

@Entity
@Table(
    name = "UserAccount", indexes = [
        Index(name = "idx_useraccount_email", columnList = "accountEmail", unique = true)
    ]
)


class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null
        protected set

    @Basic(fetch = FetchType.EAGER)
    @Column(name = "accountEmail", nullable = false, unique = true)
    lateinit var email: String

    @Column(name = "toppings", nullable = false, unique = true)
    @ManyToMany(
        cascade = arrayOf(
           CascadeType.ALL
        )
    )
    @JoinTable(
        name = "user_toppings",
        joinColumns = arrayOf(JoinColumn(name = "useraccount_id")),
        inverseJoinColumns = arrayOf(JoinColumn(name = "topping_id"))
    )
    var toppings: MutableSet<Toppings> = mutableSetOf()

    fun addTopping(topping: Toppings){
        toppings.add(topping)
        topping.toppings.add(this)
    }

    fun removeTopping(topping: Toppings){
        this.toppings.remove(topping)
        topping.toppings.remove(this)
    }
    fun removeAllToppings(){
        toppings.clear()
    }

}