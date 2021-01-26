package MainTask;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Ship extends Thread {
    private final AtomicInteger containersQuantity = new AtomicInteger();
    private final Lock lock = new ReentrantLock();
    public static final int CONTAINERS_CAPACITY = 30;
    public static final int RANDOM_RANGE = 100;

    public Ship(int containersNumber, String name) {
        super(name);
        this.containersQuantity.set(containersNumber);
    }

    @Override
    public void run() {
        Port.getInstance().serveShip(this);
        Random random = new Random();
        System.out.println("Число контейнеров на корабле = " + containersQuantity.get());
        try {
            if (containersQuantity.get() == 0 || random.nextInt(RANDOM_RANGE) > random.nextInt(RANDOM_RANGE)) {

                int containersResidues = loadContainers(random.nextInt(CONTAINERS_CAPACITY));
                if (containersResidues > 0) {
                    System.out.println("Значение было превышено, остатки разгружают на складе.");
                    unloadContainersIntoWarehouse(containersResidues);                }
            } else if (random.nextInt(RANDOM_RANGE) < random.nextInt(RANDOM_RANGE)) {
                unloadContainersIntoWarehouse(this.containersQuantity.get());
                containersQuantity.set(0);
            }
            containersQuantity.set(0);
            Thread.sleep(1000);
            Port.getInstance().sendShip(this);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }


    public void unloadContainersIntoWarehouse(int number) {
        System.out.println("Выгрузка контейнеров на склад");
        while (!Port.getInstance().loadContainers(number)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }


    public int loadContainers(int containersNumber) {
        System.out.println("Погрузка контейнеров на судно " + getName());
        int containersResidues = 0;
        try {
            lock.lock();
            if ((this.containersQuantity.get() + containersNumber) > CONTAINERS_CAPACITY) {
                containersResidues = (containersNumber + this.containersQuantity.get()) - CONTAINERS_CAPACITY;
                this.containersQuantity.set(CONTAINERS_CAPACITY);
            } else {
                this.containersQuantity.addAndGet(containersNumber);
            }
        } finally {
            lock.unlock();
        }
        return containersResidues;
    }
}
