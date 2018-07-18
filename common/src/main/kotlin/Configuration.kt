class Configuration private constructor() {
    companion object {
        @JvmStatic
        fun getBotToken():String {
            return "663462502:AAES5VLE2lAw_K2IyimGQASaf36fJxT18e8"
        }

        @JvmStatic
        fun getChatId():String {
            return "-1001395933380"
        }

        @JvmStatic
        fun getProxyHost():String {
            return "194.67.201.106"
        }

        @JvmStatic
        fun getProxyPort():Int {
            return 3128
        }
    }
}