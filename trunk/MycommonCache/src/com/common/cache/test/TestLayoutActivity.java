package com.common.cache.test;

import android.os.Bundle;
import android.widget.ListView;

import com.common.BaseActivity;
import com.common.cache.R;

public class TestLayoutActivity extends BaseActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_test_layout);
	}
}
