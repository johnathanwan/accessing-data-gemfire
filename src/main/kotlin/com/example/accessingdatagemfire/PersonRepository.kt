package com.example.accessingdatagemfire

import org.springframework.data.gemfire.repository.query.annotation.Trace
import org.springframework.data.repository.CrudRepository

@Suppress("unused")
interface PersonRepository : CrudRepository<Person, String> {

    @Trace
    fun findByName(name: String): Person

    @Trace
    fun findByAgeGreaterThan(age: Int): Iterable<Person>

    @Trace
    fun findByAgeLessThan(age: Int): Iterable<Person>

    @Trace
    fun findByAgeGreaterThanAndAgeLessThan(greaterThanAge: Int, lessThanAge: Int): Iterable<Person>
}