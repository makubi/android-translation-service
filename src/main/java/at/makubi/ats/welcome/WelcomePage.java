package at.makubi.ats.welcome;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.util.io.IOUtils;

import at.makubi.ats.ATSExecutorService;
import at.makubi.ats.ProjectProperties;

public class WelcomePage extends WebPage {

	private static Future<String> submit;
	private final String repoDir = "/tmp";
	private String clonedRepoName;

	public WelcomePage() {
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		add(feedbackPanel);

		final TextField<String> gitUrl = new TextField<String>("gitUrl");
		// gitUrl.add(new UrlValidator());

		ProjectProperties p = new ProjectProperties();
		Form form = new Form("form",
				new CompoundPropertyModel<ProjectProperties>(p));
		form.add(gitUrl);
		form.add(new Button("submitButton") {

			@Override
			public void onSubmit() {
				info(gitUrl.getValue());
				cloneRepo(gitUrl.getValue());
			}

			private void cloneRepo(final String value) {
				System.out.println("cloneRepo");
				Callable<String> pro = new Callable<String>() {

					@Override
					public String call() throws Exception {
						final String cmdGit = "git";
						final String cmdGitClone = "clone";
						clonedRepoName = value.substring(
								value.lastIndexOf("/") + 1,
								value.lastIndexOf("."));

						ProcessBuilder processBuilder = new ProcessBuilder(
								cmdGit, cmdGitClone, value, repoDir
										+ File.separator + clonedRepoName);

						processBuilder.redirectErrorStream(true);

						Process process;
						int waitFor = -1;
						try {
							process = processBuilder.start();

							final InputStream inputStream = process
									.getInputStream();

							waitFor = process.waitFor();

							//
							//
							// info(repoDir+File.separator+clonedRepoName+File.separator+"res/values");
							//
							//
							//
							// info(clonedRepoName);
						} catch (Exception e) {
							error(e.getMessage());
							System.out.println(e.getMessage());
						}
						System.out.println("finished");
						return waitFor == 0 ? "alright" : "error";
					}

				};
				submit = ATSExecutorService.getExecutorService().submit(pro);
			}
		});

		System.out.println(submit);

		if (submit != null && submit.isDone()) {
			try {
				String string = submit.get();
				if (string.equals("alright")) {
					for (File file : new File(repoDir + File.separator
							+ clonedRepoName + File.separator + "res/values")
							.listFiles()) {
						System.out.println(file.getName());
					}
				}
				add(new Label("text", "done"));
			} catch (Exception e) {
				add(new Label("text",
						"Welcome to the Android Translation Service"));
			}
		} else {
			add(new Label("text", "Welcome to the Android Translation Service"));
		}

		add(form);

	}
}
