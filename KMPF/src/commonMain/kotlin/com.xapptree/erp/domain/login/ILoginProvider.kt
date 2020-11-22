package com.xapptree.erp.domain.login

interface ILoginProvider {
    fun provideLoginUseCase(callback:ILoginProcessable):ILoginUseCase
}