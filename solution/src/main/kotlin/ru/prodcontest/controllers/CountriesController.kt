package ru.prodcontest.controllers

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.prodcontest.db.entities.Country
import ru.prodcontest.db.exceptions.SqlRowNotFoundException
import ru.prodcontest.db.exceptions.WrongDBQueryArgumentException
import ru.prodcontest.services.CountryService


@RestController
@RequestMapping("/api/countries")
class CountriesController(@Autowired private val countryService: CountryService) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun countries(
        @RequestParam(
            required = false,
            name = "region"
        ) regionString: String?
    ): ResponseEntity<out Any> {

        logger.info { "Countries query received" }
        logger.info { "Requested region:$regionString" }

        return if (regionString == null) {
            ResponseEntity.ok(countryService.readAll().toList().sortedBy { it.alpha2 })
        } else {
            getCountriesOfRegions(regionString)
        }
    }

    @GetMapping("/{alpha2}")
    fun countryByAlpha2(@PathVariable("alpha2") alpha2: String): ResponseEntity<out Any> {
        logger.info { "Country by alpha2 query received" }
        logger.info { "Requested alpha2:$alpha2" }

        return try {
            val country = countryService.readByAlpha2(alpha2)
            ResponseEntity.ok(country)
        } catch (e: WrongDBQueryArgumentException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: SqlRowNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(hashMapOf(Pair("reason", e.message)))
        }
    }

    private fun getCountriesOfRegions(regionString: String): ResponseEntity<out Any> {
        return try {
            val countries = ArrayList<Country.CountryWOId>()
            regionString.split(",").forEach {
                countries.addAll(countryService.readAllInRegion(it))
            }

            ResponseEntity.ok(countries.sortedBy { it.alpha2 })
        } catch (e: WrongDBQueryArgumentException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(hashMapOf(Pair("reason", e.message)))
        }
    }
}
