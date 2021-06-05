package commands;

import java.io.Serializable;

public enum CommandCode implements Serializable {
    CHANGE,                 //команда выполнилась, коллекция изменилась (удаление/добавление элемента, очищение коллекции)
    ERROR,              //невозможно выполнить команду
    DEFAULT;            //команда выполнилась, коллекция не изменилась

    private static final long serialVersionUID = 14L;

}
