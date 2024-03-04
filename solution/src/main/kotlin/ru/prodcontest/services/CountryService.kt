package ru.prodcontest.services

import ru.prodcontest.db.entities.Country

interface CountryService {
    fun readAll(): MutableList<Country.CountryWOId>

    fun readAllInRegion(region: String): MutableList<Country.CountryWOId>

    fun readByAlpha2(alpha2: String): Country.CountryWOId
}
