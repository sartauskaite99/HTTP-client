package com.company;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {
    private static URL objectUrl;
    private static HttpURLConnection connection;
    public static void main(String[] args) {
	// write your code here
        while (true) {
            HttpClient myClass = new HttpClient();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter URL");
            String url = scanner.nextLine();
            try {
                objectUrl = new URL(url.trim());
                connection = (HttpURLConnection)objectUrl.openConnection();
                myClass.HttpRequest(connection, objectUrl);

            } catch (Exception e) {
                System.out.println("Invalid input");
            }
        }
    }
}
