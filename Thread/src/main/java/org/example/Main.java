package org.example;

import java.io.IOException;

public class Main {
    private static final Object monitor = new Object();
    private static volatile boolean running = true;
    private static int currentThread = 0;

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Туманова Екатерина Александровна, группа РИБО-02-22, вариант 1, практика1_14");
        System.out.println("Нажмите Enter чтобы остановить потоки");
        Thread thread0 = new Thread(() -> {
            while (running) {
                synchronized (monitor) {
                    try {
                        while (currentThread != 0) {
                            monitor.wait();
                        }
                        System.out.println("Thread-0");
                        currentThread = 1;
                        monitor.notifyAll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread thread1 = new Thread(() -> {
            while (running) {
                synchronized (monitor) {
                    try {
                        while (currentThread != 1) {
                            monitor.wait();
                        }
                        System.out.println("Thread-1");
                        currentThread = 2;
                        monitor.notifyAll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (running) {
                synchronized (monitor) {
                    try {
                        while (currentThread != 2) {
                            monitor.wait();
                        }
                        System.out.println("Thread-2");
                        currentThread = 0;
                        monitor.notifyAll();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread0.start();
        thread1.start();
        thread2.start();

        // Ждем нажатия клавиши для завершения работы потоков
        System.in.read();
        running = false;

        // Разбудим все потоки, чтобы они могли завершиться
        synchronized (monitor) {
            monitor.notifyAll();
        }
    }
}
