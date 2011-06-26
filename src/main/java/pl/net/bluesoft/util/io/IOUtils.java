package pl.net.bluesoft.util.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author tlipski@bluesoft.net.pl
 */
public class IOUtils {
	public static byte[] slurp(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int c=0;
		while ((c = is.read()) > 0) {
			baos.write(c);
		}
		return baos.toByteArray();
	}
}
