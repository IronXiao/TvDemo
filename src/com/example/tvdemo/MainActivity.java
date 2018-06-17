package com.example.tvdemo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import tvConfig.ConfigJson;
import tvConfig.TvConfig;
import tvadapter.TvAdapter;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView mTvList;
	private boolean mIsAssetType = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTvList = (ListView) findViewById(R.id.tv_list);
		updateList();

	}

	private void updateList() {
		ConfigJson configJson;
		try {
			configJson = readConfig();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(
					MainActivity.this,
					"Please check your config file in "
							+ (mIsAssetType ? "Asset !" : "sdcard !"),
					Toast.LENGTH_SHORT).show();
			return;
		}

		final TvAdapter adapter = new TvAdapter(configJson.getTvs(), this);
		mTvList.setAdapter(adapter);
		Toast.makeText(
				MainActivity.this,
				"Config file now is in "
						+ (mIsAssetType ? "Asset !" : "sdcard !"),
				Toast.LENGTH_SHORT).show();
		mTvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = ((TvConfig) adapter.getItem(position)).getTvUrl();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				// intent.setPackage("com.mxtech.videoplayer.ad");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mIsAssetType = !mIsAssetType;
		updateList();
		return true;
	}

	private ConfigJson readConfig() throws IOException {
		List<TvConfig> tvConfigs = new ArrayList<TvConfig>();
		BufferedReader reader = null;
		@SuppressWarnings("resource")
		InputStream inputStream = mIsAssetType ? getAssets().open("tv.txt")
				: new FileInputStream("/sdcard/tv.txt");
		reader = new BufferedReader(new InputStreamReader(inputStream));
		String temp = null;
		temp = reader.readLine();
		int i = 1;
		while (temp != null) {
			String tvName = i + "." + temp.substring(0, temp.indexOf(","));
			String tvUrl = temp.substring(temp.indexOf(",") + 1, temp.length());
			TvConfig tvConfig = new TvConfig(tvName.trim(), tvUrl.trim());
			tvConfigs.add(tvConfig);
			i++;
			temp = reader.readLine();
		}
		reader.close();
		inputStream.close();
		return new ConfigJson(tvConfigs);
	}
}
