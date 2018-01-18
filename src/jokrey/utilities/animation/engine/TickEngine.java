package jokrey.utilities.animation.engine;

public abstract class TickEngine extends AnimationEngine {
    private long last = System.nanoTime();
    public final void calculate() {
        if(isPaused()) {
            last = System.nanoTime();
            return;
        }
        long newLast = System.nanoTime();
        if(calculate((newLast - last)/1e9))
            last = newLast;
    }
    private final boolean calculate(double delta) {
        double tickEvery = 1.0/getTicksPerSecond();
        if(delta > tickEvery) {
            do {
                calculateTick();
                delta-=tickEvery;
            } while(delta > tickEvery && redoDelayedTicks());
            return true;
        }
        return false;
    }
    protected abstract void calculateTick();
    public int getTicksPerSecond() {return 100;}
    public boolean redoDelayedTicks() {return true;}
}
