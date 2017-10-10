package com.youzi.okredoo.net;

/**
 * Created by hjw on 17/1/24.
 */
public class Api {

    //首页直播列表
    public static final String HOME_LIVING_LIST = "/live/list";
    public static final String LATELY_LIVING_LIST = "/live/newList";
    public static final String GROUP_LIVING_LIST = "/auth/live/groupLive";
    public static final String NEARLY_LIVING_LIST = "/live/local";

    public static final String SEARCHGROUP = "/group/search";
    public static final String HOME_LIVING_BANNER = "/getBanner";
    public static final String MSG_CODE = "/getVerifCode";
    //重置密码获取验证码
    public static final String PASSWORD_MSG_CODE = "/user/getVerifCode";
    public static final String TEL_LOGIN = "/login";
    //账号登录
    public static final String USERNAME_PASSWOED_LOGIN = "/loginPwd";
    public static final String RONGTOKEN = "/auth/im/token";
    public static final String FREND_LIST = "/auth/friend/list";
    public static final String SEARCHFREND = "/auth/user/search";
    public static final String FRENDHISTORY = "/auth/friend/requestHistory";
    public static final String ADD_FRIEND = "/auth/friend/request";
    public static final String BASE_INFO = "/user/baseInfo";
    public static final String DELETE_FRIEND = "/auth/friend/remove";
    public static final String AGREEFREND = "/auth/friend/agree";
    public static final String MOBILE = "/auth/friend/phone";
    public static final String SETFRENNAME = "/auth/friend/setDisplayName";
    public static final String MY_GROUPS = "/auth/group/mys";
    public static final String CREATE_GROUP = "/auth/group/add";
    public static final String GROUP_INFO = "/group/info";
    public static final String GROUP_MEMBER = "/group/user/list";
    public static final String ADDPEPLE = "/auth/group/user/add";
    public static final String USER_INFO_IN_GROUP = "/group/user";
    public static final String MODIFY_GROUP_NAME = "/auth/group/setGroupName";
    public static final String MODIFY_GROUPmyname = "/auth/group/setDisplayName";
    public static final String MODIFY_GROUPINTR = "/auth/group/setIntr";
    public static final String MODIFY_GROUPTYPE = "/auth/group/setJoinType";
    public static final String REMOVEGROUP = "/auth/group/remove";
    public static final String GOOUTGROUP = "/auth/group/exit";
    public static final String BLACKADDREMOVE = "/auth/black/addRemove";
    //    public static final String ISFOLLOW = "/auth/follow/isFollow";
    public static final String ISFREND = "/auth/friend/isFriend";
    public static final String ISBLACK = "/auth/black/isBlack";
    public static final String BLACK_LIST = "/auth/black/list";
    public static final String SEND_HONGBAO = "/auth/redpack/send";
    public static final String GETUSERINFO = "/auth/user/info";
    public static final String SET_INFO = "/auth/user/setInfo";
    public static final String FANS_LIST = "/follow/followme/list";
    public static final String FOLLOW_LIST = "/follow/myfollow/list";
    public static final String ATTENTION = "/auth/follow/add";
    public static final String ACCOUNT_BIND = "/auth/user/bindStatus";
    public static final String LAUNCH_REMINDER = "/auth/noties/list";
    public static final String NOTIES_SETTING = "/auth/noties/setting";
    public static final String NOTIES_SETTING_ALL = "/auth/noties/settingAll";

    //背景图
    public static final String BACKGROUND = "/auth/user/setBackground";

    //身份证
    public static final String AUTHINFO = "/auth/user/setAuthInfo";
    //获取提现税收
    public static final String TAX_MONEY_URL = "/auth/pay/rate";


    //红包领取详情
    public static final String RED_PACK_INFO = "/auth/redpack/info";
    //抢红包
    public static final String REDPACK_ROB = "/auth/redpack/rob";
    //抢红包列表
    public static final String RED_PACK_RECIVESS = "/auth/redpack/recives";
    //余额
    public static final String USER_COINS = "/auth/coins";
    public static final String CIRCLE = "/dynamic/public/list";
    //动态评论列表
    public static final String DYNAMIC_COMMENT_LIST = "/dynamic/comment/list";
    //上传背景图
    public static final String UPLOAD_BACKGROUND = "/auth/user/setLiveCover";
    /**
     * 评论回复列表
     */
    public static final String DYNAMIC_REPLY_LIST = "/dynamic/reply/list";

    // 删除评论 /auth/dynamic/comment/remove
    public static final String DYNAMIC_COMMENT_REMOVE = "/auth/dynamic/comment/remove";

    //动态是否赞过
    public static final String DYNAMIC_ISPRAISE = "/auth/dynamic/isPraise";
    //第三方登录
    public static final String THIRD_LOGIN = "/thirdLogin";
    public static final String HOME_LIVING_ATTENTION = "/follows";
    public static final String HOME_LIVING_NEARBY = "/live/local";
    public static final String HOME_SMALL_VIDEO = "/dynamic/video/list";
    public static final String TAKE_INCOME = "/auth/income/out";
    //我的收益
    public static final String ACCOUNT_INCOME = "/auth/income/info";
    public static final String FANSATRIBUTION = "/fans/orderAll";
    public static final String INCOME_RANK = "/income/order";
    public static final String INCOME_AWARD_TUDOUNI = "/auth/income/awardExperience";
    public static final String INCOME_AWARD_LIANGPIAO = "/auth/income/awardHots";


    public static final String DYNAMIC_LIST = "/dynamic/list";
    public static final String DYNAMIC_USER_LIST = "/auth/dynamic/user/list";
    public static final String INCOME_RECORD_LIST = "/auth/income/history";
    public static final String INCOME_PAY_LIST = "/pay/getCoinRule";
    public static final String USER_INFO = "/user/info";
    public static final String HOT_LIST = "/live/hotList";
    public static final String LIVE_LOCAL = "/live/local";
    public static final String STAR_RECOM = "/star/recom";
    public static final String SEARCH = "/star/search";
    public static final String START_LIVING = "/auth/live/starLive";
    public static final String HISTORY_MSG = "/live/lastMsg";
    //直播间在线用户(顶部)
    public static final String ONLINE_USER = "/live/online";
    //直播间在线用户(列表)
    public static final String ONLINE_USER2 = "/live/onlineUsers";
    public static final String IS_FOLLOW = "/auth/follow/isFollow";
    public static final String SHARE_INFO = "/share/info";
    public static final String END_LIVING = "/auth/live/endLive";
    //直播拉流地址获取
    public static final String LIVING_URL = "/auth/live/videoUrl";
    //获取直播信息
    public static final String LIVING_DETAIL = "/live/info";
    public static final String GIFT_LIST = "/gift/list";
    public static final String BARRAGE_GIFT_LIST = "/gift/barrage";
    public static final String ADDFOLLOW = "/auth/follow/add";
    //送礼物
    public static final String SEND_GIFT = "/auth/pay/giftPayment";
    //游客直播间进场
    public static final String INTO_ROOM = "/auth/live/enter";
    //发送弹幕消息
    public static final String SEND_BARRAGE = "/auth/pay/barrage";
    //粉丝贡献榜
    public static final String FANS_RANK = "/fans/order";
    //发布动态
    public static final String PUSH_DYNAMIC = "/auth/dynamic/add";
    //删除动态
    public static final String DELETE_DYNAMIC = "/auth/dynamic/remove";
    public static final String VIDEO_URL = "/video/playUrl";
    public static final String DYNAMIC_DETAIL = "/dynamic/desc";
    public static final String PRAISE_ADD = "/auth/dynamic/praise/add";
    public static final String PRAISE_REMOVE = "/auth/dynamic/praise/remove";
    //动态评论
    public static final String DYNAMIC_ADD_COMMENT = "/auth/dynamic/comment/add";
    public static final String JOINGROUP = "/auth/group/request";
    public static final String AGREEJOINGROUP = "/auth/group/agree";
    public static final String SETINVITE = "/auth/user/setInvite";
    public static final String MYINVITE = "/auth/user/myInvite";
    public static final String INVITELIST = "/auth/invite/list";

    //评论点赞
    public static final String COMMENT_PRAISE = "/auth/dynamic/comment/praise";


    //获取上传鉴权信息
    public static final String UPLOAD_INFO = "/auth/upAuth";
    public static final String BINDACCCOUNT = "/auth/user/bindAccount";


    //发送申请连麦消息
    public static final String INVATE_MIC = "/auth/live/mic";
    //连麦开始
    public static final String MIC_START = "/auth/live/micStart";
    //连麦关闭
    public static final String MIC_CLOSE = "/auth/live/micClose";
    public static final String SENDVIDEO = "/auth/im/sendVideo";
    public static final String BINDPHONE = "/auth/user/bindPhone";

    public static final String ALIAUTH = "/auth/alipay/info";
    public static final String DOAUTH = "/auth/user/doAuth";

    public static final String BUY = "/auth/pay/charge";
    public static final String BUYFINISH = "/auth/pay/charge/finish";
    public static final String DEVOTE = "/auth/user/devote";

    //查询支付状态绑定
    public static final String PAY_STATUS = "/auth/pay/status";
    //支付宝绑定
    public static final String PAY_BIND_ALIPAY = "/auth/pay/bindAlipay";
    //粮票提现
    public static final String WITHDRAW_HOTS = "/auth/pay/cash";
    //佣金提现
    public static final String WITHDRAW_PROFIT = "/auth/pay/cash/shartProfit";

    //版本升级信息
    public static final String UPINFO = "/config/getUpgradeInfo";
    public static final String CONFIG = "/config";
    public static final String LIVERECORD = "/auth/live/vod";

    //举报
    public static final String JUBAO = "/auth/report";

    public static final String ISLIVE = "/auth/live/isliving";

    public static final String DNYRANK = "/dynamic/gift/list";
    public static final String ISLIVING = "/auth/live/isliving";
    public static final String TOTAL = "/fans/total";
    public static final String CHANGE = "/pay/exchangeRule";
    public static final String doCHANGE = "/auth/pay/exchange";
    public static final String FRIEND_BATCH_AGREE = "/auth/friend/batchAgree";
    public static final String GROUP_BATCH_AGREE = "/auth/group/batchAgree";
    public static final String GROUPIDS = "/group/user/ids";
    public static final String GETLIANPIAO = "/auth/account/hots/balance";
    public static final String DEL_HISTORY = "/auth/friend/delHistory";
    public static final String SETTING = "/auth/friend/setting";
    public static final String setUnlookHimCyc = "/auth/friend/setUnlookHimCyc";
    public static final String setForbirdMyCyc = "/auth/friend/setForbirdMyCyc";
    public static final String setStar = "/auth/friend/setStar";
    public static final String GROUP_USER_SEARCH = "/group/user/search";
    public static final String AUTH_GROUP_SETADMIN = "/auth/group/setAdmin";
    public static final String GROUP_TRANSFER = "/auth/group/transfer";
    /*是否同步好友*/
    public static final String FREND_STAMP = "/auth/friend/stamp";
    /*全员禁言*/   /*/auth/group/groupShutup?gid=&shutup=*/
    public static final String GROUP_SHUT_UP = "/auth/group/groupShutup";


    public static final String HAVESHOP = "http://testseller.mall.doubozhibo.com/api/shop/haveShop";

    //观众退出直播间
    public static final String LIVING_PLAYER_EXIT = "/auth/live/exit";
//    "http://api.tudouni.doubozhibo.com/test/auth/live/videoUrl

    public static final String ROOM_INFO = "/auth/live/getRoomInfo";

    //群成员禁言／解禁
    public static final String GROUP_MUTE = "/auth/group/shutup";

    //直播间top用户  /live/topFans?lid=&top=
    public static final String LIVE_TOP_USER = "/live/topFans";

    /**
     * 点赞的列表
     * dynamic/praise/list?uid=&did
     */
    public static final String DYNAMIC_PRAISE_LIST = "/dynamic/praise/list";

    /**
     * 分享到朋友圈
     */
    public static final String SHARE_TO_CIRCLE = "/auth/dynamic/share";

    /**
     * 分享动态到第三方统计
     */
    public static final String SHARE_STATISTICS = "/auth/dynamic/share/3rd";

    /**
     * 我的收益（土豆泥）
     */
    public static final String MY_EXPERIENCE = "/auth/account/experience/statis";
    /**
     * 邀请佣金
     */
    public static final String MY_PROFIT = "/auth/account/profit";

    /**
     * 直播间设置场控
     */
    public static final String LIVE_SET_CONTROL = "/live/setControl";

    /**
     * 获取场控列表
     */
    public static final String LIVE_GET_CONTROL = "/live/control/list";

    /**
     * 直播间踢人
     */
    public static final String LIVE_KICKOUT = "/auth/live/outRoom";

    public static final String VERIFCODE = "/auth/user/getVerifCode";
    public static final String NEWVERIFCODE = "/getVerifCode";
    public static final String VERIFCODE_PROVE = "/verifyCode";
    public static final String VERIFCODE_CHANGE = "/auth/user/verifyCode";
    public static final String BINDPHONECODE = "/auth/user/bindPhone";
    public static final String VERIFPWD = "/auth/user/verifPwd";
    public static final String SETPWD = "/auth/user/setPwd";
    public static final String UNBUNDTHREE = "/auth/user/unBindThird";
    public static final String INVITEINFO = "/auth/invite/info";
    public static final String SETGROUPAGREEN = "/auth/user/setGroupAgree";
}
