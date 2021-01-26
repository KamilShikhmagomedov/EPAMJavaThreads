package MainTask;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Начало работы порта");
        final int SHIPS_QUANTITY = 120;
        Random random = new Random();
        for (int i = 1; i < SHIPS_QUANTITY; i++) {
            Ship ship = new Ship(random.nextInt(Ship.CONTAINERS_CAPACITY), "Ship - " + i);
            ship.start();
        }
    }
}
