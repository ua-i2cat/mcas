package cat.i2cat.mcaslite.utils;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import cat.i2cat.mcaslite.config.model.TRequest;
import cat.i2cat.mcaslite.config.model.Transco;
import cat.i2cat.mcaslite.config.model.TranscoderConfig;
import cat.i2cat.mcaslite.exceptions.MCASException;

public class MediaUtils {
	
	public static void createOutputWorkingDir(String id, String outputWorkingDir) throws MCASException {
		File file = new File(TranscoderUtils.getOutputDir(id, outputWorkingDir));
		if (! file.mkdirs()){
			throw new MCASException();
		}
	}
	
	public static void createDestinationDir(String id, String dst) throws MCASException{
		if (! (new File(TranscoderUtils.getDestinationDir(dst, id))).mkdirs()){
			throw new MCASException();
		}
	}
	
	public static boolean deleteInputFile(String requestId, String inputWorkingDir) throws MCASException{
		return deleteFile(FilenameUtils.concat(getWorkDir(inputWorkingDir), requestId));
	}
	
	public static String getWorkDir(String workDir){
		File file = new File(workDir);
		if (! file.isAbsolute()){
			return FilenameUtils.concat(System.getProperty("mcas.home"), workDir);
		}
		return file.getPath();
	}
	
	private static String createWorkDir(String workDir) throws MCASException{
		File file = new File(getWorkDir(workDir));
		if (! file.exists()){
			file.mkdirs();
		} else if (! file.isDirectory()) {
			throw new MCASException();
		}
		return file.getPath();
	}

	public static File setInFile(String id, TranscoderConfig tConfig) throws MCASException {
		try {
			String inputDir = createWorkDir(tConfig.getInputWorkingDir());
			createWorkDir(tConfig.getOutputWorkingDir());
			return new File(FilenameUtils.concat(inputDir, id));
		} catch (Exception e){
			e.printStackTrace();
			throw new MCASException();
		}
	}
	
	public static boolean deleteFile(String file) throws MCASException{
		File fd = new File(file);
		if(fd.isDirectory() && fd.exists()){
			try {
				FileUtils.deleteDirectory(fd);
			} catch (Exception e) {
				e.printStackTrace();
				throw new MCASException();
			}
		}
		else if (fd.exists()){
			return fd.delete();
		}
		return false;
	}

	private static void cleanTransco(Transco transco){
		deleteFile(transco.getInputFile());
		deleteFile(transco.getOutputFile());
	}
	
	private static void cleanTranscos(List<Transco> transcos){
		for(Transco transco : transcos){
			cleanTransco(transco);
		}
	}
	
	public static synchronized void clean(TRequest request){
		if (request.getTranscoded().size() > 0){
			cleanTranscos(request.getTranscoded());
		} else {
			deleteInputFile(request.getIdStr(), request.getTConfig().getInputWorkingDir());
		}
	}

}
