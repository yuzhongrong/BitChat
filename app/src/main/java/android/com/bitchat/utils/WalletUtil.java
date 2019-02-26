package android.com.bitchat.utils;

import android.text.TextUtils;
import android.util.Log;


import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;

import android.com.bitchat.bean.HDWallet;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yan
 * @version v1.0
 * @Description: TODO(钱包功能测试)
 * @date 2018年8月5日 下午7:35:22
 */
public class WalletUtil {
   // private static final Logger LOG = LoggerFactory.getLogger(WalletUtil.class);

    /**
     * @param @return 参数
     * @return String    返回类型
     * @Title: generateMnemonic
     * @Description: TODO(生成助记词)
     * @author yan
     * @date 2018年8月5日 下午3:37:28
     * @version v1.0
     */
    public static String generateMnemonic() {
        byte[] initialEntropy = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(initialEntropy);
        String mnemonic = MnemonicUtil.generateMnemonic(initialEntropy);
        return mnemonic;
    }


    /**
     * @param @param  password
     * @param @param  item_mnemonic
     * @param @return 参数
     * @return Credentials    返回类型
     * @Title: importBIP39Wallet
     * @Description: TODO(密码 ， 助记词获取凭证)
     * @author yan
     * @date 2018年8月5日 下午4:21:28
     * @version v1.0
     */
    public static Credentials importBIP39Wallet(String password, String mnemonic) {
        Credentials credentials = WalletUtils.loadBip39Credentials(password, mnemonic);
        return credentials;
    }


    /**
     * 创建BIP39 钱包
     **/

    public static Flowable<HDWallet> CreateWallet(String password,
                                                  String mnemonics) {


        return Flowable.just(mnemonics)
                .map(s -> {
                    Credentials keyPair = importBIP39Wallet(password, mnemonics);

                    HDWallet hdWallet = new HDWallet(keyPair.getEcKeyPair().getPublicKey(),keyPair.getEcKeyPair().getPrivateKey(), keyPair.getAddress(), mnemonics);

                    //WalletFile walletFile = Wallet.createLight(password, keyPair.getEcKeyPair());
                    return hdWallet;
                }).subscribeOn(Schedulers.io());
    }


    public static Flowable<HDWallet> CreateWallet(String username ,String password,
                                                  String mnemonics) {


        return Flowable.just(mnemonics)
                .map(s -> {
                    Credentials keyPair = importBIP39Wallet(password, mnemonics);

                    HDWallet hdWallet = new HDWallet(username,password,keyPair.getEcKeyPair().getPublicKey(),keyPair.getEcKeyPair().getPrivateKey(), keyPair.getAddress(), mnemonics);



                    //WalletFile walletFile = Wallet.createLight(password, keyPair.getEcKeyPair());
                    return hdWallet;
                }).subscribeOn(Schedulers.io());
    }



    /**
     * @param @param  privateKey
     * @param @return 参数
     * @return Credentials    返回类型
     * @Title: privateKey
     * @Description: TODO(私钥获取凭证)
     * @author yan
     * @date 2018年8月5日 下午6:06:44
     * @version v1.0
     */
    public static Credentials privateKey(String privateKey) {
        Credentials credentials = Credentials.create(privateKey);
        return credentials;
    }

    /**
     * @param @param  web3j
     * @param @param  credentials
     * @param @param  toAddress
     * @param @param  value
     * @param @param  unit
     * @param @return 参数
     * @return String    返回类型
     * @Title: ethTransfer
     * @Description: TODO(eth 转账)
     * @author yan
     * @date 2018年8月5日 下午5:c_14:55
     * @version v1.0
     */
    public String ethTransfer(Web3j web3j, Credentials credentials, String toAddress, BigDecimal value, Unit unit) {
        try {
            TransactionReceipt transactionReceipt = Transfer.sendFunds(web3j, credentials, toAddress, value, unit).send();
            return transactionReceipt.getTransactionHash();
        } catch (Exception e) {
            // TODO Auto-generated catch block
           // LOG.error("eth transfer error...", e);
            Log.e(this.getClass().getSimpleName(),e.getMessage());
        }
        return null;
    }

    /**
     * @param nonce
     * @param gasPrice
     * @param gasLimit
     * @param to
     * @param value
     * @param credentials
     * @return String
     * @Title: signEth
     * @Description: ETH签名
     * @date 2018年7月10日下午2:46:48
     */
    public String signEth(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to, BigInteger value, Credentials credentials) {
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, value);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        return Numeric.toHexString(signedMessage);
    }

    public static void main(String[] args) {
        WalletUtil test = new WalletUtil();
//		Credentials credentials = test.importBIP39Wallet("yanning", "dress emerge found danger brisk spin worth wine supreme brand track front");
        Credentials credentials = test.privateKey("c7d89638851d499213bfcbdbbd59cf599a4c344f4ce682f9ffaa24d56db43350");
        HttpService httpService = new HttpService("http://192.168.1.240:8545");
        Web3j web3j = Web3jFactory.build(httpService);
        String txHash = test.ethTransfer(web3j, credentials, "0xfc8eB7280045ba6536339973DC77f8611EA7159A", BigDecimal.valueOf(0.001016), Convert.Unit.ETHER);
        System.out.println("txhash = " + txHash);
    }


    public static boolean isETHValidAddress(String input) {
        if (TextUtils.isEmpty(input) || !input.startsWith("0x"))
            return false;
        return WalletUtils.isValidAddress(input);
    }

}
