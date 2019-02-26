package com.lqr.emoji;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.yy.chat.R;

/**
 * CSDN_LQR
 * 贴图适配器
 */

public class StickerAdapter extends BaseAdapter {

    private Context mContext;
    private StickerCategory mCategory;
    private int startIndex;


    public StickerAdapter(Context context, StickerCategory category, int startIndex) {
        mContext = context;
        mCategory = category;
        this.startIndex = startIndex;
    }


    @Override
    public int getCount() {
        int count = mCategory.getStickers().size() - startIndex;
        count = Math.min(count, EmotionLayout.STICKER_PER_PAGE);
        return count;
    }

    @Override
    public Object getItem(int position) {
        return mCategory.getStickers().get(startIndex + position);
    }

    @Override
    public long getItemId(int position) {
        return startIndex + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StickerViewHolder viewHolder;
        if (convertView == null) {
            SquareLayout rl = new SquareLayout(mContext);
            rl.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));

            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.ic_tab_emoji);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            rl.setGravity(Gravity.CENTER);
            int p = mContext.getResources().getDimensionPixelOffset(R.dimen.dp8);
            rl.setPadding(p, p, p, p);
            imageView.setLayoutParams(params);

            rl.addView(imageView);

            viewHolder = new StickerViewHolder();
            viewHolder.mImageView = imageView;

            convertView = rl;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (StickerViewHolder) convertView.getTag();
        }

        int index = startIndex + position;
        if (index >= mCategory.getStickers().size()) {
            return convertView;
        }

        StickerItem sticker = mCategory.getStickers().get(index);
        if (sticker == null) {
            return convertView;
        }

        String stickerBitmapUri = StickerManager.getInstance().getStickerBitmapUri(sticker.getCategory(), sticker.getName());
        LQREmotionKit.getImageLoader().displayImage(mContext, stickerBitmapUri, viewHolder.mImageView);
        return convertView;
    }

    class StickerViewHolder {
        public ImageView mImageView;
    }
}
