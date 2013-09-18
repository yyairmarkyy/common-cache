package com.common.cache.test;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.cache.R;
import com.common.cache.lru.ImageManagerLru;

public class TestAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<ImgBean> datas;

	public TestAdapter(Context context, ArrayList<ImgBean> datas) {
		this.mContext = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vl = null;
		if (null == convertView) {
			vl = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_list, null);
			vl.img = (ImageView) convertView.findViewById(R.id.imageView);
			vl.text = (TextView) convertView.findViewById(R.id.textView);
			convertView.setTag(vl);
		} else {
			vl = (ViewHolder) convertView.getTag();
		}
		ImgBean pb = datas.get(position);
		int w = vl.img.getWidth();
		int h = vl.img.getHeight();
		vl.text.setText(pb.getText());
		ImageManagerLru.from(mContext).displayImage(vl.img, pb.getImgUrl(), R.drawable.ic_launcher, w, h);
		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView text;
	}
}
