package at.makubi.ats;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ATSExecutorService {

	private static final ExecutorService POOL = Executors.newFixedThreadPool(1);
	
	private ATSExecutorService() {}

	
	public static ExecutorService getExecutorService() {
		return POOL;
	}
}
