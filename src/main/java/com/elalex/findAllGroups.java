package com.elalex;

import com.elalex.utils.CreatePdfFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class findAllGroups {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);

        logger.info("******************************************************************");
        logger.info("****************hi************");
        logger.info("******************************************************************");

        Scanner scan = new Scanner(System.in);

        //Decide the number of friends
        System.out.print("Enter how many friends: ");
        System.out.println(Integer.toBinaryString(3));
        System.out.println(Integer.toBinaryString(55));
        System.out.println(Integer.toBinaryString(98));
        System.out.println(Integer.toBinaryString(101));
        System.out.println(Integer.toBinaryString(198));
        int numOfFriends = Integer.parseInt(scan.nextLine());


        //Create a string array to store the names of your friends
        String arrayOfNames[] = new String[numOfFriends];
        for (int i = 0; i < arrayOfNames.length; i++) {
            System.out.print("Enter the name of friend " + (i+1) + " : ");
            arrayOfNames[i] = scan.nextLine();
        }

        String binary;
        for(int i=0;i<Math.pow(2,numOfFriends);i++)
        {
            System.out.print('{');
            binary=Integer.toBinaryString(i);
            //System.out.println(binary);
            for(int b1=binary.length()-1; b1>=0; b1--)
            {
                if (binary.charAt(b1)=='1')
                {
                    System.out.print(arrayOfNames[numOfFriends-binary.length()+b1]+"    ");
                }
            }
            System.out.println('}');

        }
    }

}
