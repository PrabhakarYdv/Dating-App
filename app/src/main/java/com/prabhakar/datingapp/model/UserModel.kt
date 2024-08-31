package com.prabhakar.datingapp.model

data class UserModel(
    val uId: String?="",
    var userNumber: String?="",
    val userName: String?="",
    val userEmail: String?="",
    val city: String?="",
    val gender: String?="",
    val relationshipStatus: String?="",
    val star: String?="",
    val age: String?="",
    val status: String?="",
    var image: String?=""
) {
}