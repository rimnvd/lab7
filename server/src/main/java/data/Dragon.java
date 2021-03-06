package data;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * This class contains the description of the elements stored in the collection.
 */
public class Dragon implements Comparable<Dragon>, Serializable {
    private static final long serialVersionUID = 20L;
    private final String name;
    private final Coordinates coordinates;
    private final long age;
    private final Color color;
    private final DragonType type;
    private final DragonCharacter character;
    private Long id;
    private LocalDate creationDate;
    private DragonHead head;
    private final String owner;

    public Dragon(String name,
                  long age,
                  DragonType type,
                  Color color,
                  DragonCharacter character,
                  DragonHead head,
                  Coordinates coordinates, String owner) {
        this.name = name;
        this.age = age;
        this.creationDate = LocalDate.now();
        this.color = color;
        this.type = type;
        this.character = character;
        this.head = head;
        this.coordinates = coordinates;
        this.owner = owner;

    }

    public Dragon(String name,
                  long age,
                  DragonType type,
                  Color color,
                  DragonCharacter character,
                  Coordinates coordinates, String owner) {
        this.name = name;
        this.age = age;
        this.creationDate = LocalDate.now();
        this.color = color;
        this.type = type;
        this.character = character;
        this.coordinates = coordinates;
        this.owner = owner;
        this.head = null;
    }

    public Dragon(Long id,
                  LocalDate creationDate,
                  String name,
                  long age,
                  DragonType type,
                  Color color,
                  DragonCharacter character,
                  DragonHead head,
                  Coordinates coordinates, String owner) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.head = head;
        this.coordinates = coordinates;
        this.owner = owner;
    }

    public Dragon(Long id,
                  LocalDate creationDate,
                  String name,
                  long age,
                  DragonType type,
                  Color color,
                  DragonCharacter character,
                  Coordinates coordinates, String owner) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.coordinates = coordinates;
        this.owner = owner;
        this.head = null;
    }

    /**
     * @return Id of the dragon.
     */
    public Long getId() {
        return id;
    }

    /**
     * set the value of the field "id".
     *
     * @param setValue - the setting value.
     */
    public void setId(Long setValue) {
        id = setValue;
    }

    /**
     * @return Name of the dragon.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Coordinates of the dragon.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @return Character of the dragon.
     */
    public DragonCharacter getCharacter() {
        return character;
    }

    /**
     * @return Color of the dragon.
     */
    public Color getColor() {
        return color;
    }


    /**
     * @return Creation date of the dragon.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return Age of the dragon.
     */
    public long getAge() {
        return age;
    }

    /**
     * @return type of the dragon
     */
    public DragonType getType() {
        return type;
    }

    /**
     * @return head of the dragon
     */
    public DragonHead getHead() {
        return head;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public int compareTo(Dragon d) {
        return name.compareTo(d.getName());
    }

    @Override
    public String toString() {
        String S = "";
        S += "Dragon\n";
        S += "\tId: " + id + "\n";
        S += "\t???????? ????????????????: " + creationDate + "\n";
        S += "\t??????: " + name + "\n";
        S += "\t??????????????: " + age + "\n";
        S += "\t??????: " + type + "\n";
        S += "\t????????: " + color + "\n";
        S += "\t????????????????: " + character + "\n";
        S += "\t????????????????????:\n";
        S += "\t\tx = " + coordinates.getX() + "\n";
        S += "\t\ty = " + coordinates.getY() + "\n";
        if (head != null) {
            if (head.getSize() != null) {
                S += "\t???????????? ????????????: " + head.getSize() + "\n";
            }
            S += "\t???????????????????? ???????? " + head.getEyesCount() + "\n";
        }
        return S;
    }

}
