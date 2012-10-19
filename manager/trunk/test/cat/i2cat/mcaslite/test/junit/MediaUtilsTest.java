package cat.i2cat.mcaslite.test.junit;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Test;

import cat.i2cat.mcaslite.exceptions.MCASException;
import cat.i2cat.mcaslite.utils.MediaUtils;

public class MediaUtilsTest {

//	@Test
//	public void toWorkingDirTest(){
//		DAO<TranscoderConfig> tConfigDao = new DAO<TranscoderConfig>(TranscoderConfig.class);
//		try {
//			MediaUtils.toWorkingDir(new URI("file:///etc/fstab"),"thisIsMyId",1);
//			File file = new File(tConfigDao.findById(1).getInputWorkingDir() + "/thisIsMyId");
//			assertTrue(file.exists());
//			assertTrue(file.delete());
//		} catch (MCASException e) {
//			e.printStackTrace();
//			Assert.fail();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//			Assert.fail();
//		}
//		
//	}
	
	@Test
	public void toDestinationUriTest(){
		try {
			MediaUtils.toDestinationUri("/etc/fstab", new URI("file:///home/david/prova.c3po"));
			File file = new File("/home/david/prova.c3po");
			assertTrue(file.exists());
			assertTrue(file.delete());
		} catch (MCASException e) {
			e.printStackTrace();
			Assert.fail();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}


}
