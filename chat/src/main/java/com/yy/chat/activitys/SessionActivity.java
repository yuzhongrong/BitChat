package com.yy.chat.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.cjwsc.idcm.Utils.KeyboardUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.Utils.UIUtils;
import com.cjwsc.idcm.Utils.permission.RxPermissionUtils;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionExtClickListener;
import com.lqr.emoji.IEmotionSelectedListener;
import com.lqr.emoji.LQREmotionKit;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;
import com.lqr.imagepicker.ui.ImageGridActivity;
import com.lqr.imagepicker.ui.ImagePreviewActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yy.chat.R;
import com.yy.chat.audio.AudioPlayManager;
import com.yy.chat.audio.AudioRecordManager;
import com.yy.chat.audio.IAudioPlayListener;
import com.yy.chat.audio.IAudioRecordListener;
import com.yy.chat.bean.ChatMessage;
import com.yy.chat.bean.DateSort;
import com.yy.chat.bean.LocationData;
import com.yy.chat.contasts.AppConst;
import com.yy.chat.db.DBManager;
import com.yy.chat.widget.MessageList;

import org.litepal.crud.callback.FindMultiCallback;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Consumer;

public abstract class SessionActivity extends BaseActivity implements IEmotionSelectedListener {
    private ImageView ic_back;
    protected TextView title;
    protected ImageView ic_right;
    private EmotionKeyboard mEmotionKeyboard;
    private EmotionLayout mElEmotion;
    protected EditText mEtContent;
    LinearLayout mLlRoot;
    ConstraintLayout mLlContent;
    FrameLayout mFlEmotionView;
    ImageView mIvEmo;
    ImageView mIvMore;

    LinearLayout mLlMore;
    //  LQRRecyclerView mRvMsg;
    Button mBtnAudio;
    ImageView mIvAudio;
    Button mBtnSend;
    RelativeLayout mRlAlbum;
    protected LinearLayout ll_friend;
    protected TextView tv_add;
    RelativeLayout mRlTakePhoto;
    RelativeLayout mRlLocation;
    RelativeLayout mRlRedPacket;
    RelativeLayout rl_zhuanzhang;
    protected static MessageList messageList;
    public static final int REQUEST_IMAGE_PICKER = 1000;
    public final static int REQUEST_TAKE_PHOTO = 1001;
    public final static int REQUEST_MY_LOCATION = 1002;
    private boolean mIsFirst = false;
    protected SmartRefreshLayout messagelist_smart;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_session;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        handler = new MyHandler(this);
        mElEmotion = (EmotionLayout) $(R.id.elEmotion);

        mEtContent = (EditText) $(R.id.etContent);
        mElEmotion.attachEditText(mEtContent);
        mLlRoot = (LinearLayout) $(R.id.llRoot);
        mLlContent = (ConstraintLayout) $(R.id.llContent);
        mFlEmotionView = (FrameLayout) $(R.id.flEmotionView);
        mIvEmo = (ImageView) $(R.id.ivEmo);
        mIvMore = (ImageView) $(R.id.ivMore);
        mLlMore = (LinearLayout) $(R.id.llMore);
        //  mRvMsg= (LQRRecyclerView) $( R.id.rvMsg);
        mBtnAudio = (Button) $(R.id.btnAudio);
        mIvAudio = (ImageView) $(R.id.ivAudio);
        mBtnSend = (Button) $(R.id.btnSend);
        mRlAlbum = (RelativeLayout) $(R.id.rlAlbum);
        mRlTakePhoto = (RelativeLayout) $(R.id.rlTakePhoto);
        mRlLocation = (RelativeLayout) $(R.id.rlLocation);
        mRlRedPacket = (RelativeLayout) $(R.id.rlRedPacket);
        rl_zhuanzhang = (RelativeLayout) $(R.id.rl_zhuanzhang);
        messageList = (MessageList) $(R.id.messagelist);
        messagelist_smart = (SmartRefreshLayout) $(R.id.messagelist_smart);
        ll_friend = (LinearLayout) $(R.id.ll_friend);
        tv_add = (TextView) $(R.id.tv_add);
        mFlEmotionView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mFlEmotionView.getVisibility() == View.VISIBLE) {
                    messageList.refreshSelectLast();
                }
            }
        });
        initToolBar();//初始化标题
        initEmotionKeyboard();//初始化表情
        initAudioRecordManager();//初始化录音
        ontouch();
    }

    /**
     * 这里可让子类重写，以便设置标题栏
     **/
    public void initToolBar() {
        ic_back = (ImageView) $(R.id.ic_back);
        title = (TextView) $(R.id.title);
        ic_right = (ImageView) $(R.id.ic_right);
        title.setText(getString(R.string.str_chat));

    }


    private void initEmotionKeyboard() {
        mEmotionKeyboard = EmotionKeyboard.with(this);

        mEmotionKeyboard.bindToEditText(mEtContent);
        mEmotionKeyboard.bindToContent(mLlContent);
        mEmotionKeyboard.setEmotionLayout(mFlEmotionView);
        mIvEmo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mElEmotion.getVisibility() == View.VISIBLE) {
                    mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
                    mElEmotion.setVisibility(View.GONE);
                    mEmotionKeyboard.hide();
                } else {
                    mIvEmo.setImageResource(R.mipmap.ic_cheat_keyboard);
                    mElEmotion.setVisibility(View.VISIBLE);
                    mEmotionKeyboard.show();
                }
                mLlMore.setVisibility(View.GONE);
                hideAudioButton();
            }
        });

        mIvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLlMore.getVisibility() == View.VISIBLE) {
                    mLlMore.setVisibility(View.GONE);
                    mEmotionKeyboard.hide();
                } else {
                    mLlMore.setVisibility(View.VISIBLE);
                    mEmotionKeyboard.show();
                }
                mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
                mElEmotion.setVisibility(View.GONE);
                hideAudioButton();
            }
        });

        mEmotionKeyboard.setUnlock(new EmotionKeyboard.unlock() {
            @Override
            public void back() {

            }
        });

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mElEmotion.getLayoutParams();
        int height = LQREmotionKit.dip2px(35 + 26 + 50) + UIUtils.getDisplayWidth() / 7 * 3;

        layoutParams.height = height;
        mElEmotion.setLayoutParams(layoutParams);
        mLlMore.setLayoutParams(layoutParams);
    }


    private void initAudioRecordManager() {
        AudioRecordManager.getInstance(this).setMaxVoiceDuration(AppConst.DEFAULT_MAX_AUDIO_RECORD_TIME_SECOND);
        File audioDir = new File(AppConst.AUDIO_SAVE_DIR);
        if (!audioDir.exists()) {
            audioDir.mkdirs();
        }
        AudioRecordManager.getInstance(this).setAudioSavePath(audioDir.getAbsolutePath());
        AudioRecordManager.getInstance(this).setAudioRecordListener(new IAudioRecordListener() {

            private TextView mTimerTV;
            private TextView mStateTV;
            private ImageView mStateIV;
            private PopupWindow mRecordWindow;

            @Override
            public void initTipView() {
                View view = View.inflate(SessionActivity.this, R.layout.popup_audio_wi_vo, null);
                mStateIV = (ImageView) view.findViewById(R.id.rc_audio_state_image);
                mStateTV = (TextView) view.findViewById(R.id.rc_audio_state_text);
                mTimerTV = (TextView) view.findViewById(R.id.rc_audio_timer);
                mRecordWindow = new PopupWindow(view, -1, -1);
                mRecordWindow.showAtLocation(mLlRoot, 17, 0, 0);
                mRecordWindow.setFocusable(true);
                mRecordWindow.setOutsideTouchable(false);
                mRecordWindow.setTouchable(false);
            }

            @Override
            public void setTimeoutTipView(int counter) {
                if (this.mRecordWindow != null) {
                    this.mStateIV.setVisibility(View.GONE);
                    this.mStateTV.setVisibility(View.VISIBLE);
                    this.mStateTV.setText(R.string.voice_rec);
                    //   this.mStateTV.setBackgroundResource(R.drawable.bg_voice_popup);
                    this.mStateTV.setBackgroundResource(0);
                    this.mTimerTV.setText(String.format("%s", new Object[]{Integer.valueOf(counter)}));
                    this.mTimerTV.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void setRecordingTipView() {
                if (this.mRecordWindow != null) {
                    this.mStateIV.setVisibility(View.VISIBLE);
                    this.mStateIV.setImageResource(R.mipmap.ic_volume_1);
                    this.mStateTV.setVisibility(View.VISIBLE);
                    this.mStateTV.setText(R.string.voice_rec);
//                      this.mStateTV.setBackgroundResource(R.drawable.bg_voice_popup);
                    this.mStateTV.setBackgroundResource(0);
                    this.mTimerTV.setVisibility(View.GONE);
                }
            }

            @Override
            public void setAudioShortTipView() {
                if (this.mRecordWindow != null) {
                    mStateIV.setImageResource(R.mipmap.ic_volume_wraning);
                    mStateTV.setText(R.string.voice_short);
                }
            }

            @Override
            public void setCancelTipView() {
                if (this.mRecordWindow != null) {
                    this.mTimerTV.setVisibility(View.GONE);
                    this.mStateIV.setVisibility(View.VISIBLE);
                    this.mStateIV.setImageResource(R.mipmap.ic_volume_cancel);
                    this.mStateTV.setVisibility(View.VISIBLE);
                    this.mStateTV.setText(R.string.voice_cancel);
                    this.mStateTV.setBackgroundResource(R.drawable.corner_voice_style);
                }
            }

            @Override
            public void destroyTipView() {
                if (this.mRecordWindow != null) {
                    this.mRecordWindow.dismiss();
                    this.mRecordWindow = null;
                    this.mStateIV = null;
                    this.mStateTV = null;
                    this.mTimerTV = null;
                }
            }

            @Override
            public void onStartRecord() {
                // RongIMClient.getInstance().sendTypingStatus(mConversationType, mSessionId, VoiceMessage.class.getAnnotation(MessageTag.class).value());
            }

            @Override
            public void onAudioDBChanged(int db) {
                switch (db / 5) {
                    case 0:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_1);
                        break;
                    case 1:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_2);
                        break;
                    case 2:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_3);
                        break;
                    case 3:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_4);
                        break;
                    case 4:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_5);
                        break;
                    case 5:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_6);
                        break;
                    case 6:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_7);
                        break;
                    default:
                        this.mStateIV.setImageResource(R.mipmap.ic_volume_8);
                }
            }

            @Override
            public void onFinish(Uri audioPath, int duration) {
                mBtnAudio.setBackground(getResources().getDrawable(R.drawable.shape_session_btn_voice_normal));
                mBtnAudio.setText("按住 说话");
                if (audioPath == null) return;
                //发送文件
                File file = new File(audioPath.getPath());
                if (file.exists()) {
                    sendAudioFile(audioPath, duration);
                }
            }

        });
    }

    public void set() {
        MediaRecorder mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!mIsFirst) {
            mEtContent.clearFocus();
        } else {
            mIsFirst = false;
        }
        //  mPresenter.resetDraft();

    }

    @Override
     protected void onEventListener() {

        initListener();
    }


    private void initListener() {

//        mIbToolbarMore.setOnClickListener(v -> {
//            Intent intent = new Intent(SessionActivity.this, SessionInfoActivity.class);
//            intent.putExtra("sessionId", mSessionId);
//            intent.putExtra("sessionType", mConversationType == mConversationType.PRIVATE ? SessionActivity.SESSION_TYPE_PRIVATE : SessionActivity.SESSION_TYPE_GROUP);
//            jumpToActivity(intent);
//        });
        mElEmotion.setEmotionSelectedListener(this);
        mElEmotion.setEmotionAddVisiable(false);
        mElEmotion.setEmotionSettingVisiable(false);
        mElEmotion.setEmotionExtClickListener(new IEmotionExtClickListener() {
            @Override
            public void onEmotionAddClick(View view) {
                ToastUtil.show("add");
            }

            @Override
            public void onEmotionSettingClick(View view) {
                ToastUtil.show("setting");
            }
        });
        mLlContent.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    closeBottomAndKeyboard();
                    break;
            }
            return false;
        });
        messageList.setOnTouchListener((v, event) -> {
            closeBottomAndKeyboard();
            return false;
        });
        mIvAudio.setOnClickListener(v -> {
            if (mBtnAudio.isShown()) {
                hideAudioButton();
                mEtContent.requestFocus();
                if (mEmotionKeyboard != null) {
                    mEmotionKeyboard.showSoftInput();

                }
            } else {
                mEtContent.clearFocus();
                showAudioButton();
                hideEmotionLayout();
                hideMoreLayout();
            }


        });
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtContent.getText().toString().trim().length() > 0) {
                    mBtnSend.setVisibility(View.VISIBLE);
                    mIvMore.setVisibility(View.GONE);
                    //  RongIMClient.getInstance().sendTypingStatus(mConversationType, mSessionId, TextMessage.class.getAnnotation(MessageTag.class).value());
                } else {
                    mBtnSend.setVisibility(View.GONE);
                    mIvMore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtContent.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                //   UIUtils.postTaskDelay(() -> mRvMsg.smoothMoveToPosition(mRvMsg.getAdapter().getItemCount() - 1), 50);
                //    RxTimerUtil.timer(50,TimeUnit.MILLISECONDS,n->mRvMsg.smoothMoveToPosition(mRvMsg.getAdapter().getItemCount() - 1));


            }
        });
        mBtnSend.setOnClickListener(v -> {//发送文字
            // sendTextMsg(getView().getEtContent().getText().toString());
            //    messageList.addData(ChatMessage.createTextSendMessage(mEtContent.getText().toString(),""));
            sendMessage(new ChatMessage(mEtContent.getText().toString(), ChatMessage.Type.TEXT));
            mEtContent.setText("");

        });
        mBtnAudio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RxPermissionUtils.requestPermission(SessionActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, new RxPermissionUtils.PermissionsResultCallback() {
                    @Override
                    public void onSuccessPermission() {

                    }

                    @Override
                    public void onFailPermission() {

                    }
                });
                if (new RxPermissions(SessionActivity.this).isGranted(Manifest.permission.RECORD_AUDIO)) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            mBtnAudio.setBackground(getResources().getDrawable(R.drawable.shape_session_btn_voice_normal2));
                            mBtnAudio.setText("松开 结束");
                            AudioPlayManager.getInstance().stopPlay();
                            AudioRecordManager.getInstance(SessionActivity.this).startRecord();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            mBtnAudio.setBackground(getResources().getDrawable(R.drawable.shape_session_btn_voice_normal2));
                            if (isCancelled(v, event)) {
                                AudioRecordManager.getInstance(SessionActivity.this).willCancelRecord();
                            } else {
                                AudioRecordManager.getInstance(SessionActivity.this).continueRecord();
                            }
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            mBtnAudio.setBackground(getResources().getDrawable(R.drawable.shape_session_btn_voice_normal));
                            mBtnAudio.setText("按住 说话");
                            AudioRecordManager.getInstance(SessionActivity.this).stopRecord();
                            AudioRecordManager.getInstance(SessionActivity.this).destroyRecord();
                            break;
                        case MotionEvent.ACTION_UP:
                            mBtnAudio.setBackground(getResources().getDrawable(R.drawable.shape_session_btn_voice_normal));
                            mBtnAudio.setText("按住 说话");
                            AudioRecordManager.getInstance(SessionActivity.this).stopRecord();
                            AudioRecordManager.getInstance(SessionActivity.this).destroyRecord();
                            break;
                    }
                }
                return false;
            }
        });

        mRlAlbum.setOnClickListener(v -> {
            ImagePicker imagePicker = ImagePicker.getInstance();
            imagePicker.setMultiMode(true);
            imagePicker.setShowCamera(false);

            imagePicker.setCrop(false);
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_IMAGE_PICKER);
        });
        mRlTakePhoto.setOnClickListener(v -> {
            RxPermissionUtils.requestPermission(this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    },
                    new RxPermissionUtils.PermissionsResultCallback() {
                        @Override
                        public void onSuccessPermission() {
                        }

                        @Override
                        public void onFailPermission() {

                        }
                    });

            if (new RxPermissions(this).isGranted(Manifest.permission.CAMERA)
                    && new RxPermissions(this).isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && new RxPermissions(this).isGranted(Manifest.permission.RECORD_AUDIO)) {
                Intent intent = new Intent(SessionActivity.this, TakePhotoActivity.class);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }

        });
        mRlLocation.setOnClickListener(v -> {
//            new RxPermissions(this).requestEach(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                    .subscribe(new Consumer<Permission>() {
//                        @Override
//                        public void accept(Permission permission) throws Exception {
//                            if (permission.granted) {
//                                Intent intent = new Intent(SessionActivity.this, MapActivity.class);
//                                startActivityForResult(intent, REQUEST_MY_LOCATION);
//                            }
//                        }
//                    });

        });
        mRlRedPacket.setOnClickListener(v -> {
            //发送红包
            sendRedEnvelope();
        });
        rl_zhuanzhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferAccounts();
            }
        });
        KeyboardUtil.KeyBoardListner(this, mEtContent, new KeyboardUtil.OnKeyBoardListener() {
            @Override
            public void showKeyBoard() {
                messageList.refreshSelectLast();

            }

            @Override
            public void hideKeyBoard() {

            }
        });


    }

    @Override
    protected BaseView getView() {
        return null;
    }

    public void sendAudioFile(Uri audioPath, int duration) {
        if (audioPath != null) {
            File file = new File(audioPath.getPath());
            if (!file.exists() || file.length() == 0L) {
                //  LogUtils.sf(UIUtils.getString(R.string.send_audio_fail));
                Logger.d(UIUtils.getString(R.string.send_audio_fail));
                return;
            }
            sendMessage(new ChatMessage(audioPath.getPath(), duration, ChatMessage.Type.VOICE));

        }
    }


    private void showAudioButton() {
        mBtnAudio.setVisibility(View.VISIBLE);
        mEtContent.setVisibility(View.GONE);
        mIvAudio.setImageResource(R.mipmap.ic_cheat_keyboard);

        if (mFlEmotionView.isShown()) {
            if (mEmotionKeyboard != null) {
                mEmotionKeyboard.interceptBackPress();
            }
        } else {
            if (mEmotionKeyboard != null) {
                mEmotionKeyboard.hideSoftInput();
            }
        }
    }


    private void hideAudioButton() {
        mBtnAudio.setVisibility(View.GONE);
        mEtContent.setVisibility(View.VISIBLE);
        mIvAudio.setImageResource(R.mipmap.ic_cheat_voice);
    }

    private void showEmotionLayout() {
        mElEmotion.setVisibility(View.VISIBLE);
        mIvEmo.setImageResource(R.mipmap.ic_cheat_keyboard);
    }

    private void hideEmotionLayout() {
        mElEmotion.setVisibility(View.GONE);
        mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
    }

    private void showMoreLayout() {
        mLlMore.setVisibility(View.VISIBLE);
    }

    private void hideMoreLayout() {
        mLlMore.setVisibility(View.GONE);
    }

    private boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth()
                || event.getRawY() < location[1] - 40) {
            return true;
        }

        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    void ontouch() {
        RecyclerView recyclerView = messageList.getRecycleView();
        if (recyclerView != null)
            recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        View view1 = getCurrentFocus();
                        if (view1 != null) {
                            closeBottomAndKeyboard();
                        }
                    }
                    return false;
                }
            });
    }

    public void closeBottomAndKeyboard() {
        mElEmotion.setVisibility(View.GONE);
        mLlMore.setVisibility(View.GONE);
        if (mEmotionKeyboard != null) {
            mEmotionKeyboard.interceptBackPress();
            mIvEmo.setImageResource(R.mipmap.ic_cheat_emo);
        }
        hideSoftKeyboard();
    }


    @Override
    public void onEmojiSelected(String key) {

    }

    @Override
    public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {
        sendMessage(new ChatMessage(stickerBitmapPath, false));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_PICKER:
                if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {//返回多张照片
                    if (data != null) {
                        //是否发送原图
                        boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
                        ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        for (ImageItem imageItem : images) {
                            if (imageItem != null && !TextUtils.isEmpty(imageItem.path)) {
                                ChatMessage chatMessage = new ChatMessage(imageItem.path, isOrig);
                                sendMessage(chatMessage);
                            }
                        }
                    }
                }
                break;
            case REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    String path = data.getStringExtra("path");
                    if (data.getBooleanExtra("take_photo", true)) {
                        /**发送照片 每个人实现不一样 所以这里要让子类去实现**/
                        //照片
                        sendMessage(new ChatMessage(path, false));
                    } else {
                        /**发送小视频 每个人实现不一样 所以这里要让子类去实现**/
                        //小视频
                        sendMessage(new ChatMessage(path, ChatMessage.Type.VIDEO));
                    }
                }
                break;
            case REQUEST_MY_LOCATION:
                if (resultCode == Activity.RESULT_OK) {
                    /**发送地理位置 每个人实现不一样 所以这里要让子类去实现**/
                    LocationData locationData = (LocationData) data.getSerializableExtra("location");
                    ChatMessage chatMessage = new ChatMessage(locationData.getLat() + "," + locationData.getLng(), ChatMessage.Type.LOCATION);
                    chatMessage.setThumbImg(locationData.getImgUrl());
                    chatMessage.setOrig(false);
                    sendMessage(chatMessage);
                }
                break;
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioPlayManager.getInstance().stopPlay();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioPlayManager.getInstance().stopPlay();
    }

    ImageView ivAudio;

    boolean ispay = false;
    int index;

    static class MyHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public MyHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final Activity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        messageList.getData().get(msg.arg1).setPlay(true);
                        messageList.getAdapter().setSelectedPosition(msg.arg1);
                        break;
                    case 1:
                        messageList.getAdapter().setSelectedPosition(-5);
                        break;
                }
                messageList.getAdapter().notifyDataSetChanged();
            }
        }
    }

    MyHandler handler;

    /**
     * 播放语音
     **/
    protected void startPlayAudio(ChatMessage message, int position) {
        RxPermissionUtils.requestPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                },
                new RxPermissionUtils.PermissionsResultCallback() {
                    @Override
                    public void onSuccessPermission() {
                    }

                    @Override
                    public void onFailPermission() {

                    }
                });

        if (new RxPermissions(this).isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && new RxPermissions(this).isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            if (!TextUtils.isEmpty(message.getBody()) && message.getBody().contains("http")) {
                OkGo.<File>get(message.getBody()).execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {
                        message.setBody(response.body().getPath());
                        DBManager.update(message, null);
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);

                    }
                });
            }
            boolean isplay = message.isPlay();//是否播放过

            if (ispay && index == position) {
                AudioPlayManager.getInstance().stopPlay();
                ispay = false;
                return;
            }
            AudioPlayManager.getInstance().startPlay(SessionActivity.this, Uri.parse(message.getBody()), new IAudioPlayListener() {
                @Override
                public void onStart(Uri var1) {
                    if (!message.isDirction()) {
                        message.setPlay(true);
                        DBManager.update(message, null);
                    }
                    ispay = true;
                    index = position;
                    Message m = new Message();
                    m.what = 0;
                    m.arg1 = position;
                    handler.sendMessage(m);
                }

                @Override
                public void onStop(Uri var1) {
                    Message m = new Message();
                    m.what = 1;
                    m.arg1 = position;
                    handler.sendMessage(m);

                }

                @Override
                public void onComplete(Uri var1) {
                    Message m = new Message();
                    m.what = 1;
                    m.arg1 = position;
                    handler.sendMessage(m);

                    DBManager.getMsgVoice(message.getFrom(), message.getMsgType(), new FindMultiCallback() {
                        @Override
                        public <T> void onFinish(List<T> list) {
                            int p = 0;
                            int nextPosition = 0;//下一个语音的position
                            List<ChatMessage> chatMessages = (List<ChatMessage>) list;
                            if (chatMessages == null) {
                                return;
                            }
                            for (int i = 0; i < chatMessages.size(); i++) {
                                if (chatMessages.get(i).getMsgid().equals(message.getMsgid())) {
                                    p = i;
                                }
                                if ((p + 1) == i) {//获取下一个语音的位置
                                    List<ChatMessage> data = messageList.getData();
                                    Collections.sort(chatMessages, new DateSort());
                                    ChatMessage c = chatMessages.get(i);
                                    for (int j = 0; j < data.size(); j++) {
                                        ChatMessage chatMessage = data.get(j);
                                        if (chatMessage.getMsgid().endsWith(c.getMsgid())) {
                                            nextPosition = j;
                                        }
                                    }
                                }
                            }
                            if (p < list.size() - 1) {//递归循环播放没播放的语音
                                if (!chatMessages.get(p + 1).isPlay() && !isplay)
                                    startPlayAudio(chatMessages.get(p + 1), nextPosition);
                            }

                        }
                    });

                }
            });

        }
    }


    public abstract void sendMessage(ChatMessage content);

    public abstract void sendRedEnvelope();

    public abstract void transferAccounts();

    public void showTransferAccounts() {
        rl_zhuanzhang.setVisibility(View.VISIBLE);
    }
}
