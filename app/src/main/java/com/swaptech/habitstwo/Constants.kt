package com.swaptech.habitstwo

const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"

const val IMAGE_URL = "https://images.freeimages.com/images/small-previews/eee/summer-nature-3-1370238.jpg"

const val AUTH_TOKEN = "80f5ca3b-1fcd-4b2c-9cfa-a27dd170a6b3"

enum class ResponseStatus(val code: Int) {
    OK(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    INTERNAL_SERVER_ERROR(500)
}