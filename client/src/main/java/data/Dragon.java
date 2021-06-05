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

    public Dragon(String name,
                  long age,
                  DragonType type,
                  Color color,
                  DragonCharacter character,
                  DragonHead head,
                  Coordinates coordinates) {
        this.name = name;
        this.age = age;
        this.creationDate = LocalDate.now();
        this.color = color;
        this.type = type;
        this.character = character;
        this.head = head;
        this.coordinates = coordinates;
    }

    public Dragon(String name,
                  long age,
                  DragonType type,
                  Color color,
                  DragonCharacter character,
                  Coordinates coordinates) {
        this.name = name;
        this.age = age;
        this.creationDate = LocalDate.now();
        this.color = color;
        this.type = type;
        this.character = character;
        this.coordinates = coordinates;
    }

    public Dragon(Long id,
                  LocalDate creationDate,
                  String name,
                  long age,
                  DragonType type,
                  Color color,
                  DragonCharacter character,
                  DragonHead head,
                  Coordinates coordinates) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.age = age;
        this.creationDate = LocalDate.now();
        this.color = color;
        this.type = type;
        this.character = character;
        this.head = head;
        this.coordinates = coordinates;
    }

    public Dragon(Long id,
                  LocalDate creationDate,
                  String name,
                  long age,
                  DragonType type,
                  Color color,
                  DragonCharacter character,
                  Coordinates coordinates) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = name;
        this.age = age;
        this.creationDate = LocalDate.now();
        this.color = color;
        this.type = type;
        this.character = character;
        this.coordinates = coordinates;
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

    @Override
    public int compareTo(Dragon d) {
        return name.compareTo(d.getName());
    }

    @Override
    public String toString() {
        String S = "";
        S += "Dragon\n";
        S += "\tId: " + id + "\n";
        S += "\tДата создания: " + creationDate + "\n";
        S += "\tИмя: " + name + "\n";
        S += "\tВозраст: " + age + "\n";
        S += "\tТип: " + type + "\n";
        S += "\tЦвет: " + color + "\n";
        S += "\tХарактер: " + character + "\n";
        S += "\tКоординаты:\n";
        S += "\t\tx = " + coordinates.getX() + "\n";
        S += "\t\ty = " + coordinates.getY() + "\n";
        if (head != null) {
            S += "\tРазмер головы: " + head.getSize() + "\n";
            S += "\tКоличество глаз " + head.getEyesCount() + "\n";
        }

        return S;
    }

}
