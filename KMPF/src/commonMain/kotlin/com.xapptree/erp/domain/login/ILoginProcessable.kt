package com.xapptree.erp.domain.login

import com.xapptree.erp.enitities.LoginModel

interface ILoginProcessable {
    fun didReceiveLogin(response:LoginModel.LoginResponse)
    fun didReceiveLoginError(error:String)
}