package tvConfig;

import java.util.List;

public class ConfigJson {
	private final List<TvConfig> tvs;

	public ConfigJson(List<TvConfig> tvs) {
		this.tvs = tvs;
	}

	public List<TvConfig> getTvs() {
		return tvs;
	}

}
