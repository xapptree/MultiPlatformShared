import com.xapptree.erp.NetworkClient
import io.ktor.client.*
import io.ktor.client.engine.*

actual  fun platform():String{
    return "iOS"
}

actual  fun engine():HttpClientEngine?{
return null
}

actual fun httpClient():HttpClient?{
 //var iosClient = NSURLSession
    val configuration = NetworkClient.getConfiguration()
return null
}