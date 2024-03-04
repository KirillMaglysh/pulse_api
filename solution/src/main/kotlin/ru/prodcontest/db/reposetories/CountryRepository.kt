package ru.prodcontest.db.reposetories

import org.springframework.data.jpa.repository.JpaRepository
import ru.prodcontest.db.entities.Country

interface CountryRepository : JpaRepository<Country, Int> {
    fun findProjectedBy(): MutableList<Country.CountryWOId>
    fun findProjectedByRegion(alpha2: String): MutableList<Country.CountryWOId>

    fun findOneProjectedByAlpha2(alpha2: String): Country.CountryWOId?
}
