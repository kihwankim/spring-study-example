package com.example.kotlinmultimodule.exception

const val msg = "해당 Member를 찾지 못 했습니다"

object MemberNotFoundException : NotFoundException(msg)
