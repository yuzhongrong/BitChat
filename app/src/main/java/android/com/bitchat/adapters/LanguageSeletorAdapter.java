package android.com.bitchat.adapters;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjwsc.idcm.language.LanguageSettingBean;

import java.util.List;

import android.com.bitchat.R;

/**
 * Created by hpz on 2018/2/1.
 */

public class LanguageSeletorAdapter extends BaseQuickAdapter<LanguageSettingBean,BaseViewHolder> {

    private int currentPosition;
    private int[] mLanguageIcons = {
            R.mipmap.icon_en,
            R.mipmap.icon_jan,
            R.mipmap.icon_kro,
            R.mipmap.icon_hk,
            R.mipmap.icon_fran,
            R.mipmap.icon_xibanya,
            R.mipmap.icon_helan,
            R.mipmap.icon_yuenan,
            R.mipmap.icon_china,
    };

    public LanguageSeletorAdapter(@Nullable List<LanguageSettingBean> data) {
        super(R.layout.item_language, data);
    }

    public void setCurrentPosition(int position) {
        this.currentPosition = position;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, LanguageSettingBean item) {
        int position = helper.getAdapterPosition();
        if (position == currentPosition) {
            helper.getView(R.id.ra_btn).setVisibility(View.VISIBLE);
            helper.getView(R.id.ra_btn_select).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.ra_btn).setVisibility(View.GONE);
            helper.getView(R.id.ra_btn_select).setVisibility(View.VISIBLE);
        }
        helper.setText(R.id.img_name, item.getName());
        helper.setImageResource(R.id.img_tag,mLanguageIcons[position]);
    }
}

