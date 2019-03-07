package com.elalex;

import com.elalex.utils.CreatePdfFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class myExe {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);

        logger.info("******************************************************************");
        logger.info("****************hi************");
        logger.info("******************************************************************");

        Scanner scan = new Scanner(System.in);

        //Decide the number of friends
        System.out.print("Enter how many friends: ");
        int numOfFriends = Integer.parseInt(scan.nextLine());
        System.out.print("Enter hmax sum: ");
        int maxSum = Integer.parseInt(scan.nextLine());

        //Create a string array to store the names of your friends
        int arrayOfNames[] = new int[numOfFriends];
        for (int i = 0; i < arrayOfNames.length; i++) {
            System.out.print("Enter the name of friend " + (i+1) + " : ");
            arrayOfNames[i] = Integer.parseInt(scan.nextLine());
        }

        Arrays.sort(arrayOfNames);
        //Now show your friend's name one by one
        for (int i = 0; i < arrayOfNames.length; i++) {
            System.out.print("My friend " + (i+1) + " : ");
            System.out.print(arrayOfNames[i] + "\n");
        }
        int groupCounter = 0;
        int b=0;
        for (int i=0;i<numOfFriends;i++)
        {
            for (int j=i;j<numOfFriends;j++)
            {
              if  ((arrayOfNames[i]  + arrayOfNames[j]) <maxSum)
              {
                  b=(int)Math.pow(2, (j - i - 1));

                      if (i==j)
                      {
                          groupCounter++;
                      }
                      else {
                          groupCounter = groupCounter + b;
                          System.out.print("(2 ^ (j - i - 1)) "+ b+ "\n");
                      }
                  System.out.print("arrayOfNames[i] "  + arrayOfNames[i] +"arrayOfNames[j] "  + arrayOfNames[j]+"groupCounter "+groupCounter+"\n");

              }
            }
        }


        System.out.print("groupCounter" + groupCounter);
        CreatePdfFile.createPdfFile();
        sendEmailController.sendEmail();
    }

}
