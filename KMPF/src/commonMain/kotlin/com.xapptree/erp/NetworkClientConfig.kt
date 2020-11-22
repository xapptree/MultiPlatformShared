package com.xapptree.erp

class NetworkClientConfig {
    private var baseUrl: String? = null
    private var timeout = 0
    private var isHttps = false

    private var sslCertificate: String? = null
    private var sslPins: List<String>? = null
    private var userName: String? = null
    private var password: String? = null
    private var basicAuth = false
    private var havePins = false
    private var haveSsl = false

    private constructor(builder: Builder) {
        this.baseUrl = builder.baseUrl
        this.timeout = builder.timeout
        this.isHttps = builder.isHttps
        this.sslCertificate = builder.sslCertificate
        this.sslPins = builder.pins
        this.userName = builder.userName
        this.password = builder.password
        this.basicAuth = builder.basicAuth
        this.havePins = builder.havePins
        this.haveSsl = builder.haveSsl
        NetworkClient.setConfiguration(this)
    }

    fun getBaseUrl(): String? {
        return baseUrl
    }

    fun getTimeOut(): Int {
        return timeout
    }

    fun IsHttps(): Boolean {
        return isHttps
    }

    fun getSSLCertificate(): String? {
        return sslCertificate
    }

    fun getPins(): List<String?>? {
        return sslPins
    }

    fun getUserName(): String? {
        return userName
    }

    fun getPassword(): String? {
        return password
    }

    fun IsBasicAuth(): Boolean {
        return basicAuth
    }

    fun HavePins(): Boolean {
        return havePins
    }

    fun HaveSSLStream(): Boolean {
        return haveSsl
    }

    class Builder {

        internal var baseUrl: String? = null
        internal var timeout = 60000
        internal var isHttps = false

        internal var sslCertificate: String? = null
        internal var pins: List<String> = ArrayList<String>()
        internal var userName: String? = null
        internal var password: String? = null
        internal var basicAuth = false
        internal var haveSsl = false
        internal var havePins = false

        fun setBaseUrl(base_url: String?) = apply { this.baseUrl = base_url }

        fun setTimeOut(timeout: Int) = apply { this.timeout = timeout }

        fun setHttps(is_https: Boolean) = apply { this.isHttps = is_https }

        fun setSslStream(ssl_cert: String?) = apply {
            if (ssl_cert == null) {
                throw IllegalArgumentException("Missing InputStream, Certificate stream required for Trust Cert.")
            }
            this.sslCertificate= ssl_cert
            this.haveSsl = true
        }

        fun setPins(pins: List<String>) = apply {
            if (pins.size <= 0) {
                throw IllegalArgumentException("Missing keys, Certificate PINS are required for ssl pinning.")
            }
            this.pins = pins
            this.havePins = true
        }

        fun setUserName(user_name: String?) = apply { this.userName = user_name }

        fun setPassword(password: String?) = apply { this.password = password }

        fun setBasicAuth(basic_auth: Boolean) = apply { this.basicAuth = basic_auth }

        fun build(): NetworkClientConfig? {
            if (baseUrl.isNullOrEmpty()) {
                throw IllegalArgumentException("Missing base url, URL is required and it is mandatory for further process.")
            }
            if (basicAuth) {
                if (userName.isNullOrEmpty() || password.isNullOrEmpty()) {
                    throw IllegalArgumentException("Missing values, UserName and Password are required in case you are using basic authentication.")
                }
            }
            return NetworkClientConfig(this)
        }
    }

}