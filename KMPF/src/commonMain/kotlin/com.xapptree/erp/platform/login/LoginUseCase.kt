package com.xapptree.erp.platform.login

import com.xapptree.erp.NetworkClient
import com.xapptree.erp.NetworkClientConfig
import com.xapptree.erp.domain.login.ILoginProcessable
import com.xapptree.erp.domain.login.ILoginUseCase
import com.xapptree.erp.enitities.LoginModel
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*

class LoginUseCase(callback: ILoginProcessable) : ILoginUseCase {

    private val callback: ILoginProcessable? = callback as ILoginProcessable
    override fun doLogin(request: LoginModel.LoginRequest) {
        // callback?.didReceiveLogin(LoginModel.LoginResponse("1", "abc"))
        //   makeRequest()
        NetworkClientConfig.Builder().setBaseUrl("https://reqres.in/api/").build()
       // makeRequest()

        CoroutineScope(Dispatchers.Default).launch {
           val res =  async {   val response=
                NetworkClient.getClient()!!.get<String>("https://reqres.in/api/users/2")
               return@async response
           }.await()
            withContext(Dispatchers.Main) {
                callback?.didReceiveLogin(LoginModel.LoginResponse("1", "abc  "+ res))
            }
        }
    }

    fun getHeader(): HashMap<String, String> {
        val map = HashMap<String, String>()
//        contentType?.let { map.put("Content-Type", it) }
//        locale?.let { map.put("locale", it) }
//        apiVersion?.let { map.put("APIVersion", it) }
//        authorization?.let { map.put("Authorization", it) }
//        userAgent?.let { map.put("User-Agent", it) }
//        deviceID?.let { map.put("DeviceID", it) }
        return map
    }

    fun makeRequest() {
        GlobalScope.apply {
            launch(Dispatchers.Default) {
                val response: HttpResponse =
                    NetworkClient.getClient()!!.get("https://reqres.in/api/users/2")
               // callback?.didReceiveLogin(LoginModel.LoginResponse("1", "abc"))
                // callback(result)
            }
        }

//        NetworkClient.getClient()!!.post<String>{
//            url("https://reqres.in/api/login")
////            headers{
////                getHeader().forEach { (key, value) ->
////                    append(key,value)
////                }
////
////             }
//        }
    }
}