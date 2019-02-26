package android.com.bitchat.utils

import com.cjwsc.idcm.Utils.TimeUtils
import com.yy.chat.bean.ChatMessage

class MessageUtils{


    companion object {

        /**封装接收到的单聊消息**/
        fun makeReceiveMsg(address:String,type:Int,msg:String):ChatMessage{

            return ChatMessage()?.apply {
                from=address//消息来源
                setType(type)//消息类型
                body=msg//消息内容
                isDirction=false//消息方向
                timeStamp=System.currentTimeMillis()

            }

        }
    }




}
