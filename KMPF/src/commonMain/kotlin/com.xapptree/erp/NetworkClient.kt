package com.xapptree.erp

import httpClient
import io.ktor.client.*

object NetworkClient {
    private var client: HttpClient? = null
    private var configuration: NetworkClientConfig? = null
    fun getClient(): HttpClient? {
        if (client == null) {
            client = httpClient()
        }
        return client
    }

    fun setConfiguration(config: NetworkClientConfig) {
        this.configuration = config
    }

    fun getConfiguration(): NetworkClientConfig? {
        return configuration
    }

}