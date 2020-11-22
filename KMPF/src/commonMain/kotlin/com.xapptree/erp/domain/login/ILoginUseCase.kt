package com.xapptree.erp.domain.login

import com.xapptree.erp.enitities.LoginModel

interface ILoginUseCase {
    fun doLogin(request: LoginModel.LoginRequest)
}