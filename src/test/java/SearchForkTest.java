import org.junit.Test;
import org.kohsuke.github.*;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class SearchForkTest {

    private final String GITHUB_TOKEN = System.getenv("GITHUB_TOKEN");

    // Repo the issue at - https://github.com/hub4j/github-api/issues/1154
    // Example scenario should be moved to their test org once confirmed
    @Test
    public void searchForkedRepos() {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(GITHUB_TOKEN).build();
            var repos = github.searchRepositories()
                    .q("jbang-appstore")
                    .fork(GHRepositorySearchBuilder.Fork.PARENT_AND_FORKS)
                    .order(GHDirection.DESC)
                    .list();
            repos.toList().forEach(ghRepository -> System.out.println(ghRepository.getFullName() + " " + ghRepository.isFork()));

            assertTrue(repos.getTotalCount() == 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Trying out code search on forked repos
    @Test
    public void searchForkedContent() {
        try {
            GitHub github = new GitHubBuilder().withOAuthToken(GITHUB_TOKEN).build();
            PagedSearchIterable<GHContent> r = github.searchContent()
                    .q("addClass")
                    .in("file")
                    .language("js")
                    .fork("true")
                    // ignored unless sort is also set
                    .order(GHDirection.DESC)
                    .list();
            System.out.println(r.getTotalCount());

            assertTrue(r.getTotalCount() > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
