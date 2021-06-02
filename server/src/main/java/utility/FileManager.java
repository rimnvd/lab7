package utility;

import data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Vector;

/**
 * This class is responsible for the operations with file.
 */
public class FileManager {
    private String name;
    private long age;
    private Color color;
    private DragonType type;
    private DragonCharacter character;
    private Integer x;
    private Integer y;
    private String call;
    private Integer size;
    private Double eyesCount;
    private Long id;
    private LocalDate creationDate;
    private final Vector<Dragon> vector = new Vector<>(0);
    private File file;
    private boolean checkHeadSize;
    private boolean checkEyesNumber;
    private Long maxId = null;
    private final Logger logger = LoggerFactory.getLogger(FileManager.class);

    public FileManager(String envVariable) {
        if (System.getenv().get(envVariable) != null) {
            this.file = new File(System.getenv(envVariable));
        } else {
            System.out.println("\u001B[31m" + "Environment variable was FILE not found. Create environment variable and try again");
            System.exit(0);
        }
    }

    /**
     * Reads the collection from the file.
     *
     * @return vector - the collection from the file
     */
    public Vector<Dragon> readCollection() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(file);
            Node root = document.getDocumentElement();
            NodeList dragons = root.getChildNodes();
            clearFields();
            for (int i = 0; i < dragons.getLength(); i++) {
                boolean checkFields = true;
                String FieldName;
                String FieldValue;
                String ClassName;
                if (dragons.item(i).getNodeType() != Node.TEXT_NODE && dragons.item(i).getChildNodes().getLength() > 1) {
                    call = dragons.item(i).getNodeName();
                    NodeList dragon = dragons.item(i).getChildNodes();
                    checkHeadSize = false;
                    checkEyesNumber = false;
                    for (int j = 0; j < dragon.getLength() && checkFields; j++) {
                        if (dragon.item(j).getNodeType() != Node.TEXT_NODE && dragon.item(j).getChildNodes().getLength() == 1) {
                            FieldName = dragon.item(j).getNodeName();
                            FieldValue = dragon.item(j).getChildNodes().item(0).getTextContent().replace("\n", "").trim();
                            checkFields = checkFields(FieldName, FieldValue, "a");
                        } else if (dragon.item(j).getNodeType() != Node.TEXT_NODE && dragon.item(j).getChildNodes().getLength() > 1) {
                            NodeList dragon1 = dragon.item(j).getChildNodes();
                            ClassName = dragon.item(j).getNodeName();
                            for (int k = 0; k < dragon1.getLength() && checkFields; k++) {
                                if (dragon1.item(k).getNodeType() != Node.TEXT_NODE && dragon1.item(k).getChildNodes().getLength() == 1) {
                                    FieldName = dragon1.item(k).getNodeName();
                                    FieldValue = dragon1.item(k).getChildNodes().item(0).getTextContent().replace("\n", "").trim();
                                    checkFields = checkFields(ClassName, FieldName, FieldValue);
                                }
                            }
                        }
                    }
                }
                if (dragons.item(i).getNodeType() != Node.TEXT_NODE) {
                    if (checkFields && elementCreation()) {
                        logger.info("The element number " + (i / 2 + 1) + " has been successfully added to the collection");
                    } else {
                        logger.info("The element number " + (i / 2 + 1) + " cannot be added to the collection");
                    }
                    clearFields();
                }
            }
        } catch (ParserConfigurationException e) {
            logger.warn("\u001B[31m" + "It is impossible to read from this file because of configuration error");
            System.exit(0);
        } catch (IOException ex) {
            if (!file.exists()) {
                logger.warn("\u001B[31m" + "File with the specified name was not found. Create a file and try again.");
                System.exit(0);

            } else if (file.exists() && !file.canRead()) {
                logger.warn("\u001B[31m" + "It is impossible to read data from this file.");
                System.exit(0);
            }
        } catch (SAXException ex) {
            logger.warn("\u001B[31m" + "It is impossible to read data from this file because it has incorrect structure");
            System.exit(0);
        }
        return vector;
    }

    /**
     * Clears the values of the characteristics of the dragon.
     */
    public void clearFields() {
        name = null;
        age = 0;
        color = null;
        type = null;
        character = null;
        x = null;
        y = null;
        call = null;
        size = null;
        eyesCount = null;
        id = null;
        creationDate = null;
    }

    /**
     * Checks if the String fieldName is one of the characteristics of the dragon or not and sets the value of this characteristic, using String fieldValue or tempFieldValue.
     *
     * @return true if String fieldName is one of the characteristics of the dragon, false otherwise
     */
    public boolean checkFields(String fieldName, String fieldValue, String tempFieldValue) {
        boolean checkField;
        if (fieldName.equalsIgnoreCase("name")) {
            if (name != null) checkField = false;
            else {
                name = fieldValue;
                checkField = true;
            }
        } else if (fieldName.equalsIgnoreCase("age")) {
            if (age != 0) checkField = false;
            else {
                age = checkAge(fieldValue);
                checkField = true;
            }
        } else if (fieldName.equalsIgnoreCase("color")) {
            if (color != null) checkField = false;
            else {
                color = checkColor(fieldValue.toUpperCase());
                checkField = true;
            }
        } else if (fieldName.equalsIgnoreCase("DragonType")) {
            if (type != null) checkField = false;
            else {
                type = checkType(fieldValue.toUpperCase());
                checkField = true;
            }
        } else if (fieldName.equalsIgnoreCase("DragonCharacter")) {
            if (character != null) checkField = false;
            else {
                character = checkCharacter(fieldValue.toUpperCase());
                checkField = true;
            }
        } else if (fieldName.equalsIgnoreCase("Coordinates") && fieldValue.equalsIgnoreCase("x")) {
            if (x != null) checkField = false;
            else {
                x = checkInt(tempFieldValue);
                checkField = true;
            }
        } else if (fieldName.equalsIgnoreCase("Coordinates") && fieldValue.equalsIgnoreCase("y")) {
            if (y != null) checkField = false;
            else {
                y = checkInt(tempFieldValue);
                checkField = true;
            }
        } else if (fieldName.equalsIgnoreCase("DragonHead") && fieldValue.equalsIgnoreCase("size")) {
            if (size != null) checkField = false;
            else {
                size = checkPositiveInt(tempFieldValue);
                checkField = true;
                checkHeadSize = true;
            }
        } else if (fieldName.equalsIgnoreCase("DragonHead") && fieldValue.equalsIgnoreCase("eyesCount")) {
            if (eyesCount != null) checkField = false;
            else {
                eyesCount = checkEyesCount(tempFieldValue);
                checkField = true;
                checkEyesNumber = true;
            }
        } else if (fieldName.equalsIgnoreCase("id")) {
            if (id != null) checkField = false;
            else {
                id = checkId(fieldValue);
                checkField = true;
            }
        } else if (fieldName.equalsIgnoreCase("CreationDate")) {
            if (creationDate != null) checkField = false;
            else {
                creationDate = checkData(fieldValue);
                checkField = true;
            }
        } else checkField = false;
        return checkField;
    }

    /**
     * Sets the value of id of the dragon.
     *
     * @param checkedValue set value
     * @return the set value of id or null if it was not set
     */
    public Long checkId(String checkedValue) {
        try {
            id = Long.parseLong(checkedValue);
            if (id <= 0) {
                id = null;
            } else if (maxId != null) {
                if (id > maxId) {
                    maxId = id;
                } else id = null;
            } else maxId = id;
        } catch (NumberFormatException ex) {
            id = null;
        }
        return id;
    }

    /**
     * Sets the value of the age of the dragon.
     *
     * @param checkedValue set value
     * @return the set value of the age or 0 if it was not set
     * @throws NumberFormatException thrown to indicate that the application has attempted to convert a string to one of the numeric types,
     *                               but that the string does not have the appropriate format
     */
    public long checkAge(String checkedValue) {
        try {
            age = Long.parseLong(checkedValue);
            if (age < 0) {
                age = 0;
            }
        } catch (NumberFormatException ex) {
            age = 0;
        }
        return age;
    }

    /**
     * Sets the value of the color of the dragon
     *
     * @param checkedValue set value
     * @return the set value of the color or null if it was not set
     */
    public Color checkColor(String checkedValue) {
        if (checkedValue == null) color = null;
        else if (checkedValue.toUpperCase().matches("RED|YELLOW|ORANGE|BROWN|WHITE")) {
            color = Color.valueOf(checkedValue.toUpperCase());
        } else color = null;
        return color;
    }

    /**
     * Sets the value of the type of the dragon.
     *
     * @param checkedValue set value
     * @return the set value of the type or null if it was not set
     */
    public DragonType checkType(String checkedValue) {
        if (checkedValue == null) type = null;
        else if (checkedValue.toUpperCase().matches("WATER|AIR|FIRE|UNDERGROUND")) {
            type = DragonType.valueOf(checkedValue.toUpperCase());
        } else type = null;
        return type;
    }

    /**
     * Sets the value of the character of the dragon.
     *
     * @param checkedValue set value.
     * @return the set value of the character or null if it was not set
     */
    public DragonCharacter checkCharacter(String checkedValue) {
        if (checkedValue == null) character = null;
        else if (checkedValue.toUpperCase().matches("GOOD|CUNNING|CHAOTIC_EVIL")) {
            character = DragonCharacter.valueOf(checkedValue.toUpperCase());
        } else character = null;
        return character;
    }

    /**
     * Checks whether String S can be parsed to Integer or not
     *
     * @return the Integer value if it is possible or null in the opposite situation
     * @throws NumberFormatException thrown to indicate that the application has attempted to convert a string to one of the numeric types,
     *                               but that the string does not have the appropriate format
     */
    public Integer checkInt(String checkedValue) {
        Integer value;
        try {
            value = Integer.parseInt(checkedValue);
        } catch (NumberFormatException ex) {
            value = null;
        }
        return value;
    }

    /**
     * Checks whether String S can be parsed to Integer or not and checks whether the value is positive.
     *
     * @return the positive Integer value if it possible or null in the opposite situation
     * @throws NumberFormatException Thrown to indicate that the application has attempted to convert a string to one of the numeric types,
     *                               but that the string does not have the appropriate format
     */
    public Integer checkPositiveInt(String checkedValue) {
        Integer value;
        try {
            value = Integer.parseInt(checkedValue);
            if (value <= 0) {
                value = null;
            }
        } catch (NumberFormatException ex) {
            value = null;
        }
        return value;
    }

    /**
     * Checks whether String S can be parsed to Double or not and checks whether the value is positive.
     *
     * @return the positive Double value if it possible or null in the opposite situation
     * @throws NumberFormatException Thrown to indicate that the application has attempted to convert a string to one of the numeric types,
     *                               but that the string does not have the appropriate format
     */
    public Double checkEyesCount(String checkedValue) {
        try {
            eyesCount = Double.parseDouble(checkedValue);
            long value = Math.round(eyesCount);
            if (eyesCount <= 0 || value != eyesCount) {
                eyesCount = null;
            }
        } catch (NumberFormatException ex) {
            eyesCount = null;
        }
        return eyesCount;
    }

    /**
     * Checks whether at least one of the dragon characteristics is null(= 0 for age) or not.
     *
     * @return true if none of the characteristics is null
     */
    public boolean checkNull() {
        return id != null && creationDate != null && name != null && age != 0 && color != null && type != null && character != null && x != null && y != null;
    }

    /**
     * Creates new object Dragon and adds it to collection, if it is possible, or indicates that it is impossible.
     */
    public boolean elementCreation() {
        boolean checkElementCreation = true;
        if (checkNull() && checkNullHead() && checkCall()) {
            vector.add(new Dragon(id, creationDate, name, age, type, color, character, new DragonHead(size, eyesCount), new Coordinates(x, y)));
        } else if (checkNull() && checkCall() && !checkHeadSize && !checkEyesNumber) {
            vector.add(new Dragon(id, creationDate, name, age, type, color, character, new Coordinates(x, y)));
        } else checkElementCreation = false;
        return checkElementCreation;
    }

    /**
     * Checks whether at least one of the characteristics of the head of the dragon is null.
     *
     * @return true if none of the characteristics is null
     */
    public boolean checkNullHead() {
        return size != null && eyesCount != null;
    }

    public LocalDate checkData(String checkedValue) {
        try {
            creationDate = LocalDate.parse(checkedValue);
        } catch (DateTimeParseException ex) {
            creationDate = null;
        }
        return creationDate;
    }

    /**
     * Checks if String call is equal to "Dragon" or not.
     *
     * @return true if String call is equal to "Dragon"; false otherwise
     */
    public boolean checkCall() {
        return call.equalsIgnoreCase("Dragon");
    }

    public Long getMaxId() {
        return maxId;
    }

    /**
     * Transforms the collection to the xml-format and writes it to xml-file.
     *
     * @param vector - the collection
     */

    public void writeCollection(Vector<Dragon> vector) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();
            if (!vector.isEmpty()) {
                Element root = document.createElement("Dragons");
                document.appendChild(root);
                for (Dragon dragon : vector) {
                    Element call = document.createElement("Dragon");
                    root.appendChild(call);
                    Element id = document.createElement("id");
                    id.appendChild(document.createTextNode(String.valueOf(dragon.getId())));
                    call.appendChild(id);
                    Element creationDate = document.createElement("CreationDate");
                    creationDate.appendChild(document.createTextNode(String.valueOf(dragon.getCreationDate())));
                    call.appendChild(creationDate);
                    Element name = document.createElement("name");
                    name.appendChild(document.createTextNode(dragon.getName()));
                    call.appendChild(name);
                    Element age = document.createElement("age");
                    age.appendChild(document.createTextNode(String.valueOf(dragon.getAge())));
                    call.appendChild(age);
                    Element type = document.createElement("DragonType");
                    type.appendChild(document.createTextNode(String.valueOf(dragon.getType())));
                    call.appendChild(type);
                    Element color = document.createElement("Color");
                    color.appendChild(document.createTextNode(String.valueOf(dragon.getColor())));
                    call.appendChild(color);
                    Element character = document.createElement("DragonCharacter");
                    character.appendChild(document.createTextNode(String.valueOf(dragon.getCharacter())));
                    call.appendChild(character);
                    if (dragon.getHead() != null) {
                        Element head = document.createElement("DragonHead");
                        call.appendChild(head);
                        Element size = document.createElement("size");
                        size.appendChild(document.createTextNode(String.valueOf(dragon.getHead().getSize())));
                        head.appendChild(size);
                        Element eyesCount = document.createElement("eyesCount");
                        eyesCount.appendChild(document.createTextNode(String.valueOf(dragon.getHead().getEyesCount())));
                        head.appendChild(eyesCount);
                    }
                    Element coordinates = document.createElement("Coordinates");
                    call.appendChild(coordinates);
                    Element x = document.createElement("x");
                    x.appendChild(document.createTextNode(String.valueOf(dragon.getCoordinates().getX())));
                    coordinates.appendChild(x);
                    Element y = document.createElement("y");
                    y.appendChild(document.createTextNode(String.valueOf(dragon.getCoordinates().getY())));
                    coordinates.appendChild(y);
                }
            }
            writeDocument(document);
        } catch (ParserConfigurationException e) {
            logger.warn("\u001B[31m" + "It is impossible to write the collection because of configuration error" + "\u001B[0m");
        }
    }

    /**
     * Writes document to the xml-file.
     *
     * @param document - written document
     */
    public void writeDocument(Document document) {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource dom = new DOMSource(document);
            if (!file.canWrite() && file.exists()) {
                logger.warn("\u001B[31m" + "It is impossible to write to this file" + "\u001B[0m");
            } else if (!file.exists()) {
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream("lab6reserve.xml"));
                StreamResult result = new StreamResult(outputStream);
                tr.transform(dom, result);
            } else {
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                StreamResult result = new StreamResult(outputStream);
                tr.transform(dom, result);
            }
        } catch (TransformerException ex) {
            logger.warn("\u001B[31m" + "It is impossible to write the collection to the file" + "\u001B[0m");
        } catch (FileNotFoundException ex) {
            if (!file.exists()) {
                logger.warn("\u001B[31m" + "It is impossible to write collection to the file because file with the specified name does not exist" + "\u001B[0m");
            }
        }
    }
}
