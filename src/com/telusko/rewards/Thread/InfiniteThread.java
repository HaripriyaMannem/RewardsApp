package com.telusko.rewards.Thread;
import java.util.concurrent.*;

public class InfiniteThread {

        public static void main(String[] args)
        {
            BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>(); // Shared data structure

            // Create and start the continuous thread
            ContinuousThread continuousThread = new ContinuousThread(sharedQueue);
            continuousThread.start();

            // Consume values from the thread
            while (true) {
                try {
                    Integer value = sharedQueue.take(); // Blocks until a value is available
                    processValue(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        private static void processValue(Integer value) {
            System.out.println("Processed value: " + value);
        }
    }

    class ContinuousThread extends Thread {
        private BlockingQueue<Integer> sharedQueue;

        public ContinuousThread(BlockingQueue<Integer> sharedQueue) {
            this.sharedQueue = sharedQueue;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                // Simulate some work
                int value = generateValue();

                try {
                    sharedQueue.put(value); // Put the value in the shared queue
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private int generateValue() {
            // Simulate generating a value
            return (int) (Math.random() * 100);
        }
}


