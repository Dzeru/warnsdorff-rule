package com.dzeru.warnsdorffrule;

import java.awt.*;

public class Constants {
    public static final Color VISITED_CELL_COLOR = new Color(193, 211, 127);
    public static final Color INDEX_CELL_COLOR = new Color(240, 226, 163);
    public static final Color HORSE_CELL_COLOR = new Color(102, 78, 76);
    public static final Color NEXT_CELL_COLOR = new Color(249, 212, 187);
    public static final Color MIN_NEXT_CELL_COLOR = new Color(204, 255, 255);

    public static final int CELL_WIDTH = 100;
    public static final int CELL_HEIGHT = 100;

    public static final int WINDOW_WIDTH = 1700;
    public static final int WINDOW_HEIGHT = 1000;

    public static final Font FONT = new Font("Verdana", Font.PLAIN, 24);

    public static int CELL_COUNT = 9;
    public static String[] LETTERS = {" ", "A", "B", "C", "D", "E", "F", "G", "H"};

    public static final String DESCRIPTION = "Правило Варнсдорфа, являющееся разновидностью \nжадного алгоритма для отыскания маршрута коня, \nформулируется так:\n" +
            "\n" +
            "При обходе доски конь следует на то поле, \nс которого можно пойти \nна минимальное число ещё не пройденных полей. \nЕсли таких полей несколько, \nто можно пойти на любое из них.\n" +
            "Долгое время считалось, \nчто правило Варнсдорфа работает безукоризненно. \nПозднее с помощью компьютеров была установлена \nнеточность во второй его части: \n" +
            "если существует несколько подходящих полей, \nто не все они равноценны, \nи произвольный выбор поля может завести коня в тупик. \n" +
            "Однако на практике вероятность \nпопадания в тупик невелика даже \nпри вольном пользовании \nвторой частью правила Варнсдорфа." +
            "\n\n\n" +
            "Коричневый - конь.\n" +
            "Зеленый - посещеные клетки.\n" +
            "Голубой - следующая клетка для хода.\n" +
            "Розовый - другие возможные клетки для хода.\n";
}
