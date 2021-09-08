package GithubRepo.controllers;

import GithubRepo.models.Contributor;
import GithubRepo.models.Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.*;
import java.util.*;

public class Github {
    public String account;

    public Github(String account){
        this.account = account;
    }

    public JSONArray getJsonArray(String Url){
        String responseBody = "";
        String response = "";
        int page = 1;
        do{
            String link = Url + page;
            response = Fetcher.getResponse(link);
            responseBody += response;
            if(page != 1 && response.length() > 2){
                int num = responseBody.indexOf("]");
                StringBuilder builder = new StringBuilder(responseBody);
                builder.setCharAt(num, ',');
                builder.deleteCharAt(num + 1);
                responseBody = builder.toString();
            }
            page++;
        } while (response.length() > 2);

        return new JSONArray(responseBody);
    }

    public Repository[] getMostForked(int forks){
        String link = "https://api.github.com/orgs/" + this.account + "/repos?per_page=100&type=public&page=";
        JSONArray repos = getJsonArray(link);
        Repository[] sortedRepos = getRepositoriesForForked(repos);

        if(forks > sortedRepos.length){
            return sortedRepos;
        }
        else {
            Repository[] repositories = new Repository[forks];
            System.arraycopy(sortedRepos, 0, repositories, 0, forks);
            return repositories;
        }
    }

    public Repository[] getRepositoriesForForked(JSONArray repos){
        Repository[] repositories = new Repository[repos.length()];

        for(int i=0; i < repos.length(); i++){
            JSONObject repo = repos.getJSONObject(i);
            Repository repository = new Repository();
            repository.setForksCount(repo.getInt("forks_count"));
            repository.setName(repo.getString("name"));
            repository.setHtmlUrl(repo.getString("html_url"));
            if(repo.get("description") != null){
                repository.setDescription(repo.get("description").toString());
            }
            else {
                repository.setDescription("No Description");
            }
            repository.setContributorsUrl(repo.getString("contributors_url"));
            repositories[i] = repository;
        }

        Arrays.sort(repositories);

        return repositories;
    }

    public List<Contributor> getMostContributers(Repository[] repositories, int Contributers){
        List<Contributor> contributorList = new ArrayList<Contributor>();

        for (Repository value : repositories) {
            JSONArray repository = getJsonArray(value.getContributorsUrl() + "?per_page=100&page=");

            getContributors(contributorList, repository, Contributers, value.getName());
        }

        return contributorList;
    }

    private Map<String, Integer> Contributors = new HashMap<String,Integer>();

    public void getContributors(List<Contributor> contributorList, JSONArray repository, int contributorsNumber, String RepositoryName){
        Contributor[] contributorsArray = new Contributor[repository.length()];

        for(int i=0; i < repository.length(); i++){
            System.out.println((i + 1) + "   Length   " + repository.length());
            JSONObject repo = repository.getJSONObject(i);
            contributorsArray[i] = new Contributor();

            contributorsArray[i].setRepositoryName(RepositoryName);
            contributorsArray[i].setUserName(repo.getString("login"));
            contributorsArray[i].setContributionQuantity(repo.getInt("contributions"));

            int numOfFollowers;

            if(!Contributors.containsKey(contributorsArray[i].getUserName())){
                System.out.println("Sending");
                JSONArray followers = getJsonArray(repo.getString("followers_url") + "?per_page=100&page=");
                numOfFollowers = followers.length();
                Contributors.put(contributorsArray[i].getUserName(), numOfFollowers);
                System.out.println(contributorsArray[i].getUserName() + " -> " + Contributors.get(contributorsArray[i].getUserName()));
            }
            else{
                System.out.println("Exist");
                System.out.println(contributorsArray[i].getUserName() + " -> " + Contributors.get(contributorsArray[i].getUserName()));
                numOfFollowers = Contributors.get(contributorsArray[i].getUserName());
            }

            contributorsArray[i].setFollowersQuantity(numOfFollowers);
        }

        System.out.println("Size Of Contributors is => " + Contributors.size());
        Arrays.sort(contributorsArray);

        pushTheTopContributors(contributorsArray, contributorList, contributorsNumber);
    }

    public void pushTheTopContributors(Contributor[] contributors, List<Contributor> contributorList, int contributorsNumber){
        if(contributorsNumber > contributors.length){
            contributorsNumber = contributors.length;
        }
        contributorList.addAll(Arrays.asList(contributors).subList(0, contributorsNumber));
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
