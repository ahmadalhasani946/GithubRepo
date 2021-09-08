package GithubRepo.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.json.*;

public class Repository implements Comparable<Repository> {
    private String name;
    private String htmlUrl;
    private String description;
    private String contributorsUrl;
    private int forksCount;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    @JSONPropertyName("html_url")
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
