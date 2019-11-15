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

    public static final int WINDOW_WIDTH = 1800;
    public static final int WINDOW_HEIGHT = 1000;

    public static final Font FONT = new Font("Verdana", Font.PLAIN, 24);
    public static final Font SMALL_FONT = new Font("Verdana", Font.PLAIN, 20);

    public static final int CELL_COUNT = 9;
    public static final String[] LETTERS = {" ", "A", "B", "C", "D", "E", "F", "G", "H"};

    public static final int INSETS_SIZE = 20;

    public static final String DESCRIPTION = "Правило Варнсдорфа (1823 год), являющееся разновидностью жадного алгоритма для отыскания маршрута коня, формулируется так:" +
            "\n" +
            "При обходе доски конь следует на то поле, с которого можно пойти на минимальное число ещё не пройденных полей. Если таких полей несколько, то можно пойти на любое из них." +
            "Долгое время считалось, что правило Варнсдорфа работает безукоризненно. Позднее с помощью компьютеров была установлена неточность во второй его части: " +
            "если существует несколько подходящих полей, то не все они равноценны, и произвольный выбор поля может завести коня в тупик. " +
            "Однако на практике вероятность попадания в тупик невелика даже при вольном пользовании второй частью правила Варнсдорфа." +
            "\n" +
            "Машинный эксперимент, проведенный в Тульском пединституте под руководством А. Есаяна, " +
            "показал, что с какого бы поля доски конь ни начал свой маршрут, можно так пользоваться второй частью правила Варнсдорфа, " +
            "что он попадет в тупик раньше, чем посетит все поля доски." +
            "\n\n" +
            "Подробнее:\n" +
            "https://forany.xyz/a-16?pg=8\n" +
            "http://study.sfu-kras.ru/DATA/docs/ProgramTheory/recurs/varnsdrf.htm" +
            "\n\n\n" +
            "Коричневый - конь.\n" +
            "Зеленый - посещеные клетки.\n" +
            "Голубой - следующая клетка для хода.\n" +
            "Оранжевый - другие возможные клетки для хода.\n" +
            "Число в клетке - количество клеток,\nна которые можно попасть из нее." +
            "\n\n\n" +
            "Автор: Машкина Диана, 351 группа, КНиИТ, СГУ. 2019 год.";
}
