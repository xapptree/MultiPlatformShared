package com.xapptree.erp.enitities

class LoginModel {
    data class LoginRequest(var username:String, var password:String)
    data class LoginResponse(var id:String, var name:String)
}