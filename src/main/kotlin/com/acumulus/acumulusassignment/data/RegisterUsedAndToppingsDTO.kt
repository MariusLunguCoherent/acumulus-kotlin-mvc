package com.acumulus.acumulusassignment.data

import com.fasterxml.jackson.databind.ObjectMapper

class RegisterUsedAndToppingsDTO {

    lateinit var email: String
    var toppingsPrefered: MutableSet<String> = mutableSetOf()
    override fun toString(): String {

        return "(email='$email', toppings=$toppingsPrefered)"
    }

    fun mapDtoToJson(): String{
        return ObjectMapper().writeValueAsString(this)
    }


}