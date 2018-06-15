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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ListView tvList;
	private ConfigJson configJson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*
		 * Gson gson = new Gson(); ConfigJson configJson =
		 * gson.fromJson(readJsonFromAssets(), ConfigJson.class);
		 */
		try {
			configJson = readTxtFromAssetAndFormatAsJson();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(MainActivity.this,
					"Please check your config file !", Toast.LENGTH_SHORT)
					.show();
			MainActivity.this.finish();
			return;
		}
		tvList = (ListView) findViewById(R.id.tv_list);
		final TvAdapter adapter = new TvAdapter(configJson.getTvs(), this);
		tvList.setAdapter(adapter);
		tvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = ((TvConfig) adapter.getItem(position)).getTvUrl();
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				// intent.setPackage("com.mxtech.videoplayer.ad");
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

	/*
	 * private String readJsonFromAssets() throws IOException { StringBuilder
	 * stringBuilder = new StringBuilder(); BufferedReader reader = null;
	 * InputStream inputStream = null;
	 * 
	 * try { inputStream = getAssets().open("tv.json"); reader = new
	 * BufferedReader(new InputStreamReader(inputStream)); String temp = "";
	 * temp = reader.readLine(); while (temp != null) {
	 * stringBuilder.append(temp).append("\n"); temp = reader.readLine(); }
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } finally {
	 * reader.close(); inputStream.close(); } return new String(stringBuilder);
	 * }
	 */
	private ConfigJson readTxtFromAssetAndFormatAsJson() throws IOException {
		List<TvConfig> tvConfigs = new ArrayList<TvConfig>();
		BufferedReader reader = null;
		InputStream inputStream = null;
		inputStream = getAssets().open("tv.txt");
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
