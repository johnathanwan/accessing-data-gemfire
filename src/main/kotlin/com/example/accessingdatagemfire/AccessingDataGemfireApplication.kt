package com.example.accessingdatagemfire

import org.apache.geode.cache.client.*
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories
import java.util.stream.StreamSupport.*

@SpringBootApplication
@ClientCacheApplication(name = "AccessingDataGemFireApplication")
@EnableEntityDefinedRegions(basePackageClasses = [Person::class], clientRegionShortcut = ClientRegionShortcut.LOCAL)
@EnableGemfireRepositories
class AccessingDataGemfireApplication {

    @Bean
    fun run(personRepository: PersonRepository): ApplicationRunner {
        return ApplicationRunner {
            val alice = Person("Adult Alice", 40)
            val bob = Person("Baby Bob", 1)
            val carol = Person("Teen Carol", 13)

            println("Before accessing data in Apache Geode...")

            listOf(alice, bob, carol).forEach { println(it) }

            println("Saving Alice, Bob and Carol to Pivotal GemFire...")

            personRepository.save(alice)
            personRepository.save(bob)
            personRepository.save(carol)

            println("Lookup each person by name...")

            listOf(alice.name, bob.name, carol.name).forEach { println(personRepository.findByName(it)) }

            println("Query adults (over 18)")

            stream(personRepository.findByAgeGreaterThan(18).spliterator(), false).forEach { println(it) }

            println("Query babies (less than 5):")

            stream(personRepository.findByAgeLessThan(5).spliterator(), false).forEach { println(it) }

            println("Query teens (between 12 and 20):")

            stream(personRepository.findByAgeGreaterThanAndAgeLessThan(12, 20).spliterator(),
                false)
                .forEach { println(it) }
        }
    }

}

fun main(args: Array<String>) {
    runApplication<AccessingDataGemfireApplication>(*args)
}
