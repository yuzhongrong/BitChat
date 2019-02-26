package com.yy.chat.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.cjwsc.idcm.Utils.GlideUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.Utils.UIUtils;
import com.cjwsc.idcm.Utils.pwd.PwdCheckUtil;
import com.cjwsc.idcm.base.BaseFragment;
import com.cjwsc.idcm.base.BaseView;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRHeaderAndFooterAdapter;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.recyclerview.LQRRecyclerView;
import com.yy.chat.R;
import com.yy.chat.bean.Friend;
import com.yy.chat.widget.QuickIndexBar;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

public abstract class ContactFragment extends BaseFragment {

    private LQRRecyclerView mRvContacts;
    private QuickIndexBar mQib;
    private TextView mTvLetter;
    protected View mHeaderView, fake_status_bar;
    protected TextView mFooterView;
    protected TextView mTvNewFriendUnread;
    protected FrameLayout mLayoutHeader;
    protected QBadgeView qBadgeView;
    protected LinearLayout llNewFriend;
    private int type = 1;

    protected List<Friend> mData = new ArrayList<>();
    protected LQRHeaderAndFooterAdapter mAdapter;
    private boolean isEidt = false;
    protected ArrayList<String> mGroupMemberList = new ArrayList<>();
    private List<Friend> mSelectedContactList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        mRvContacts = (LQRRecyclerView) $(R.id.rvContacts);
        mQib = (QuickIndexBar) $(R.id.qib);
        mTvLetter = (TextView) $(R.id.tvLetter);
        mLayoutHeader = (FrameLayout) $(R.id.layout_header);
        fake_status_bar = (View) $(R.id.fake_status_bar);
        mFooterView = new TextView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2Px(50));
        mFooterView.setLayoutParams(params);
        mFooterView.setGravity(Gravity.CENTER);
        View headView = initHeadView();
        if (headView == null) {
            mHeaderView = View.inflate(getActivity(), R.layout.contacts_header, null);
        } else {
            mHeaderView = headView;
        }
        llNewFriend = (LinearLayout) mHeaderView.findViewById(R.id.llNewFriend);
        qBadgeView = new QBadgeView(getActivity());
        if (llNewFriend != null)
            qBadgeView.bindTarget(llNewFriend)
                    .setBadgeGravity(Gravity.CENTER | Gravity.END)
                    .setBadgeTextSize(getResources().getDimensionPixelSize(R.dimen.dp8), false)
                    .setGravityOffset(getResources().getDimensionPixelOffset(R.dimen.dp32), false)
                    .setShowShadow(false)
                    .setBadgeNumber(0);//初始化默认设置0
        mTvNewFriendUnread = (TextView) mHeaderView.findViewById(R.id.tvNewFriendUnread);
        setAdapter();
        loadData();


    }

    public void goneStatusBar() {
        if (fake_status_bar != null)
            fake_status_bar.setVisibility(View.GONE);
    }
    public void showStatusBar() {
        if (fake_status_bar != null)
            fake_status_bar.setVisibility(View.VISIBLE);
    }

    @Override
     protected void onEventListener() {

//        mHeaderView.findViewById(R.id.llNewFriend).setOnClickListener(v -> {
//
//              ((MainActivity) getActivity()).mTvContactRedDot.setVisibility(View.GONE);
//            startActivity(new Intent(this,NewFriendActivity.class));
//            mTvNewFriendUnread.setVisibility(View.GONE);
//        });


//        mHeaderView.findViewById(R.id.llGroup).setOnClickListener(v -> ((MainActivity) getActivity()).jumpToActivity(GroupListActivity.class));
        mQib.setOnLetterUpdateListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                //显示对话框
                showLetter(letter);
                //滑动到第一个对应字母开头的联系人
                if ("↑".equalsIgnoreCase(letter)) {
                    mRvContacts.moveToPosition(0);
                } else if ("☆".equalsIgnoreCase(letter)) {
                    mRvContacts.moveToPosition(0);
                } else {
                    List<Friend> data = ((LQRAdapterForRecyclerView) ((LQRHeaderAndFooterAdapter) mRvContacts.getAdapter()).getInnerAdapter()).getData();
                    for (int i = 0; i < data.size(); i++) {
                        Friend friend = data.get(i);
                        String c = friend.getDisplayNameSpelling().charAt(0) + "";
                        if (c.equalsIgnoreCase(letter)) {
                            mRvContacts.moveToPosition(i);
                            break;
                        }
                    }
                }
            }

            @Override
            public void onLetterCancel() {
                //隐藏对话框
                hideLetter();
            }
        });


    }


    public abstract void loadData();// {
//        Flowable.just(DBManager.getInstance().getFriends())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(friends -> {
//                    if (friends != null && friends.size() > 0) {
//                        mData.clear();
//                        mData.addAll(friends);
//                        mFooterView.setText(UIUtils.getString(R.string.count_of_contacts, mData.size()));
//                        //整理排序
//                        SortUtils.sortContacts(mData);
//                        if (mAdapter != null)
//                            mAdapter.notifyDataSetChanged();
//                    }
//                }, this::loadError);


    //   }

    private void testData() {


    }

    private void loadError(Throwable throwable) {
        LogUtils.e(throwable.getLocalizedMessage());
        ToastUtil.show(getResources().getString(R.string.load_contacts_error));
    }

    @Override
    protected BaseView getViewImp() {
        return null;
    }

    @Override
    protected void lazyFetchData() {

    }


    private void showLetter(String letter) {
        mTvLetter.setVisibility(View.VISIBLE);
        mTvLetter.setText(letter);
    }

    private void hideLetter() {
        mTvLetter.setVisibility(View.GONE);
    }

    /**
     * 是否显示快速导航条
     *
     * @param show
     */
    public void showQuickIndexBar(boolean show) {
        if (mQib != null) {
            mQib.setVisibility(show ? View.VISIBLE : View.GONE);
            mQib.invalidate();
        }
    }

    private void setAdapter() {
        if (mAdapter == null) {
            LQRAdapterForRecyclerView adapter = new LQRAdapterForRecyclerView<Friend>(mContext, mData, R.layout.item_contact) {
                @Override
                public void convert(LQRViewHolderForRecyclerView helper, Friend item, int position) {
                    if (!TextUtils.isEmpty(item.getRemarck()) && !item.getRemarck().equals(item.getName())) {
                        helper.setText(R.id.tvName, item.getRemarck());
                    } else if (!TextUtils.isEmpty(item.getGroupMemberNick()) && type == 1) {
                        helper.setText(R.id.tvName, item.getGroupMemberNick());
                    } else {
                        helper.setText(R.id.tvName, item.getName());
                    }

                    ImageView ivHeader = helper.getView(R.id.ivHeader);
                    //  Glide.with(mContext).load(item.getPortraitUri()).centerCrop().into(ivHeader);
                    //
                    GlideUtil.loadImageViewLoding(mContext, item.getPortraitUri(), ivHeader, R.mipmap.ic_user, 0);
                    if (isEidt) {
                        CheckBox edit = helper.getView(R.id.cb);
                        edit.setVisibility(View.VISIBLE);
                        if (!mSelectedContactList.isEmpty()) {
                            if (mSelectedContactList.contains(item)) {
                                edit.setChecked(true);
                            } else {
                                edit.setChecked(false);
                            }
                        } else {
                            edit.setChecked(false);
                        }
                    }

                    String str = "";
                    //得到当前字母
                    if (item.getDisplayNameSpelling() != null && item.getDisplayNameSpelling().length() > 0) {
                        String currentLetter = item.getDisplayNameSpelling().charAt(0) + "";
                        if (!PwdCheckUtil.isLetter(currentLetter)) currentLetter = "#";
                        if (position == 0) {
                            str = currentLetter;
                        } else {
                            //得到上一个字母
                            if (mData.get(position - 1).getDisplayNameSpelling().length() > 0) {
                                //如果和上一个字母的首字母不同则显示字母栏
                                String preLetter = mData.get(position - 1).getDisplayNameSpelling().charAt(0) + "";
                                if (!preLetter.equalsIgnoreCase(currentLetter)) {
                                    str = currentLetter;
                                }
                                //上一个字符存在并且不是字母并且当前字符是#号的
                                if (!TextUtils.isEmpty(preLetter) && !PwdCheckUtil.isLetter(preLetter) && currentLetter.equals("#"))
                                    str = "";

                            }
                        }
                        int nextIndex = position + 1;
                        if (nextIndex < mData.size() - 1) {
                            //得到下一个字母
                            if (mData.get(nextIndex).getDisplayNameSpelling().length() > 0) {
                                String nextLetter = mData.get(nextIndex).getDisplayNameSpelling().charAt(0) + "";
                                //如果和下一个字母的首字母不同则隐藏下划线
                                if (!nextLetter.equalsIgnoreCase(currentLetter)) {
                                    helper.setViewVisibility(R.id.vLine, View.INVISIBLE);
                                } else {
                                    helper.setViewVisibility(R.id.vLine, View.VISIBLE);
                                }
                            }
                        } else {
                            helper.setViewVisibility(R.id.vLine, View.INVISIBLE);
                        }
                    }
                    if (position == mData.size() - 1) {
                        helper.setViewVisibility(R.id.vLine, View.GONE);
                    }

                    //根据str是否为空决定字母栏是否显示
                    if (TextUtils.isEmpty(str)) {
                        helper.setViewVisibility(R.id.tvIndex, View.GONE);
                    } else {
                        helper.setViewVisibility(R.id.tvIndex, View.VISIBLE);
                        helper.setText(R.id.tvIndex, str.toUpperCase());//最终显示要大写
                    }

                    if (!mGroupMemberList.isEmpty()) {
                        if (mGroupMemberList.contains(item.getUserId())) {
                            CheckBox checkBox = helper.getView(R.id.cb);
                            checkBox.setChecked(true);
                        }
                    }
                    helper.getConvertView().setOnClickListener(v -> {

                        handlerListItemClick(item, helper);

                    });
                }

            };
            adapter.addHeaderView(mHeaderView);
            if (mFooterView.getVisibility() != View.GONE) {
                adapter.addFooterView(mFooterView);
            }
            mAdapter = adapter.getHeaderAndFooterAdapter();
            mRvContacts.setAdapter(mAdapter);
        }
    }


    public void setIsEidt(boolean isEidt) {
        this.isEidt = isEidt;
    }

    public void setGroupMemberList(ArrayList<String> memberList) {
        mGroupMemberList.clear();
        mGroupMemberList.addAll(memberList);
    }

    public void setSelectedContactList(List<Friend> selectList) {
        mSelectedContactList.clear();
        mSelectedContactList.addAll(selectList);
    }

    public abstract void handlerListItemClick(Friend item, LQRViewHolderForRecyclerView holder);


    protected View initHeadView() {
        return null;
    }

}
