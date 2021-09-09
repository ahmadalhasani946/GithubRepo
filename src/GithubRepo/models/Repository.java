package GithubRepo.models;

import com.google.gson.annotations.SerializedName;
import org.json.*;

public class Repository implements Comparable<Repository> {
    private String name;
    @SerializedName("html_url")
    private String htmlUrl;
    private String description;
    @SerializedName("contributors_url")
    private String contributorsUrl;
    @SerializedName("forks")
    private int forksCount;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setHtmlUrl(String htmlUrl){
        this.htmlUrl = htmlUrl;
    }

    public String getHtmlUrl(){
        return this.htmlUrl;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getDescription(){
        return this.description;
    }

    public void setContributorsUrl(String contributorsUrl){
        this.contributorsUrl = contributorsUrl;
    }

    public String getContributorsUrl(){
        return this.contributorsUrl;
    }

    public void setForksCount(int forksCount){
        this.forksCount = forksCount;
    }

    public int getForksCount(){
        return this.forksCount;
    }

    @Override
    public int compareTo(Repository other) {
        return other.getForksCount() - getForksCount();
    }

}
