import org.junit.Test;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class PullRequestTest {

    private final String GITHUB_TOKEN = System.getenv("GITHUB_TOKEN");

    @Test
    public void getPullRequestWithId1() {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(GITHUB_TOKEN).build();
            var repo = github.getRepository("sahansera/TestRepo");
            var pr = repo.getPullRequest(1);
            var reviewer = pr.getRequestedReviewers();

            assertTrue(reviewer.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPullRequestWithId2() {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(GITHUB_TOKEN).build();
            var repo = github.getRepository("sahansera/TestRepo");
            var pr = repo.getPullRequest(2);
            var reviewer = pr.getUser();

           assertTrue(reviewer.getLogin().equals("ghost"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
