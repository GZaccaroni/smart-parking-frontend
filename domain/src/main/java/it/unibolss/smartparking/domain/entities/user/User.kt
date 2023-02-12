package it.unibolss.smartparking.domain.entities.user

data class User(
    val name: String,
    val email: String,
    val parkSlot: UserParkingSlot?
)