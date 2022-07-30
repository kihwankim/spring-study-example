package com.example.hystrixfeign.application

const val CIRCUIT_NAME = "other"

interface ApiService {

    fun callNoraml()

    fun callTimeout(): String

    fun callNotFound(): String
}