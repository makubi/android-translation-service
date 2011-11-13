package at.makubi.ats.welcome;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class WelcomePage extends WebPage {

	public WelcomePage() {
		add(new Label("text", "Welcome to the Android Translation Service"));
	}
}
