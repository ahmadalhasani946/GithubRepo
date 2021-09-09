package GithubRepo;

import GithubRepo.controllers.Github;
import GithubRepo.controllers.CSV;

import GithubRepo.models.Contributor;
import GithubRepo.models.Organization;
import GithubRepo.models.Repository;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String name;
        int forked;
        int contributors;

        Scanner sc = new Scanner(System.in);
        System.out.print("Please Enter the Name of The Organization > ");
        name = sc.next();
        System.out.print("Please Enter the Number of The Most Forked > ");
        forked = sc.nextInt();
        System.out.print("Please Enter the Number of The Most Contributors > ");
        contributors = sc.nextInt();

        Organization org = new Organization(name,forked,contributors);

        Github github = new Github(org.getName());
        List<Repository> mostForked = github.getMostForked(org.getForks());
        System.out.println("Repositories Are : ");
        System.out.println();
        for(Repository repo : mostForked){
            System.out.println(repo.getName());
            System.out.println(repo.getForksCount());
            System.out.println(repo.getHtmlUrl());
            System.out.println(repo.getDescription());
            System.out.println();
        }
        System.out.println("hello");
        List<Contributor> mostContributors = github.getMostContributers(mostForked,org.getContributers());
        System.out.println("Contributors Are : ");
        System.out.println();
        for(Contributor contributor : mostContributors){
            System.out.println(contributor.getRepositoryName());
            System.out.println(contributor.getUserName());
            System.out.println(contributor.getContributionQuantity());
            System.out.println(contributor.getFollowersQuantity());
            System.out.println();
        }

        CSV.createForksFile(mostForked, org.getName());
        CSV.createUsersFile(mostContributors, org.getName());
    }
}
