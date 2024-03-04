package ru.prodcontest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.prodcontest.db.entities.Country
import ru.prodcontest.db.exceptions.SqlRowNotFoundException
import ru.prodcontest.db.exceptions.WrongDBQueryArgumentException
import ru.prodcontest.db.reposetories.CountryRepository

@Service
class CountryServiceImpl(@Autowired val countryRepo: CountryRepository) : CountryService {
    private val regionRegex = Regex("^(Europe|Africa|Americas|Oceania|Asia)\$")
    private val alpha2Regex = Regex("^[a-zA-Z]{2}\$")


    override fun readAll(): MutableList<Country.CountryWOId> {
        return countryRepo.findProjectedBy()
    }

    override fun readAllInRegion(region: String): MutableList<Country.CountryWOId> {
        if (!checkIfRegionSafe(region)) {
            throw WrongDBQueryArgumentException("Region is not correct")
        }

        return countryRepo.findProjectedByRegion(region)
    }

    override fun readByAlpha2(alpha2: String): Country.CountryWOId {
        if (!checkIfAlpha2Safe(alpha2)) {
            throw WrongDBQueryArgumentException("Alpha2 is not correct")
        }

        return countryRepo.findOneProjectedByAlpha2(alpha2)
            ?: throw SqlRowNotFoundException("No country find with alpha2=$alpha2")
    }

    private fun checkIfRegionSafe(region: String): Boolean {
        return regionRegex.matches(region)
    }

    private fun checkIfAlpha2Safe(alpha2: String): Boolean {
        return alpha2Regex.matches(alpha2)
    }


}