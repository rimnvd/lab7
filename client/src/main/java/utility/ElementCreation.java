package utility;

import data.*;
import exceptions.WrongInputFormatException;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class is responsible for the creation of the new Dragon object.
 */
public class ElementCreation {
    private final Scanner scanner;

    public ElementCreation(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Creates new dragon.
     *
     * @return new dragon
     */
    public Dragon createElement(String username) {
        String name = null;
        long age = 0;
        DragonType type = null;
        Color color = null;
        DragonCharacter character = null;
        Integer x;
        Integer y;
        Integer size;
        Double eyesCount;
        Dragon dragon;
        try {
            name = checkName();
            age = checkAge();
            type = checkType();
            color = checkColor();
            character = checkCharacter();
        } catch (WrongInputFormatException e) {
            System.out.println(ConsoleColor.ANSI_RED.getColor() + "\nНеверный формат данных. Повторите ввод" + ConsoleColor.ANSI_RESET.getColor());
        }
        x = checkX();
        y = checkY();
        if (checkDragonHead()) {
            size = checkSize();
            eyesCount = checkEyesCount();
            dragon = new Dragon(name,
                    age,
                    type,
                    color,
                    character,
                    new data.DragonHead(size, eyesCount),
                    new data.Coordinates(x, y), username);
        } else {
            dragon = new Dragon(name,
                    age,
                    type,
                    color,
                    character,
                    new data.Coordinates(x, y), username);
        }
        return dragon;
    }

    /**
     * Reads the line from the console.
     *
     * @return line from the console
     */
    public String read() {
        String line;
        try {
            line = scanner.nextLine().trim();
        } catch (NoSuchElementException ex) {
            System.out.println("Завершение работы клиентского приложения");
            System.exit(0);
            line = null;
        }
        if (line.equals("")) {
            line = null;
        }
        return line;
    }


    public <T> T readAndCheckField(String FieldName, FieldChecker<T> checker) {
        T temp;
        while (true) {
            System.out.println("Введите " + FieldName);
            try {
                temp = checker.check(read());
                System.out.println();
            } catch (NumberFormatException | WrongInputFormatException ex) {
                System.out.println(ConsoleColor.ANSI_RED.getColor() + "\nНеверный формат данных. Повторите ввод" + ConsoleColor.ANSI_RESET.getColor());
                System.out.println();
                continue;
            }
            return temp;
        }
    }

    public long checkAge() throws NumberFormatException {
        FieldChecker<Long> fieldChecker = str -> {
            Long result = Long.parseLong(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("возраст", fieldChecker);
    }

    public Integer checkX() throws NumberFormatException {
        FieldChecker<Integer> fieldChecker = Integer::parseInt;
        return readAndCheckField("координату x", fieldChecker);
    }

    public Integer checkY() throws NumberFormatException {
        FieldChecker<Integer> fieldChecker = Integer::parseInt;
        return readAndCheckField("координату y", fieldChecker);
    }

    public Integer checkSize() throws NumberFormatException {
        FieldChecker<Integer> fieldChecker = str -> {
            if (str == null) return null;
            Integer result = Integer.parseInt(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("размер головы", fieldChecker);
    }

    public Double checkEyesCount() throws NumberFormatException {
        FieldChecker<Double> fieldChecker = str -> {
            if (str == null) throw new WrongInputFormatException();
            Double result = Double.parseDouble(str);
            if (result <= 0) {
                throw new NumberFormatException();
            }
            long intResult = Math.round(result);
            if (intResult != result) {
                throw new NumberFormatException();
            }
            return result;
        };
        return readAndCheckField("количество глаз", fieldChecker);
    }

    public Color checkColor() throws WrongInputFormatException {
        FieldChecker<Color> fieldChecker = str -> {
            if (str == null) throw new WrongInputFormatException();
            if (str.toUpperCase().matches("RED|YELLOW|ORANGE|BROWN|WHITE")) {
                return Color.valueOf(str.toUpperCase());
            }
            throw new WrongInputFormatException();
        };
        StringBuilder ColorConstants = new StringBuilder();
        for (Color color : Color.values()) {
            ColorConstants.append(color.toString()).append("\n");
        }
        ColorConstants.deleteCharAt(ColorConstants.length() - 1);
        return readAndCheckField("цвет\n" + ColorConstants, fieldChecker);
    }

    public DragonType checkType() throws WrongInputFormatException {
        FieldChecker<DragonType> fieldChecker = str -> {
            if (str == null) throw new WrongInputFormatException();
            if (str.toUpperCase().matches("WATER|AIR|FIRE|UNDERGROUND")) {
                return DragonType.valueOf(str.toUpperCase());
            }
            throw new WrongInputFormatException();
        };
        StringBuilder TypeConstants = new StringBuilder();
        for (DragonType type : DragonType.values()) {
            TypeConstants.append(type.toString()).append("\n");
        }
        TypeConstants.deleteCharAt(TypeConstants.length() - 1);
        return readAndCheckField("тип\n" + TypeConstants, fieldChecker);
    }

    public DragonCharacter checkCharacter() throws WrongInputFormatException {
        FieldChecker<DragonCharacter> fieldChecker = str -> {
            if (str == null) throw new WrongInputFormatException();
            if (str.toUpperCase().matches("GOOD|CUNNING|CHAOTIC_EVIL")) {
                return DragonCharacter.valueOf(str.toUpperCase());
            }
            throw new WrongInputFormatException();
        };
        StringBuilder CharacterConstants = new StringBuilder();
        for (DragonCharacter character : DragonCharacter.values()) {
            CharacterConstants.append(character.toString()).append("\n");
        }
        CharacterConstants.deleteCharAt(CharacterConstants.length() - 1);
        return readAndCheckField("характер\n" + CharacterConstants, fieldChecker);
    }

    public String checkName() throws WrongInputFormatException {
        FieldChecker<String> fieldChecker = str -> {
            if (str == null) {
                throw new WrongInputFormatException();
            }
            return str;
        };
        return readAndCheckField("имя", fieldChecker);
    }

    public boolean checkDragonHead() {
        boolean checkCorrectAnswer = true;
        boolean ans = true;
        while (checkCorrectAnswer) {
            System.out.println("Вы хотите ввести размер головы и количество глаз? (YES/NO)");
            String answer = scanner.nextLine().trim();
            System.out.println();
            if (answer.equalsIgnoreCase("YES")) {
                checkCorrectAnswer = false;
                ans = true;
            } else if (answer.equalsIgnoreCase("NO")) {
                checkCorrectAnswer = false;
                ans = false;
            }
        }
        return ans;
    }

    public Dragon createFromScript(String[] fields, String username) {
        try {
            DragonType type = null;
            Color color = null;
            DragonCharacter character = null;
            String name = fields[0];
            long age = Long.parseLong(fields[1]);
            if (fields[2] != null) {
                if (fields[2].toUpperCase().matches("WATER|AIR|FIRE|UNDERGROUND")) {
                    type = DragonType.valueOf(fields[2].toUpperCase());
                }
            }
            if (fields[3] != null) {
                if (fields[3].toUpperCase().matches("RED|YELLOW|ORANGE|BROWN|WHITE")) {
                    color = Color.valueOf(fields[3].toUpperCase());
                }
            }
            if (fields[4] != null) {
                if (fields[4].toUpperCase().matches("GOOD|CUNNING|CHAOTIC_EVIL")) {
                    character = DragonCharacter.valueOf(fields[4].toUpperCase());
                }
            }
            Integer x = Integer.parseInt(fields[5]);
            Integer y = Integer.parseInt(fields[6]);
            if (fields[7].equals("") && fields[8].equals("")) {
                if (age > 0 && name != null && color != null && type != null && character != null) {
                    return new Dragon(name, age, type, color, character, new Coordinates(x, y), username);
                }
            } else {
                Integer size = Integer.parseInt(fields[7]);
                Double eyesCount = Double.parseDouble(fields[8]);
                long eyesCountRound = Math.round(eyesCount);
                if (eyesCount == eyesCountRound && age > 0 && size > 0 && eyesCount > 0 && name != null && type != null && character != null) {
                    return new Dragon(name, age, type, color, character, new DragonHead(size, eyesCount), new Coordinates(x, y), username);
                }
            }
        } catch (NumberFormatException ex) {
            return null;
        }
        return null;
    }


}
