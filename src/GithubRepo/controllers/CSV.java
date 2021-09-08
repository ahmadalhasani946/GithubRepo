package GithubRepo.controllers;

import GithubRepo.models.Contributor;
import GithubRepo.models.Repository;
import org.apache.commons.csv.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CSV {
    public static void createForksFile(Repository[] repositories, String accountName) {
        try{
            String outputFile = accountName + "_repos.csv";
            CSVPrinter csvFilePrinter = null;
            CSVFormat csvFileFormat = CSVFormat.DEFAULT;
            FileWriter fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            csvFilePrinter.printRecord("repo", "forks", "url", "description");
            for (Repository repository : repositories) {
                csvFilePrinter.printRecord(repository.getName() , repository.getForksCount() , repository.getHtmlUrl() , repository.getDescription());
            }


            fileWriter.flush();
            fileWriter.close();
            csvFilePrinter.close();
        }
        catch (IOException e){
        }
    }

    public static void createUsersFile(List<Contributor> contributors, String accountName) {
        try{
            String outputFile = accountName + "_users.csv";
            CSVPrinter csvFilePrinter = null;
            CSVFormat csvFileFormat = CSVFormat.DEFAULT;
            FileWriter fileWriter = new FileWriter(outputFile);
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            csvFilePrinter.printRecord("repo", "username", "contributions", "followers");
            for (Contributor contributor : contributors) {
                csvFilePrinter.printRecord(contributor.getRepositoryName() , contributor.getUserName() , contributor.getContributionQuantity() , contributor.getFollowersQuantity());
            }

            fileWriter.flush();
            fileWriter.close();
            csvFilePrinter.close();
        }
        catch (IOException e){
        }
    }
}
