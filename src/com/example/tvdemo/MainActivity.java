package com.example.tvdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import tvConfig.ConfigJson;
import tvConfig.TvConfig;
import tvadapter.TvAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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

import com.google.gson.Gson;

public class MainActivity extends Activity {
	private ListView tvList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvList = (ListView) findViewById(R.id.tv_list);
		Gson gson = new Gson();
		ConfigJson configJson = gson.fromJson(readJsonFromAssets(),
				ConfigJson.class);
		final TvAdapter adapter = new TvAdapter(configJson.getTvs(), this);
		tvList.setAdapter(adapter);
		tvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = ((TvConfig) adapter.getItem(position)).getTvUrl();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
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

}
