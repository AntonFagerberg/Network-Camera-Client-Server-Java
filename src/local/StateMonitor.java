package local;

public class StateMonitor {
    public static final boolean
        MOVIE = true,
        IDLE = false;
    private boolean
        mode = IDLE,
        forceMode = false;
    private long lastTimeStamp;
    public int
    	INT_MOVIE_FORCED = 4,
    	INT_IDLE_FORCED = 3,
    	INT_MOVIE = 2,
    	INT_IDLE = 1;
    private int INT_MODE = 1;
    	
    public synchronized int getMode(int previousMode) {
    	while(previousMode == INT_MODE){
    		try{
    			wait();
    		} catch (InterruptedException e){
    			e.printStackTrace();
    		}
    	}
    	if(forceMode && mode){
    		INT_MODE = INT_MOVIE_FORCED;
    		
    	} else if(forceMode && !mode){
    		INT_MODE = INT_IDLE_FORCED;
    		
    	} else if(!forceMode && mode){
    		INT_MODE = INT_MOVIE;
    		
    	} else if(!forceMode && !mode){
    		INT_MODE = INT_IDLE;
    	}
    	
    	return INT_MODE;
    	
    }

    public synchronized void setMode(boolean mode) {
        if (!forceMode) {
            this.mode = mode;
            notifyAll();
        }
    }

    public synchronized void setForcedMode(boolean mode) {
        forceMode = true;
        this.mode = mode;
        notifyAll();
    }

    public synchronized void unsetForcedMode() {
        forceMode = false;
        notifyAll();
    }

    public synchronized void synchronizeTimeStamps(int cameraIndex, long imageTimeStamp) {
        if (lastTimeStamp < 0) {
            lastTimeStamp = imageTimeStamp;
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (lastTimeStamp - imageTimeStamp > 0) {
                System.out.println("Waiting: " + (lastTimeStamp - imageTimeStamp) + " for thread: " + Thread.currentThread().getId());
                try {
                    wait(lastTimeStamp - imageTimeStamp);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            lastTimeStamp = -1;
            notify();
        }
    }
}
