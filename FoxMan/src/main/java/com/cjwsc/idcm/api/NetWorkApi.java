package com.cjwsc.idcm.api;

/**
 * 作者：yzr
 * 电话：licnese
 * 邮箱：licnese@qq.com
 * 版本号：1.0
 * 类描述：   接口Api
 * 备注消息：
 * 修改时间：2016/12/15 下午4:12
 **/
public class NetWorkApi {

    /**
     * 0 测试
     * 1 预发布
     * 2 灰度
     * 3 正式 正式地址必须为3，如果需要改，必须得修改{{@link "com.android.foxidcw.activitys.FindAssetActivity#testEnviromentFill"}}
     */
    public static int API_STATE = 1;

    public static String baseUrl;//文件服务器
    public static int OPENFIRE_PORT;
    public static String OPENFIRE_IP;
    public static String OPENFIRE_NAME;
    public static String walletUrl;
    /**
     * 测试环境
     */
    protected static String TEST_walletUrl = "http://www.vv.com";//预发布环境
    protected static String TEST_URL = "http://192.168.10.250:9999";//局域网测试环境
    public static String TEST_OPENFIRE_IP = "192.168.10.150";
    public static String TEST_OPENFIRE_NAME = "test-a";
    public static int TEST_OPENFIRE_PORT = 5222;

    /**
     * 预发布
     */
    protected static String PRE_walletUrl = "http://192.168.10.252:9001";//预发布环境
    protected static String PRE_URL = "http://192.168.10.252:9999";//预发布环境
    public static String PRE_OPENFIRE_IP = "192.168.10.252";
    public static String PRE_OPENFIRE_NAME = "node2.tigase.yjy";
    public static int PRE_OPENFIRE_PORT = 5222;

    /**
     * uat
     */
    protected static String GRAY_walletUrl = "http://192.168.10.252:9001";//预发布环境
    protected static String GRAY_URL = "http://120.78.7.163:9999";//线上正式环境
    public static String GRAY_OPENFIRE_IP = "im-uat.hetcoinex.com";
    public static String GRAY_OPENFIRE_NAME = "im-uat.hetcoinex.com";
    public static int GRAY_OPENFIRE_PORT = 5222;

    /**
     * 正式
     */
    protected static String RELEASE_walletUrl = "http://192.168.10.252:9001";//预发布环境
    protected static String RELEASE_URL = "//119.23.75.216:9999";//线上正式环境
    public static String RELEASE_OPENFIRE_IP = "im-pro-node1.hetcoinex.com";
    public static String RELEASE_OPENFIRE_NAME = "im-pro-node1.hetcoinex.com";
    public static int RELEASE_OPENFIRE_PORT = 5222;

    private static String[] BASE_URLS = {TEST_URL, PRE_URL, GRAY_URL, RELEASE_URL};
    private static String[] BASE_walletUrl = {TEST_walletUrl, PRE_walletUrl, GRAY_walletUrl, RELEASE_walletUrl};
    private static String[] BASE_OPENFIRE_IP = {TEST_OPENFIRE_IP, PRE_OPENFIRE_IP, GRAY_OPENFIRE_IP, RELEASE_OPENFIRE_IP};
    private static String[] BASE_OPENFIRE_NAME = {TEST_OPENFIRE_NAME, PRE_OPENFIRE_NAME, GRAY_OPENFIRE_NAME, RELEASE_OPENFIRE_NAME};
    private static int[] BASE_OPENFIRE_PORT = {TEST_OPENFIRE_PORT, PRE_OPENFIRE_PORT, GRAY_OPENFIRE_PORT, RELEASE_OPENFIRE_PORT};

    static {
        baseUrl = BASE_URLS[API_STATE];
        OPENFIRE_PORT = BASE_OPENFIRE_PORT[API_STATE];
        OPENFIRE_IP = BASE_OPENFIRE_IP[API_STATE];
        OPENFIRE_NAME = BASE_OPENFIRE_NAME[API_STATE];
        walletUrl = BASE_walletUrl[API_STATE];
    }
}
