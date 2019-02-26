package android.com.bitchat.bean;

public class TianWallet {

    private String address;
    private String publickey;
    private String privatekey;
    private String words;

    public TianWallet(String address, String publickey, String privatekey, String words) {
        this.address = address;
        this.publickey = publickey;
        this.privatekey = privatekey;
        this.words = words;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
