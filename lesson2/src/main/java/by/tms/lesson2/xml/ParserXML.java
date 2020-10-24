package by.tms.lesson2.xml;

import by.tms.lesson2.classes.User;
import by.tms.lesson2.classes.UserBase;
import by.tms.lesson2.exceptions.UserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParserXML {

    private static final String xmlFile = "/Users/trifonovanv/Desktop/TeachMeSkills/TMS_Homework_part2/lesson2/src/main/resources/users.xml";
    private static final String xsdFile = "/Users/trifonovanv/Desktop/TeachMeSkills/TMS_Homework_part2/lesson2/src/main/resources/users.xsd";

    public static void start() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            if (validateXMLSchema(xsdFile, xmlFile)) {

                Node root = document.getDocumentElement();

                NodeList users = root.getChildNodes();
                for (int i = 0; i < users.getLength(); i++) {
                    Node user = users.item(i);

                    if (user.getNodeType() != Node.TEXT_NODE) {
                        User newUser = new User();
                        String username = user.getAttributes().getNamedItem("Username").getNodeValue();
                        newUser.setUsername(username);
                        NodeList bookProps = user.getChildNodes();

                        for (int j = 0; j < bookProps.getLength(); j++) {
                            Node bookProp = bookProps.item(j);

                            if (bookProp.getNodeType() != Node.TEXT_NODE) {
                                switch (bookProp.getNodeName()) {
                                    case "Name":
                                        newUser.setName(bookProp.getFirstChild().getTextContent());
                                        break;
                                    case "Password":
                                        newUser.setPassword(bookProp.getFirstChild().getTextContent());
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                        try {
                            UserBase.addUser(newUser);
                        } catch (UserException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace(System.out);
        }

    }

    private static boolean validateXMLSchema(String xsdPath, String xmlPath) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static void addNewUser(User newUser) throws UserException {
        try {
            try {
                UserBase.addUser(newUser);
            } catch (UserException e){
                throw new UserException("This user already exists.");
            }

            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element user = document.createElement("user");
            user.setAttribute("Username", newUser.getUsername());

            Element name = document.createElement("Name");
            name.setTextContent(newUser.getName());

            Element password = document.createElement("Password");
            password.setTextContent(newUser.getPassword());

            user.appendChild(name);
            user.appendChild(password);

            Node root = document.getDocumentElement();
            root.appendChild(user);

            writeDocument(document);
        } catch (ParserConfigurationException | SAXException | IOException ex){
            ex.printStackTrace(System.out);
        }
    }

    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer tr = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(xmlFile);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}