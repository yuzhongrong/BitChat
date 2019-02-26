package android.com.bitchat.bean

class SearchFriendBean {


    /**
     * uuid : c41cd737-52e6-4984-895a-5dab8fdd0167
     * success : true
     * method : search
     * common : {"search":[{"id":"8","ip":"113.91.211.165","port":"28973","publicKey":"-----BEGIN RSA PUBLIC KEY-----\nMIGJAoGBAL9ggCHu0xT1GP55hOQy57tRg+EtXTJtdQbKh6jiNxme4s7EsYEfJTyG\nRy+v17tIKOdG3VajHw30reI0tClnuyyxQpZ6bCaRmfN9Ew4SP/mX25AxwDqZ7QKQ\n3wPdZSwnuWBLPrxX58PWTXKwYmyFJrTiF3jDe7/NBq1a9QsPSZwfAgMBAAE=\n-----END RSA PUBLIC KEY-----\n"}]}
     */

    var uuid: String? = null
    var success: String? = null
    var method: String? = null
    var common: CommonBean? = null

    class CommonBean {
        var search: List<SearchBean>? = null

        class SearchBean {
            /**
             * id : 8
             * ip : 113.91.211.165
             * port : 28973
             * publicKey : -----BEGIN RSA PUBLIC KEY-----
             * MIGJAoGBAL9ggCHu0xT1GP55hOQy57tRg+EtXTJtdQbKh6jiNxme4s7EsYEfJTyG
             * Ry+v17tIKOdG3VajHw30reI0tClnuyyxQpZ6bCaRmfN9Ew4SP/mX25AxwDqZ7QKQ
             * 3wPdZSwnuWBLPrxX58PWTXKwYmyFJrTiF3jDe7/NBq1a9QsPSZwfAgMBAAE=
             * -----END RSA PUBLIC KEY-----
             *
             */

            var id: String? = null
            var ip: String? = null
            var port: String? = null
            var publicKey: String? = null
        }
    }
}
