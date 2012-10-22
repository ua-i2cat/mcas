package cat.i2cat.mcaslite.config.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "transcoConfig")
public class TranscoderConfig implements Serializable {
	
	private static final long serialVersionUID = 5142563434573216847L;
	public static final int FFMPEG 		= 1;
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false, length = 100)
	private String name;
	@Column(nullable = false, length = 255)
	private String inputWorkingDir;
	@Column(nullable = false, length = 255)
	private String outputWorkingDir;
	@Column(nullable = false)
	private int timeout;
	@Column(nullable = false)
	private int threads;
	@Column(nullable = false)
	private int transcoder;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="tConfig", referencedColumnName="id")
	private List<TProfile> profiles;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="tConfig", referencedColumnName="id")
	private List<TLevel> levels;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getTimeout() {
		return timeout;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public int getThreads() {
		return threads;
	}
	
	public void setThreads(int threads) {
		this.threads = threads;
	}
	
	public int getTranscoder() {
		return transcoder;
	}
	
	public void setTranscoder(int transcoder) {
		this.transcoder = transcoder;
	}
	
	public List<TProfile> getProfiles() {
		return profiles;
	}
	
	public void setProfiles(List<TProfile> profiles) {
		this.profiles = profiles;
	}
	
	public List<TLevel> getLevels() {
		return levels;
	}
	
	public void setLevels(List<TLevel> levels) {
		this.levels = levels;
	}

	public String getInputWorkingDir() {
		return inputWorkingDir;
	}

	public void setInputWorkingDir(String inputWorkigDir) {
		this.inputWorkingDir = inputWorkigDir;
	}

	public String getOutputWorkingDir() {
		return outputWorkingDir;
	}

	public void setOutputWorkingDir(String outputWorkigDir) {
		this.outputWorkingDir = outputWorkigDir;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}