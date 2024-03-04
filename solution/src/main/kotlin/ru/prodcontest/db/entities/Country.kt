package ru.prodcontest.db.entities

import jakarta.persistence.*
import java.io.Serializable


@Entity
@Table(name = "countries")
class Country {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

    @Column(name = "name")
    var name: String? = null

    @Column(name = "alpha2")
    var alpha2: String? = null

    @Column(name = "alpha3")
    var alpha3: String? = null

    @Column(name = "region")
    var region: String? = null

    class CountryWOId(val name: String, val alpha2: String, val alpha3: String, val region: String) : Serializable
}
