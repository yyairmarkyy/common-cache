package com.common.cache;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.common.BaseActivity;
import com.common.cache.test.TestLayoutActivity;
import com.common.cache.test.TestListActivity;
import com.common.uitl.Tool;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);
		findButtonById(R.id.buttonLoadAsync).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Tool.startActivity(MainActivity.this, TestListActivity.class);
			}
		});
		findButtonById(R.id.buttonOnLayoutTest).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Tool.startActivity(MainActivity.this, TestLayoutActivity.class);
			}
		});
	}

}
