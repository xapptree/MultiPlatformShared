import io.ktor.client.*
import io.ktor.client.engine.*

expect fun platform():String
expect fun engine():HttpClientEngine?
expect fun httpClient():HttpClient?