package MainTask;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private int containersNumber;
    public static final int CONTAINERS_CAPACITY = 500;
    public static final int DOCKS_NUMBER = 10;
    private final Semaphore docksSemaphore = new Semaphore(DOCKS_NUMBER, true);
    private final ArrayDeque<Ship> ships = new ArrayDeque<Ship>(DOCKS_NUMBER);

    private static boolean status = true;
    private static Port instance = null;
    private static final ReentrantLock portLock = new ReentrantLock();

    private final Lock lockAdd = new ReentrantLock();
    private final Lock lockRemove = new ReentrantLock();

    private static final ReentrantLock loadLock = new ReentrantLock();

    private Port() {
    }

    public static Port getInstance() {
        if (status) {
            portLock.lock();
            try {
                if (instance == null) {
                    instance = new Port();
                    status = false;
                }
            } finally {
                portLock.unlock();
            }
        }
        return instance;
    }

    public void serveShip(Ship ship) {
        try {
            lockAdd.lock();
            docksSemaphore.acquire();
            ships.addLast(ship);
            System.out.println("Корабль пришел в порт " + ship.getName());
        } catch (InterruptedException e) {
            System.out.println(e);
        } finally {
            lockAdd.unlock();
        }
    }

    public void sendShip(Ship ship) {
        System.out.println("Корабль ушел из порта " + ship.getName());
        try {
            lockRemove.lock();
            ships.remove(ship);
            docksSemaphore.release();
        } finally {
            lockRemove.unlock();
        }
    }

    public boolean loadContainers(int containersNumber) {
        try {
            loadLock.lock();
            if ((this.containersNumber + containersNumber) > CONTAINERS_CAPACITY) {
                System.out.println("Склад переполнен, выполняется разгрузка. Ожидайте.");
                this.containersNumber = 0;
                return false;
            }
            this.containersNumber += containersNumber;
        } finally {
            loadLock.unlock();
        }
        return true;
    }
}