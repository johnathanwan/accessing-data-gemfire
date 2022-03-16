package com.example.accessingdatagemfire

import org.springframework.data.annotation.*
import org.springframework.data.gemfire.mapping.annotation.*
import java.io.Serializable

@Region(value = "People")
data class Person(@field:Id val name: String, val age: Int): Serializable {
    override fun toString(): String {
        return "$name is $age years old"
    }
}