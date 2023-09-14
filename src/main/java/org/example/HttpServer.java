package org.example;

import java.net.*;
import java.io.*;

public class HttpServer {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(-1);
        }
        Socket clientSocket = null;
        while (!serverSocket.isClosed()) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine = null;
            boolean firstLine = true;
            String uriString = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    firstLine = false;
                    uriString = inputLine.split(" ")[1];

                }
                if (!in.ready()) {
                    break;
                }
            }
            System.out.println("URI: " + uriString);
            String responseBody = "";

            if (uriString != null && uriString.equals("/")) {
                responseBody = "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "    <title>Form Example</title>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<h1>Calculadora</h1>\n" +
                        "<form action=\"/hello\">\n" +
                        "    <label for=\"valor1\">Valor 1:</label><br>\n" +
                        "    <input type=\"text\" id=\"valor1\" name=\"name\" value=\"1\"><br><br>\n" +
                        "    <label for=\"valor2\">Valor 2:</label><br>\n" +
                        "    <input type=\"text\" id=\"valor2\" name=\"name\" value=\"1\"><br><br>\n" +
                        "    <input type=\"button\" value=\"Submit\" onclick=\"result()\">\n" +
                        "</form>\n" +
                        "<div id=\"getrespmsg\"></div>\n" +
                        "\n" +
                        "<script>\n" +
                        "            function loadGetMsg() {\n" +
                        "                let nameVar = document.getElementById(\"name\").value;\n" +
                        "                const xhttp = new XMLHttpRequest();\n" +
                        "                xhttp.onload = function() {\n" +
                        "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                        "                    this.responseText;\n" +
                        "                }\n" +
                        "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n" +
                        "                xhttp.send();\n" +
                        "            }\n" +
                        "\n" +
                        "            function result(){\n" +
                        "                var x = Number(document.getElementById(\"valor1\").value);\n" +
                        "                var y = Number(document.getElementById(\"valor2\").value)\n" +
                        "                let resultado = x + y;\n" +
                        "                console.log(resultado);\n" +
                        "                document.getElementById(\"getrespmsg\").innerHTML =\n" +
                        "                    resultado;\n" +
                        "            }\n" +
                        "\n" +
                        "        </script>\n" +
                        "\n" +
                        "</body>\n" +
                        "</html>";
                outputLine = getLine(responseBody);
            }
            out.println(outputLine);
            out.close();
            in.close();
        }
        clientSocket.close();
        serverSocket.close();
    }

    public static String getIndexResponse() {
        String response = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Form Example</title>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Calculadora</h1>\n" +
                "<form action=\"/hello\">\n" +
                "    <label for=\"valor1\">Valor 1:</label><br>\n" +
                "    <input type=\"text\" id=\"valor1\" name=\"name\" value=\"1\"><br><br>\n" +
                "    <label for=\"valor2\">Valor 2:</label><br>\n" +
                "    <input type=\"text\" id=\"valor2\" name=\"name\" value=\"1\"><br><br>\n" +
                "    <input type=\"button\" value=\"Submit\" onclick=\"result()\">\n" +
                "</form>\n" +
                "<div id=\"getrespmsg\"></div>\n" +
                "\n" +
                "<script>\n" +
                "            function loadGetMsg() {\n" +
                "                let nameVar = document.getElementById(\"name\").value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "\n" +
                "            function result(){\n" +
                "                var x = Number(document.getElementById(\"valor1\").value);\n" +
                "                var y = Number(document.getElementById(\"valor2\").value)\n" +
                "                let resultado = x + y;\n" +
                "                console.log(resultado);\n" +
                "                document.getElementById(\"getrespmsg\").innerHTML =\n" +
                "                    resultado;\n" +
                "            }\n" +
                "\n" +
                "        </script>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        return response;
    }

    public static String getLine(String responseBody) {
        return "HTTP/1.1 200 OK \r\n"
                + "Content-Type: text/html \r\n"
                + "\r\n"
                + "\n"
                + responseBody;
    }
}