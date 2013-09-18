package com.common.cache.test;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.ListView;

import com.common.BaseActivity;
import com.common.cache.R;
import com.common.uitl.Tool;

public class TestListActivity extends BaseActivity {

	private ListView listViewTest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	@Override
	public void initView() {
		setContentView(R.layout.activity_main);
		String[] urls = {
				"http://e.hiphotos.baidu.com/pic/w%3D230/sign=74d1f5d430adcbef013479059cae2e0e/5d6034a85edf8db16f60b0740823dd54564e7450.jpg",
				"http://f.hiphotos.baidu.com/pic/w%3D230/sign=91b5680396dda144da096bb182b7d009/95eef01f3a292df57d9c6005bd315c6034a87347.jpg",
				"http://h.hiphotos.baidu.com/pic/w%3D230/sign=3c5d9bf0f603918fd7d13ac9613c264b/5ab5c9ea15ce36d38a434f203bf33a87e950b15f.jpg",
				"http://f.hiphotos.baidu.com/pic/w%3D230/sign=4e7d978b9345d688a302b5a794c37dab/79f0f736afc3793187d5d891eac4b74542a911cc.jpg",
				"http://h.hiphotos.baidu.com/pic/w%3D230/sign=f0df66781e30e924cfa49b327c096e66/0b46f21fbe096b6301f325200d338744eaf8acdc.jpg",
				"http://d.hiphotos.baidu.com/pic/w%3D230/sign=b17f36dc1f178a82ce3c78a3c603737f/8b13632762d0f703a2d659cc09fa513d2697c5be.jpg",
				"http://b.hiphotos.baidu.com/pic/w%3D230/sign=d0b47e4637d3d539c13d08c00a86e927/2e2eb9389b504fc2cc3ceebde4dde71190ef6d22.jpg",
				"http://e.hiphotos.baidu.com/pic/w%3D230/sign=cf96661df9dcd100cd9cff22428b47be/d043ad4bd11373f07653a38fa50f4bfbfbed04b6.jpg",
				"http://b.hiphotos.baidu.com/pic/w%3D230/sign=cebcde5f9f2f07085f052d03d924b865/0df3d7ca7bcb0a46bb7547296a63f6246b60af4f.jpg",
				"http://d.hiphotos.baidu.com/pic/w%3D230/sign=143dbb87d833c895a67e9f78e1137397/b21c8701a18b87d63c66670f060828381f30fdfe.jpg",
				"http://h.hiphotos.baidu.com/pic/w%3D230/sign=0d7ae85ea9d3fd1f3609a539004f25ce/b7fd5266d016092424e9d96bd50735fae7cd34f5.jpg",
				"http://d.hiphotos.baidu.com/pic/w%3D230/sign=855d5aec7dd98d1076d40b32113eb807/dc54564e9258d109185e65a2d058ccbf6c814d65.jpg",
				"http://h.hiphotos.baidu.com/pic/w%3D230/sign=5f5ce0f8bd3eb13544c7b0b8961ea8cb/ac6eddc451da81cbe68d4fdd5366d016092431b3.jpg",
				"http://d.hiphotos.baidu.com/pic/w%3D230/sign=05c79958b21c8701d6b6b5e5177e9e6e/472309f79052982259ba1e7fd6ca7bcb0b46d484.jpg",
				"http://h.hiphotos.baidu.com/pic/w%3D230/sign=c0bdbd9eb58f8c54e3d3c22c0a282dee/c8177f3e6709c93d1f2375879e3df8dcd00054e8.jpg",
				"http://e.hiphotos.baidu.com/pic/w%3D230/sign=1093b16df01f3a295ac8d2cda924bce3/c83d70cf3bc79f3d641bf483baa1cd11738b298e.jpg",
				"http://b.hiphotos.baidu.com/pic/w%3D230/sign=995dd884a6efce1bea2bcfc99f50f3e8/960a304e251f95cad87a09c4c8177f3e67095230.jpg",
				"http://c.hiphotos.baidu.com/pic/w%3D230/sign=8fdf54e9ae51f3dec3b2be67a4eff0ec/d0c8a786c9177f3e75c0602e71cf3bc79f3d5618.jpg",
				"http://b.hiphotos.baidu.com/pic/w%3D230/sign=b8320a7c3ac79f3d8fe1e3338aa0cdbc/d833c895d143ad4ba01c094383025aafa40f0606.jpg",
				"http://f.hiphotos.baidu.com/pic/w%3D230/sign=87a1f7c3e950352ab161220b6342fb1a/8435e5dde71190efcb946aabce1b9d16fdfa6053.jpg",
				"http://h.hiphotos.baidu.com/pic/w%3D230/sign=9666f68db03533faf5b6942d98d2fdca/dcc451da81cb39db394318d5d1160924aa1830b5.jpg",
				"http://a.hiphotos.baidu.com/pic/w%3D230/sign=ea12e7ba6a63f6241c5d3e00b745eb32/b3b7d0a20cf431add3e22fba4a36acaf2edd9804.jpg",
				"http://f.hiphotos.baidu.com/pic/w%3D230/sign=06bb88d3024f78f0800b9df049300a83/4d086e061d950a7bff5e58430bd162d9f3d3c9ca.jpg",
				"http://g.hiphotos.baidu.com/pic/w%3D230/sign=6c29e2ef10dfa9ecfd2e511452d2f754/242dd42a2834349b9dc1ee76c8ea15ce37d3be7a.jpg",
				"http://d.hiphotos.baidu.com/pic/w%3D230/sign=a728ae49e7cd7b89e96c3d803f254291/8718367adab44aed5d5c1067b21c8701a18bfb27.jpg",
				"http://h.hiphotos.baidu.com/pic/w%3D230/sign=8f0126fa0df3d7ca0cf63875c21ebe3c/0dd7912397dda144ba170d76b3b7d0a20df486d3.jpg",
				"http://b.hiphotos.baidu.com/pic/w%3D230/sign=65adbce696dda144da096bb182b6d009/95eef01f3a292df58984b4e0bd315c6035a873fb.jpg",
				"http://g.hiphotos.baidu.com/pic/w%3D230/sign=5b943ec548540923aa69647da259d1dc/c9fcc3cec3fdfc034460235ed53f8794a4c22638.jpg",
				"http://g.hiphotos.baidu.com/pic/w%3D230/sign=89b16416b58f8c54e3d3c22c0a2b2dee/c8177f3e6709c93d562fac0f9e3df8dcd000546c.jpg",
				"http://d.hiphotos.baidu.com/pic/w%3D230/sign=ce276c0ed0c8a786be2a4d0d5708c9c7/34fae6cd7b899e51e48bf6e043a7d933c8950d39.jpg",
				"http://g.hiphotos.baidu.com/pic/w%3D230/sign=fb5b700050da81cb4ee684ce6267d0a4/1f178a82b9014a90a036de49a8773912b31bee3e.jpg",
				"http://f.hiphotos.baidu.com/pic/w%3D230/sign=34c810fab03533faf5b6942d98d2fdca/dcc451da81cb39db9bedfea2d1160924ab183010.jpg",
				"http://b.hiphotos.baidu.com/pic/w%3D230/sign=7cd01bf921a446237ecaa261a8207246/faf2b2119313b07edb72318b0dd7912396dd8c61.jpg",
				"http://b.hiphotos.baidu.com/pic/w%3D230/sign=7cd01bf921a446237ecaa261a8207246/faf2b2119313b07edb72318b0dd7912396dd8c61.jpg",
				"http://g.hiphotos.baidu.com/pic/w%3D230/sign=b3f397b33bf33a879e6d0719f65d1018/4ec2d5628535e5dd81dbb15c77c6a7efcf1b62b9.jpg",
				"http://c.hiphotos.baidu.com/pic/w%3D230/sign=fd677440d000baa1ba2c40b87711b9b1/ac4bd11373f0820238f528934afbfbedab641b32.jpg",
				"http://d.hiphotos.baidu.com/pic/w%3D230/sign=828f17f55d6034a829e2bf82fb1249d9/bba1cd11728b4710a2b86338c2cec3fdfd0323aa.jpg",
				"http://a.hiphotos.baidu.com/pic/w%3D230/sign=259daa992934349b74066986f9eb1521/3801213fb80e7bec545748622e2eb9389a506ba7.jpg",
				"http://h.hiphotos.baidu.com/pic/w%3D230/sign=0155483ea8773912c4268262c8188675/09fa513d269759eeca3b6e48b3fb43166c22dfce.jpg",
				"http://f.hiphotos.baidu.com/pic/w%3D230/sign=fd859f5f023b5bb5bed727fd06d2d523/b90e7bec54e736d1fa9f12fc9a504fc2d46269b2.jpg",
				"http://a.hiphotos.baidu.com/pic/w%3D230/sign=aab3c343e850352ab161220b6342fb1a/8435e5dde71190efe6865e2bcf1b9d16fcfa60c1.jpg",
				"http://e.hiphotos.baidu.com/pic/w%3D230/sign=7bd20d8cac345982c58ae2913cf5310b/3b292df5e0fe9925503ef0a835a85edf8db1711c.jpg",
				"http://g.hiphotos.baidu.com/pic/w%3D230/sign=256b7977b64543a9f51bfdcf2e168a7b/f3d3572c11dfa9ec1c61f19463d0f703908fc1e2.jpg",
				"http://h.hiphotos.baidu.com/pic/w%3D230/sign=742e2363f603918fd7d13ac9613c264b/5ab5c9ea15ce36d3c230f7b33bf33a87e950b178.jpg"
				
		};
		ArrayList<ImgBean> datas = new ArrayList<ImgBean>();
		for (int i = 0; i < urls.length; i++) {
			ImgBean ib = new ImgBean();
			ib.setImgUrl(urls[i]);
			ib.setText(i+"");
			datas.add(ib);
		}
		TestAdapter tda = new TestAdapter(this, datas);
		listViewTest = (ListView) findViewById(R.id.listViewTest);
		listViewTest.setAdapter(tda);
		Tool.ToastShow(this, urls.length+"张图片。。");
	}
}
