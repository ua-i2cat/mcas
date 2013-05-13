package cat.i2cat.mcaslite.utils;

import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;

import cat.i2cat.mcaslite.config.dao.DAO;
import cat.i2cat.mcaslite.config.model.TDASHOptions;
import cat.i2cat.mcaslite.config.model.THLSOptions;
import cat.i2cat.mcaslite.config.model.TLevel;
import cat.i2cat.mcaslite.config.model.TProfile;
import cat.i2cat.mcaslite.config.model.TranscoderConfig;
import cat.i2cat.mcaslite.exceptions.MCASException;


public class DefaultsLoader {
	
	public static String DEFAULT = "default";
	
	public DefaultsLoader(String path){
		this.path = path;
	}
	
	private String path;

	private TranscoderConfig getConfig(Element config) throws MCASException{
		TranscoderConfig tConfig = new TranscoderConfig();
		tConfig.setName(config.getAttributeValue("name"));
		tConfig.setInputWorkingDir(XMLReader.getStringParameter(config, "workdir.input"));
		tConfig.setOutputWorkingDir(XMLReader.getStringParameter(config, "workdir.output"));
		tConfig.setTimeout(XMLReader.getIntParameter(config, "timeout"));
		tConfig.setLive(Boolean.parseBoolean(XMLReader.getStringParameter(config, "live")));
		tConfig.setProfiles(getConfigProfiles(config.getChild("profiles").getChildren("profile")));
		return tConfig;
	}
	
	private List<TProfile> getConfigProfiles(List<Element> configProfiles) throws MCASException {
		DAO<TProfile> profileDao = new DAO<TProfile>(TProfile.class);
		List<TProfile> tProfiles = new ArrayList<TProfile>();
		for(Element el : configProfiles){
			TProfile profile = profileDao.findByName(el.getAttributeValue("name"));
			profile.setLevels(getProfileLevels(getProfileLevelsName(el)));
			tProfiles.add(profile);
		}
		return tProfiles;
	}

	private TProfile getProfile(Element profile) throws MCASException{
		String classAtr = profile.getAttributeValue("class");
		if (classAtr != null && classAtr.equals("HLS")){
			return getHLSProfile(profile);
		} else if (classAtr != null && classAtr.equals("DASH")){
			return getDASHProfile(profile);
		} else {
			TProfile tProfile = new TProfile();
			setStdProfile(tProfile, profile);
			return tProfile;
		}
	}
	
	private THLSOptions getHLSProfile(Element profile) throws MCASException{
		THLSOptions hProfile = new THLSOptions(); 
		hProfile.setWindowLength(XMLReader.getIntParameter(profile, "windowLength"));
		hProfile.setSegDuration(XMLReader.getIntParameter(profile, "segDuration"));
		setStdProfile(hProfile, profile);
		return hProfile;
	}
	
	private TDASHOptions getDASHProfile(Element profile) throws MCASException{
		TDASHOptions dProfile = new TDASHOptions();
		dProfile.setSegDuration(XMLReader.getIntParameter(profile, "segDuration"));
		dProfile.setFragDuration(XMLReader.getIntParameter(profile, "fragDuration"));
		setStdProfile(dProfile, profile);
		return dProfile;
	}
	
	private void setStdProfile(TProfile tProfile, Element profile) throws MCASException{
		tProfile.setFormat(XMLReader.getStringParameter(profile, "format"));
		tProfile.setaCodec(XMLReader.getStringParameter(profile, "acodec"));
		tProfile.setvCodec(XMLReader.getStringParameter(profile, "vcodec"));
		tProfile.setName(XMLReader.getElementName(profile));
		tProfile.setAdditionalFlags(XMLReader.getStringParameter(profile, "additionalFlags"));
	}
	
	private List<TLevel> getProfileLevels(List<String> profileLevels) throws MCASException{
		List<TLevel> tLevels = new ArrayList<TLevel>();
		DAO<TLevel> levelDao = new DAO<TLevel>(TLevel.class);
		for (String levelName : profileLevels){
			tLevels.add(levelDao.findByName(levelName));
		}
		return tLevels;
	}
	
	private TLevel getLevel(Element level) throws MCASException{
		TLevel tLevel = new TLevel();
		tLevel.setaBitrate(XMLReader.getIntParameter(level, "abitrate"));
		tLevel.setaChannels(XMLReader.getIntParameter(level, "achannels"));
		tLevel.setName(XMLReader.getElementName(level));
		tLevel.setWidth(XMLReader.getIntParameter(level, "width"));
		tLevel.setQuality(XMLReader.getIntParameter(level, "quality"));
		tLevel.setMaxRate(XMLReader.getIntParameter(level, "maxRate"));
		return tLevel;
	}
	
	private List<String> getProfileLevelsName(Element profile){
		List<String> levelNames= new ArrayList<String>();
		List<Element> profileLevels = profile.getChild("levels").getChildren("level");
		for (Element el : profileLevels){
			levelNames.add(el.getAttributeValue("name"));
		}
		return levelNames;
	}
	

	public void loadDefaults() throws MCASException {
		loadDefaultLevels();
		loadDefaultProfiles();
		loadDefaultPresets();
	}
	
	private void loadDefaultLevels() throws MCASException {
		DAO<TLevel> levelDao = new DAO<TLevel>(TLevel.class);
		XMLReader reader = new XMLReader(path);
		List<Element> levelElements = reader.getRootChildrenElements(reader.getDoc("levels.xml"));
		for(Element el : levelElements){
			levelDao.save(getLevel(el));			
		}
	}
	
	private void loadDefaultProfiles()  throws MCASException {
		DAO<TProfile> profileDao = new DAO<TProfile>(TProfile.class);
		XMLReader reader = new XMLReader(path);
		List<Element> profileElements = reader.getRootChildrenElements(reader.getDoc("profiles.xml"));
		for(Element el : profileElements){
			profileDao.save(getProfile(el));			
		}
	}
	
	private void loadDefaultPresets() throws MCASException {
		DAO<TranscoderConfig> configDao = new DAO<TranscoderConfig>(TranscoderConfig.class);
		XMLReader reader = new XMLReader(path);
		List<Element> configElements = reader.getConfigs(reader.getDoc("config.xml"));
		for (Element el : configElements){
			configDao.save(getConfig(el));
		}
	}

}