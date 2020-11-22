package com.xapptree.myapplication

import AppClient
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.xapptree.erp.domain.login.ILoginProcessable
import com.xapptree.erp.domain.login.ILoginUseCase
import com.xapptree.erp.enitities.LoginModel
import com.xapptree.erp.platform.login.LoginProvider
import kotlinx.android.synthetic.main.activity_main.*
import platform

class MainActivity : AppCompatActivity(), ILoginProcessable, View.OnClickListener {

    private var loginUseCase: ILoginUseCase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var s =AppClient().getClient()
        tv_txt.text = platform() + s
btn_login.setOnClickListener(this)
        configure()
    }

    private fun configure() {
        val provider = LoginProvider()
         loginUseCase = provider.provideLoginUseCase(this)

    }

    override fun didReceiveLogin(response: LoginModel.LoginResponse) {
        tv_txt.text = response.name
    }

    override fun didReceiveLoginError(error: String) {
    }

    override fun onClick(p0: View?) {
        loginUseCase?.doLogin(LoginModel.LoginRequest("abc","123"))
    }
}
