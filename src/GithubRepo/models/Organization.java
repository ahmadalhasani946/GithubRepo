package GithubRepo.models;

public class Organization {
    private String name;
    private int contributers;
    private int forks;

    public Organization(String name, int forks, int contributers){
        this.name = name;
        this.forks = forks;
        this.contributers = contributers;
    }

    public String getName(){
        return this.name;
    }
    public int getContributers(){
        return this.contributers;
    }
    public int getForks(){
        return this.forks;
    }

}
