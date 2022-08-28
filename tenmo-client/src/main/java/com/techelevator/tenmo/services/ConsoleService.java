package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.util.BasicLogger;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printSendBucksMenu(User[] users, User currentUser){
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.printf("%-12s%s%n","ID","Name");
        System.out.println("-------------------------------------------");
        printUsersIdAndUsername(users, currentUser);
        System.out.println("---------");

    }

    public void printTransferHistory(TransferDTO[] transferDTOS){
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID          From/To                 Amount");
        System.out.println("-------------------------------------------");
        for (TransferDTO transferDTO :
                transferDTOS) {
            transferDTO.printMyTransfer();
        }
        System.out.println("---------");

    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public long promptForLong(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printUsersIdAndUsername(User[] users, User currentUser) {
                for (User user: users){
                    if (!currentUser.equals(user)){
                        System.out.printf("%-12d%s%n",user.getUserId(),user.getUsername());
                    }
                }
    }

    public void printTransferDetails(TransferDTO transferDTO) {
        System.out.println("-------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("-------------------------------------------");
        System.out.println(" Id: " + transferDTO.getTransferId());
        System.out.println(" From: " + transferDTO.getUsernameFrom());
        System.out.println(" To: " + transferDTO.getUsernameTo());
        if (transferDTO.getTransferTypeId()==1){
            System.out.println(" Type: Request");
        } else {
            System.out.println(" Type: Send");
        }

        System.out.print(" Status: ");
        if (transferDTO.getTransferStatusId()==1){
            System.out.println("Pending");
        } else if (transferDTO.getTransferStatusId()==2) {
            System.out.println("Approved");
        } else if (transferDTO.getTransferStatusId()==3){
            System.out.println("Rejected");
        } else {
            BasicLogger.log("Invalid TransferStatusId for transfer ID" + transferDTO.getTransferId() + " || " + transferDTO.toString());
        }

        String formattedBalance = NumberFormat.getCurrencyInstance().format(transferDTO.getAmount());
        System.out.println(" Amount: " + formattedBalance);
    }
}
