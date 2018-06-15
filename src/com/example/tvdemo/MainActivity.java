package com.example.tvdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import tvConfig.ConfigJson;
import tvConfig.TvConfig;
import tvadapter.TvAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.gson.Gson;

public class MainActivity extends Activity {
	private ListView tvList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvList = (ListView) findViewById(R.id.tv_list);
		/*
		 * Gson gson = new Gson(); ConfigJson configJson =
		 * gson.fromJson(readJsonFromAssets(), ConfigJson.class);
		 */
		ConfigJson configJson = readTxtFromAssetAndFormatAsJson();
		final TvAdapter adapter = new TvAdapter(configJson.getTvs(), this);
		tvList.setAdapter(adapter);
		tvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = ((TvConfig) adapter.getItem(position)).getTvUrl();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				//intent.setPackage("com.mxtech.videoplayer.ad");
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
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setTitle(R.string.alert).setMessage(R.string.message).create()
				.show();
		return true;
	}

	private String readJsonFromAssets() {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			InputStream inputStream = getAssets().open("tv.json");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			String temp = "";
			temp = reader.readLine();
			while (temp != null) {
				stringBuilder.append(temp).append("\n");
				temp = reader.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(stringBuilder);
	}

	private ConfigJson readTxtFromAssetAndFormatAsJson() {
		List<TvConfig> tvConfigs = new ArrayList<TvConfig>();

		try {
			InputStream inputStream = getAssets().open("tv.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			String temp = null;
			temp = reader.readLine();
			while (temp != null) {
				// stringBuilder.append(temp).append("\n");
				String tvName = temp.substring(0, temp.indexOf(","));
				String tvUrl = temp.substring(temp.indexOf(",") + 1,
						temp.length());
				TvConfig tvConfig = new TvConfig(tvName, tvUrl);
				tvConfigs.add(tvConfig);
				temp = reader.readLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		// return new String(stringBuilder);
		return new ConfigJson(tvConfigs);

	}
}
