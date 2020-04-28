package com.company;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class HttpClient {
    private static final int port = 80;
    public void HttpRequest(URLConnection connection, URL url) throws IOException {

        Socket socket = new Socket(url.getHost(), port);
        //Get input and output streams for the socket
        OutputStream out = socket.getOutputStream();
        // Constructe a HTTP GET request
        // The end of HTTP GET request should be \r\n\r\n
        String request = "GET " + url.getPath() + "?" + " HTTP/1.1\r\n"
                + "Accept: */*\r\n" + "Host: " + url.getHost()+ "\r\n"
                + "Connection: Close\r\n\r\n";

        // Sends off HTTP GET request
        out.write(request.getBytes());
        out.flush();
        InputStream in = socket.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        in.transferTo(baos);
        InputStream input = new ByteArrayInputStream(baos.toByteArray());
        BufferedReader bufRead = new BufferedReader(new InputStreamReader(input));
        String outStr,response="";
        //Prints each line of the response
        while((outStr = bufRead.readLine()) != null){
            response+= outStr;
            response+="\r\n";
            if(outStr.equals(""))
                break;
        }

        System.out.println(response);
        final String status = response.substring(response.indexOf(" ") + 1, response.indexOf(" ") + 4);
        if (status.equals("200")||status.equals("301")||status.equals("302")) {
            print(connection,url);
        } else {
            System.out.println("Something went wrong: " + status);
        }
        // Closes socket
        socket.close();
    }

    public void print(URLConnection connection,URL url) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Darbas\\IdeaProjects\\MainHttpClient\\header." + format(connection)));
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            byte[] bytes = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(bytes)) > 0) {
                byteStream.write(bytes, 0, bytesRead);
            }
            byteStream.writeTo(fos);

            System.out.println("Bytes have been written to file");
        } catch (IOException e) {
        System.err.printf("Could not read bytes");
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public String  format(URLConnection connection){
        String contentType = connection.getContentType();
       // System.out.println(contentType);
        if (contentType.indexOf(';') > 0) {
            return contentType.substring(contentType.indexOf('/') + 1, contentType.indexOf(';'));
        }
        return contentType.substring(contentType.indexOf('/') + 1);
    }
}
