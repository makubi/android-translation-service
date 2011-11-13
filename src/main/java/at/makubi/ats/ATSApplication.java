package at.makubi.ats;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import at.makubi.ats.welcome.WelcomePage;

public class ATSApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return WelcomePage.class;
	}

}
