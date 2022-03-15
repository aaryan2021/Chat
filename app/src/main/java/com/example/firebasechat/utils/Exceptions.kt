package com.example.firebasechat.utils

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)
class SessionExpiredException(message: String) : IOException(message)