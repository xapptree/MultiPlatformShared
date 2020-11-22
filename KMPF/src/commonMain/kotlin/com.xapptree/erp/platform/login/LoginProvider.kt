package com.xapptree.erp.platform.login

import com.xapptree.erp.domain.login.ILoginProcessable
import com.xapptree.erp.domain.login.ILoginProvider
import com.xapptree.erp.domain.login.ILoginUseCase

class LoginProvider : ILoginProvider {
    override fun provideLoginUseCase(callback: ILoginProcessable): ILoginUseCase {
        return LoginUseCase(callback)
    }
}