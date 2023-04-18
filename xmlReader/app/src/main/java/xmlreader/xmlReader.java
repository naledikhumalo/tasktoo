package xmlreader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import java.io.File;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class XMLReader {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("data.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            Scanner scanner = new Scanner(System.in);
            String input = "";
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Enter the field names to output as JSON (comma-separated): ");
                input = scanner.nextLine();
                validInput = validateInput(input, doc);
            }

            String[] fieldNames = input.split(",");
            JsonObject json = new JsonObject();
            for (String fieldName : fieldNames) {
                Node fieldNode = doc.getElementsByTagName(fieldName.trim()).item(0);
                if (fieldNode != null) {
                    json.addProperty(fieldName.trim(), fieldNode.getTextContent());
                }
            }

            Gson gson = new Gson();
            String jsonString = gson.toJson(json);
            System.out.println(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean validateInput(String input, Document doc) {
        if (input.isEmpty()) {
            System.out.println("Error: input cannot be empty");
            return false;
        }

        String[] fieldNames = input.split(",");
        for (String fieldName : fieldNames) {
            if (doc.getElementsByTagName(fieldName.trim()).getLength() == 0) {
                System.out.println("Error: " + fieldName.trim() + " is not a valid field name");
                return false;
            }
        }

        return true;
    }
}