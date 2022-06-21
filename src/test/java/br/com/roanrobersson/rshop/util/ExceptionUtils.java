package br.com.roanrobersson.rshop.util;

import org.junit.jupiter.api.function.Executable;

public final class ExceptionUtils {

	public static void ignoreThrows(Executable executable) {
		try {
			executable.execute();
		} catch (Throwable e) {
		}
	}

	public static <T extends Throwable> void ignoreThrowsExactly(Class<T> expectedType, Executable executable)
			throws Throwable {
		try {
			executable.execute();
		} catch (Throwable actualException) {
			if (!expectedType.isInstance(actualException)) {
				throw actualException;
			}
		}
	}
}
