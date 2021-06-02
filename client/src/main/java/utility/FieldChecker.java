package utility;

import exceptions.WrongInputFormatException;

public interface FieldChecker<T> {
    T check(String str) throws WrongInputFormatException;
}
