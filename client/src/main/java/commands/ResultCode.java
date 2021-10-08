package commands;

import java.io.Serializable;

public enum ResultCode implements Serializable {
    DBERROR,
    CHANGE,             //команда выполнилась, коллекция изменилась (удаление/добавление элемента, очищение коллекции)
    ERROR,              //невозможно выполнить команду
    DEFAULT,            //команда выполнилась, коллекция не изменилась
    EXIST,              //пользователь с таким имненм уже существует (при регистарции)
    NOTFOUND,
    WRONG,              //неверное имя пользователя или пароль
    OK;                 //пользователь успешно авторизован или зарегистрирован



    private static final long serialVersionUID = 14L;

}
