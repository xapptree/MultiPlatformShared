import com.xapptree.erp.NetworkClient
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import okhttp3.Credentials.basic
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

private var trustManager: X509TrustManager? = null
private var sslSocketFactory: SSLSocketFactory? = null
actual fun platform(): String {

    return "Android"
}

actual fun engine(): HttpClientEngine? {
    return null
}

actual fun httpClient(): HttpClient? {
    val okHttpClient: OkHttpClient?
    val configuration = NetworkClient.getConfiguration()

    if (configuration?.IsHttps()!!) {
        when {
            configuration.IsBasicAuth() -> {
                okHttpClient = OkHttpClient.Builder().apply {
                    connectTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
                    writeTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
                    readTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
                    addInterceptor(
                        BasicInterceptor(
                            configuration.getUserName()!!,
                            configuration.getPassword()!!
                        )
                    )
                }.build()
            }
            configuration.HaveSSLStream() -> {
                prepareCustomTrust(configuration.getSSLCertificate())
                okHttpClient = OkHttpClient.Builder().apply {
                    sslSocketFactory(sslSocketFactory!!, trustManager!!)
                    connectTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
                    writeTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
                    readTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
                    hostnameVerifier { hostname, session -> true }
                }.build()

            }
            else -> {
                okHttpClient = OkHttpClient.Builder().apply {
                    connectTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
                    writeTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
                    readTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
                }.build()
            }
        }

    } else {
        okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
            writeTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
            readTimeout(configuration.getTimeOut().toLong(), TimeUnit.MILLISECONDS)
        }.build()
    }

//    val okHttpClient2 = OkHttpClient.Builder().apply {
//
////        certificatePinner(
////            CertificatePinner.Builder()
////                .add(host, sslPin)
////                .build()
////        )
//    }.build()

    return HttpClient(OkHttp) {
        engine {
            preconfigured = okHttpClient
        }
    }


}

// Uses Interceptor
class BasicInterceptor(id: String, password: String) : Interceptor {
    var credentials: String = basic(id, password)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder().header("Authorization", credentials)
        return chain.proceed(builder.build())
    }
}

/*Custom Trust*/
private fun prepareCustomTrust(sslCertificate: String?) {
    try {
        val inputStream: InputStream =
            File(sslCertificate).inputStream()//.resources.assets.open("BW_Intermediate.der")
        val lInputStream: InputStream = inputStream
        val lCF = CertificateFactory.getInstance("X.509")
        val lCertificate = lCF.generateCertificate(lInputStream)
        val lDefaultType = KeyStore.getDefaultType()
        val lKeystore = KeyStore.getInstance(lDefaultType)
        lKeystore.load(null, null)
        lKeystore.setCertificateEntry("ca", lCertificate)
        val lTrustString = TrustManagerFactory.getDefaultAlgorithm()
        val lTrustManager = TrustManagerFactory.getInstance(lTrustString)
        lTrustManager.init(lKeystore)
        val trustManagers = lTrustManager.trustManagers
        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
            ("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers))
        }
        trustManager = trustManagers[0] as X509TrustManager
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, arrayOf(trustManager), null)
        sslSocketFactory = sslContext.socketFactory
    } catch (e: java.io.IOException) {
        e.printStackTrace()
    } catch (e: GeneralSecurityException) {
        throw RuntimeException(e)
    } catch (e: FileNotFoundException) {

    }
}