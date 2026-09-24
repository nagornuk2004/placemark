package org.setu.placemark.models

interface PlacemarkStore {

    fun findAll(): List<PlacedMark>
    fun create(placemark: PlacedMark)
    fun update(placemark: PlacedMark): Boolean
    fun delete(id: Long): Boolean
    fun findOne(id: Long): PlacedMark?

}