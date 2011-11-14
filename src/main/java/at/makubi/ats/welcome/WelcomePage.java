package at.makubi.ats.welcome;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.UrlValidator;

import at.makubi.ats.ProjectProperties;

public class WelcomePage extends WebPage {

	public WelcomePage() {
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);
		
		final TextField gitUrl = new TextField("gitUrl");
//		gitUrl.add(new UrlValidator());
		

		
		
		ProjectProperties p = new ProjectProperties();
		Form form = new Form("form", new CompoundPropertyModel<ProjectProperties>(p));
		form.add(gitUrl);
		form.add(new Button("submitButton") {
			@Override
			public void onSubmit() {
				info(gitUrl.getValue());
				cloneRepo(gitUrl.getValue());
			}

			private void cloneRepo(String value) {
				ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash");
				Process process;
				try {
					process = processBuilder.start();
					OutputStream out = process.getOutputStream();
					
					out.write("cd /tmp\n".getBytes());
					out.flush();
					out.write(("git clone "+value+"\n").getBytes());
					out.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		add(new Label("text", "Welcome to the Android Translation Service"));
		add(form);
		

		
	}
}
