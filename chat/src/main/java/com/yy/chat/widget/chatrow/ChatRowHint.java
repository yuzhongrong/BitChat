package com.yy.chat.widget.chatrow;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.LoginUtils;
import com.yy.chat.R;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.bean.Friend;
import com.yy.chat.db.DBManager;
import com.yy.chat.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatRowHint extends ChatRow {
    TextView textView;

    public ChatRowHint(Context context, int res) {
        super(context, res);
    }

    @Override
    protected void onFindViewById() {
        textView = mView.findViewById(R.id.tv_hint);
    }

    @Override
    public void onSetUpView(ChatMessage textMessageBody) {
        if (textMessageBody.getType() == 1) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(StringUtils.getClickableSpan(mContext, textMessageBody.getBody()));
        } else {
            textView.setText(textMessageBody.getBody());
        }
    }
}
