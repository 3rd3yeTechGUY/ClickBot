package sangi.bot;

import java.awt.*;

public class AutoClickerBot {
    // Constants
    private static final int DEFAULT_MILLIS_DELAY = 100;
    private static final int DEFAULT_CLICK_VAL = 0;

    // Member Variables
    private Thread botThread;
    private AutoClickerRunnable autoClickerRunnable;
    private int clickCount = DEFAULT_CLICK_VAL;
    private int millisDelay = DEFAULT_MILLIS_DELAY;
    private boolean isManualStop;
    private boolean isRandomized = true;
    private volatile int clickCounter = 0;
    private final Object clickLock = new Object();
    private volatile boolean isRunning = false;
    private final Object runningLock = new Object();

    private class AutoClickerRunnable implements Runnable{
        private SmartRobot robot;

        private AutoClickerRunnable() throws AWTException{
            robot = new SmartRobot();

        }

        @Override
        public void run() {
            if (isManualStop){
                startClickingForever();
            }
            else {
                startClickCount();
            }
        }

        private void startClickCount(){
            boolean shouldRun;
            synchronized (runningLock){
                shouldRun = isRunning;
            }

            while (clickCounter < clickCount && shouldRun){

                if (isRandomized){
                    robot.randomMouseClick(millisDelay);
                }
                else {
                    robot.mouseClick(millisDelay);
                }

                synchronized (clickLock){
                    clickCounter++;
                }

                synchronized (runningLock){
                    shouldRun = isRunning;
                }
            }

        }

        private void startClickingForever(){
            boolean shouldRun;
            synchronized (runningLock){
                shouldRun = isRunning;
            }

            while (shouldRun){

                if (isRandomized){
                    robot.randomMouseClick(millisDelay);
                }
                else {
                    robot.mouseClick(millisDelay);
                }

                synchronized (runningLock){
                    shouldRun = isRunning;
                }
            }

        }

    }

    public AutoClickerBot() throws AWTException{
        autoClickerRunnable = new AutoClickerRunnable();
        botThread = new Thread(autoClickerRunnable);
        botThread.setDaemon(true);
    }

    public void beginAutoClick(int delay, int clicks, boolean isManualStop, boolean isRandomized){
        millisDelay = delay;
        clickCount = clicks;
        this.isRandomized = isRandomized;
        this.isManualStop = isManualStop;
        synchronized (clickLock){
            clickCounter = 0;
        }
        synchronized (runningLock){
            isRunning = true;
        }


        if (botThread.getState() == Thread.State.TERMINATED){
            botThread = new Thread(autoClickerRunnable);
            botThread.setDaemon(true);
        }

        if (!botThread.isAlive()){
            botThread.start();
        }

    }

    public void stopAutoClick(){
        synchronized (runningLock){
            isRunning = false;
        }
    }


}
