package eliazarcs.com.starwars.api.util;

import org.springframework.context.ApplicationContext;

public class StarWarsUtil {
	public static ApplicationContext appContext;
	
	public static <T> T getBean(Class<T> cl) {
		return appContext.getBean(cl);
	}
	
}
