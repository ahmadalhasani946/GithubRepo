package GithubRepo.models;

public class Contributor implements Comparable<Contributor> {
    private String repositoryName;
    private String userName;
    private int contributionQuantity;
    private int followersQuantity;

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getContributionQuantity() {
        return contributionQuantity;
    }

    public void setContributionQuantity(int contributionQuantity) {
        this.contributionQuantity = contributionQuantity;
    }

    public int getFollowersQuantity() {
        return followersQuantity;
    }

    public void setFollowersQuantity(int followersQuantity) {
        this.followersQuantity = followersQuantity;
    }

    @Override
    public int compareTo(Contributor other) {
        return other.getContributionQuantity() - getContributionQuantity();
    }
}
