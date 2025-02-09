package com.example.myapplication.exception

class ViewNotFoundCustomException(id: Int) : RuntimeException("View с id = '$id' не существует")
