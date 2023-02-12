package it.unibolss.smartparking.domain.entities.user

data class User(
    val id: String,
    val parkSlot: UserParkingSlot?
)