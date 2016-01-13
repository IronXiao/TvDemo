package tvadapter;

import java.util.List;

import tvConfig.TvConfig;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tvdemo.R;

public class TvAdapter extends BaseAdapter {
	private List<TvConfig> configs;
	private Context context;

	public TvAdapter(List<TvConfig> configs, Context context) {
		super();
		this.configs = configs;
		this.context = context;
	}

	@Override
	public int getCount() {
		return configs.size();
	}

	@Override
	public Object getItem(int position) {
		return configs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		public TextView tv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.tv_list, null);
			holder.tv = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);

		} else
			holder = (ViewHolder) convertView.getTag();
		holder.tv.setText(configs.get(position).getTvName().toString());
		return convertView;
	}

}
