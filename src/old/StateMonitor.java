package old;

public class StateMonitor {
    public static final boolean
        MOVIE = true,
        IDLE = false;
    private boolean
        mode = IDLE,
        forceMode = false;
    public static int
    	INT_MOVIE_FORCED = 4,
    	INT_IDLE_FORCED = 3,
    	INT_MOVIE = 2,
    	INT_IDLE = 1;
    private int INT_MODE = 1;
    	
    public synchronized int getMode(int previousMode) {
    	while (previousMode == INT_MODE) {
    		System.out.println("woot");
    		try{
    			wait();
    		} catch (InterruptedException e){
    			e.printStackTrace();
    		}
    	}

        if (forceMode && mode){
    		INT_MODE = INT_MOVIE_FORCED;
    	} else if (forceMode){
    		INT_MODE = INT_IDLE_FORCED;
    	} else if (mode){
    		INT_MODE = INT_MOVIE;
    	} else {
    		INT_MODE = INT_IDLE;
    	}
    	System.out.println("trololol");
    	return INT_MODE;
    }

    public synchronized void setMode(boolean mode) {
        if (!forceMode) {
            this.mode = mode;
            if(mode){
            	INT_MODE = INT_MOVIE;
            } else {
            	INT_MODE = INT_IDLE;
            }
            System.out.println("setMode changed " + mode);
            notifyAll();
        }
    }

    public synchronized void setForcedMode(boolean mode) {
        forceMode = true;
        this.mode = mode;
        if(mode){
        	INT_MODE = INT_MOVIE_FORCED;
        } else {
        	INT_MODE = INT_IDLE_FORCED;
        }
        System.out.println("forceMode True| mode: " + mode);
        notifyAll();
    }

    public synchronized void unsetForcedMode() {
        forceMode = false;
        System.out.println("forceMode False");
        INT_MODE = INT_IDLE;
        notifyAll();
    }
}
