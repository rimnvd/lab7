package utility;

import utility.exception.WrongInputFormatException;

public interface FieldChecker<T> {
    T check(String str) throws WrongInputFormatException;
}
