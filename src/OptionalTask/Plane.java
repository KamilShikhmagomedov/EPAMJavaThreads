package OptionalTask;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Plane extends Thread {
    Semaphore airport;

    @Override
    public void run() {
        try {
            airport.acquire();
            System.out.println("Самолет - " + this.getName() + " начал выход на полосу.");
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
            System.out.println("Самолет - " + this.getName() + " взлетел.");
            airport.release();
            System.out.println("Полоса освободилась");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
