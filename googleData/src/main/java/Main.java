import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        JTextArea jTextArea = new JTextArea();
        JFrame frame= new JFrame("test");

        frame.setLayout(new BorderLayout());
        frame.setSize(500,300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());

        JButton button1 = new JButton("C");
        JButton button2 = new JButton("R");
        JButton button3 = new JButton("U");
        JButton button4 = new JButton("D");

        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jTextArea.append("create\n");
                String jsonData = "{\"mode\": \"create\", \"key1\": \"김창조\",\"key2\": \"26\"}";
                sendRequest(jsonData);
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.append("read\n");
                String jsonData = "{\"mode\": \"read\"}";

                sendRequest(jsonData);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.append("update\n");

                String jsonData = "{\"mode\": \"update\", \"key1\": \"김변경\",\"key2\": \"24\"}";

                sendRequest(jsonData);
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jTextArea.append("delete\n");
                String jsonData = "{\"mode\": \"delete\"}";
                sendRequest(jsonData);
            }
        });

        jPanel.add(button1);
        jPanel.add(button2);
        jPanel.add(button3);
        jPanel.add(button4);

        frame.add(jPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(jTextArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void sendRequest(String jsonData) {
        try {
            URL url = new URL("https://script.google.com/macros/s/AKfycbz64sHFYTYboJafRoFRGQbRnRroPb86qySo5V4HLoKGm5n17Cfjk9C_SYlNXfy4VyjY/exec");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            StringBuilder response = new StringBuilder();

            try (InputStream is = (responseCode >= 200 && responseCode <=299)?
                    connection.getInputStream() : connection.getErrorStream();
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr)){

                  String line;
                  while ((line = br.readLine()) != null){
                      response.append(line);
                  }
            }
            String responseMessage = response.toString();
            System.out.println("Response Message: " + responseMessage);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
