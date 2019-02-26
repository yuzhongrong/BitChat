package android.com.bitchat.bean;

import android.content.Context;

import com.cjwsc.idcm.Utils.ACacheUtil;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by jack on 2018/8/7.
 */

public class HDWallet implements Serializable {
    private BigInteger privateKey;
    private BigInteger publicKey;
    private String address;
    private String mnemonic;
    private boolean isbackups;
    private String pwd;



    private String nickname;

    public HDWallet(BigInteger publicKey,BigInteger privateKey, String address, String mnemonic) {

        this.privateKey=privateKey;
        this.address=address;
        this.mnemonic=mnemonic;
    }

    public HDWallet(String nickname,String pwd, BigInteger privateKey,BigInteger publicKey, String address, String mnemonic) {

        this.privateKey=privateKey;
        this.publicKey=publicKey;
        this.address=address;
        this.mnemonic=mnemonic;
        this.nickname=nickname;
        this.pwd=pwd;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(BigInteger privateKey) {
        this.privateKey = privateKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public boolean isIsbackups() {
        return isbackups;
    }

    public HDWallet setIsbackups(boolean isbackups) {
        this.isbackups = isbackups;
        return this;
    }

    public void save(Context context){
        ACacheUtil.get(context).put("wallet",this);

    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsername() {
        return nickname;
    }

    public void setUsername(String username) {
        this.nickname = username;
    }


    public BigInteger getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(BigInteger publicKey) {
        this.publicKey = publicKey;
    }

}
