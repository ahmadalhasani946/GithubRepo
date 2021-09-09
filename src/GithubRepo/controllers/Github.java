package GithubRepo.controllers;

import GithubRepo.models.Contributor;
import GithubRepo.models.Repository;
import com.google.gson.Gson;
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

    public List<Repository> getMostForked(int forks){
        String link = "https://api.github.com/orgs/" + this.account + "/repos?per_page=100&type=public&page=";
        JSONArray repos = getJsonArray(link);
        List<Repository> sortedRepos = getRepositoriesForForked(repos);

        if(forks > sortedRepos.size()){
            return sortedRepos;
        }
        else {
            List<Repository> repositories = sortedRepos.subList(0, forks);
            return repositories;
        }
    }

    public List<Repository> getRepositoriesForForked(JSONArray repos){
        List<Repository> repositoryList = new ArrayList<Repository>();

        for(int i=0; i < repos.length(); i++){
            JSONObject repo = repos.getJSONObject(i);
            Gson gson = new Gson();
            Repository repository = gson.fromJson(repo.toString(), Repository.class);
            repositoryList.add(repository);
        }

        Collections.sort(repositoryList);


        return repositoryList;
    }

    public List<Contributor> getMostContributers(List<Repository> repositories, int Contributers){
        List<Contributor> contributorList = new ArrayList<Contributor>();

        for (Repository value : repositories) {
            JSONArray repository = getJsonArray(value.getContributorsUrl() + "?per_page=100&page=");

            getContributors(contributorList, repository, Contributers, value.getName());
        }

        return contributorList;
    }

    private Map<String, Integer> Contributors = new HashMap<String,Integer>();

    public void getContributors(List<Contributor> contributorList, JSONArray repository, int contributorsNumber, String RepositoryName){
        List<Contributor> contributorsList = new ArrayList<Contributor>();

        for(int i=0; i < repository.length(); i++){
            System.out.println((i + 1) + "   Length   " + repository.length());
            JSONObject repo = repository.getJSONObject(i);

            Gson gson = new Gson();
            Contributor contributor = gson.fromJson(repo.toString(), Contributor.class);
            contributor.setRepositoryName(RepositoryName);
            int numOfFollowers;

            if(!Contributors.containsKey(contributor.getUserName())){
                System.out.println("Sending");
                JSONArray followers = getJsonArray(repo.getString("followers_url") + "?per_page=100&page=");
                numOfFollowers = followers.length();
                Contributors.put(contributor.getUserName(), numOfFollowers);
                System.out.println(contributor.getUserName() + " -> " + Contributors.get(contributor.getUserName()));
            }
            else{
                System.out.println("Exist");
                System.out.println(contributor.getUserName() + " -> " + Contributors.get(contributor.getUserName()));
                numOfFollowers = Contributors.get(contributor.getUserName());
            }

            contributor.setFollowersQuantity(numOfFollowers);
            contributorsList.add(contributor);
        }

        System.out.println("Size Of Contributors is => " + Contributors.size());
        Collections.sort(contributorsList);

        pushTheTopContributors(contributorsList, contributorList, contributorsNumber);
    }

    public void pushTheTopContributors(List<Contributor>  contributors, List<Contributor> contributorList, int contributorsNumber){
        if(contributorsNumber > contributors.size()){
            contributorsNumber = contributors.size();
        }
        contributorList.addAll(contributors.subList(0, contributorsNumber));
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
