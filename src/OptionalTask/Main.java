package OptionalTask;

import java.util.concurrent.Semaphore;

// В аэропорту есть 5 взлетно-посадочных полос. Самолету требуется 3 минуты чтобы выйти на полосу,
// набрать скорость и взлететь. После этого полоса свободна для вылета следующего самолета.
// Реализовать симуляцию вылета 10 самолетов используя все доступные полосы.
// 1 минуту реально времени заменить на 1 секунду в симуляции. Вывести в консоль информацию о следующих событиях:
// - Самолет начал выход на полосу
// - Самолет взлетел
// - Полоса "приняла" самолет
// - Полоса освободилась

public class Main {
    public static void main(String[] args) {
        Semaphore airport = new Semaphore(5);
        Plane [] array = new Plane[10];
        for (int i = 0; i < 10; i++) {
            array[i] = new Plane();
        }
        for (int i = 0; i < 10; i++) {
            array[i].airport = airport;
        }
        for (int i = 0; i < 10; i++) {
            array[i].start();
        }
    }
}