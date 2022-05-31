package br.com.roanrobersson.rshop.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

public final class ResourceUtils {

	public static String getContentFromResource(String resourceName) {
		try {
			InputStream stream = ResourceUtils.class.getResourceAsStream(resourceName);
			if (stream == null) {
				throw new IOException(String.format("Resource '%s' not found", resourceName));
			}
			return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}