package tvConfig;

public class TvConfig {
	private final String tvName;
	private final String tvUrl;

	public TvConfig(String tvName, String tvUrl) {
		this.tvName = tvName;
		this.tvUrl = tvUrl;
	}

	public String getTvName() {
		return tvName;
	}

	public String getTvUrl() {
		return tvUrl;
	}

}
