package com.example.hystrixfeign.application

const val CIRCUIT_NAME = "circuit"

interface ApiService {

    fun callNoraml()

    fun callTimeout(): String

    fun callNotFound(): String
}