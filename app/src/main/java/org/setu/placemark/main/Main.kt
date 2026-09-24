package org.setu.placemark.main

import org.setu.placemark.models.PlacemarkMemStore
import org.setu.placemark.models.PlacedMark

val store = PlacemarkMemStore()

fun main() {
    println("=== Placemark Console App (Lab 1) ===")
    var input: Int
    do {
        input = menu()
        when (input) {
            1 -> addPlacemark()
            2 -> listPlacemarks()
            3 -> updatePlacemark()
            4 -> deletePlacemark()
            5 -> searchPlacemark()
            0 -> println("\nExiting Placemark application. Goodbye!")
            else -> println("\nInvalid option. Please try again.")
        }
    } while (input != 0)
}

fun menu(): Int {
    println("\n----------------------------------")
    println(" MAIN MENU")
    println("----------------------------------")
    println(" 1. Add Placemark")
    println(" 2. List All Placemarks")
    println(" 3. Update a Placemark")
    println(" 4. Delete a Placemark")
    println(" 5. Search Placemark by ID")
    println(" 0. Exit")
    print("\nEnter option: ")
    return readlnOrNull()?.toIntOrNull() ?: -1
}

fun addPlacemark() {
    println("\n--- Add Placemark ---")
    print("Enter Title: ")
    val title = readlnOrNull()?.trim().orEmpty()
    print("Enter Description: ")
    val description = readlnOrNull()?.trim().orEmpty()

    if (title.isNotEmpty()) {
        val placemark = PlacedMark(title = title, description = description)
        store.create(placemark)
        println("Placemark added successfully with ID: ${placemark.id}")
    } else {
        println("Title cannot be empty. Creation cancelled.")
    }
}

fun listPlacemarks() {
    println("\n--- All Placemarks ---")
    val placemarks = store.findAll()
    if (placemarks.isEmpty()) {
        println("No placemarks stored yet.")
    } else {
        placemarks.forEach { println("ID: ${it.id} | Title: ${it.title} | Description: ${it.description}") }
    }
}

fun updatePlacemark() {
    println("\n--- Update Placemark ---")
    listPlacemarks()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Placemark to update: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null && store.findOne(id) != null) {
        print("Enter New Title: ")
        val title = readlnOrNull()?.trim().orEmpty()
        print("Enter New Description: ")
        val description = readlnOrNull()?.trim().orEmpty()

        if (title.isNotEmpty()) {
            val updated = store.update(PlacedMark(id = id, title = title, description = description))
            if (updated) println("Placemark updated successfully.")
        } else {
            println("Title cannot be empty. Update cancelled.")
        }
    } else {
        println("Placemark with ID $id not found.")
    }
}

fun deletePlacemark() {
    println("\n--- Delete Placemark ---")
    listPlacemarks()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Placemark to delete: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val deleted = store.delete(id)
        if (deleted) {
            println("Placemark with ID $id deleted successfully.")
        } else {
            println("Placemark with ID $id not found.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun searchPlacemark() {
    println("\n--- Search Placemark ---")
    print("Enter ID: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val placemark = store.findOne(id)
        if (placemark != null) {
            println("Found: ID: ${placemark.id} | Title: ${placemark.title} | Description: ${placemark.description}")
        } else {
            println("No placemark found with ID $id.")
        }
    } else {
        println("Invalid ID entered.")
    }
}
