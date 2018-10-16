package jokrey.utilities.animation.sound;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;

public class ToggleableSoundThread implements Runnable {
    public final static int SAMPLE_RATE = 44100;

    public void stop() {
        kill = true;
    }

    private final int buffer_cache_in_ms;
    private final int buffer_size;
    public ToggleableSoundThread() throws LineUnavailableException {
        this(222, true);
    }
    public ToggleableSoundThread(int frequency) throws LineUnavailableException {
        this(frequency, true);
    }
    public ToggleableSoundThread(int frequency, boolean start_paused) throws LineUnavailableException {
        this(50, frequency, start_paused);
    }
    public ToggleableSoundThread(int buffer_cache_in_ms, int frequency, boolean start_paused) throws LineUnavailableException {
        this.buffer_cache_in_ms=buffer_cache_in_ms;
        buffer_size = (int)((buffer_cache_in_ms/1000.0)* SAMPLE_RATE * 2);
        AudioFormat format = new AudioFormat(44100, 16, 1, true, true);

        line = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format, buffer_size * 2));
        line.open(format);

        this.frequency = frequency;
        this.paused=start_paused;

        new Thread(this).start();
    }




    private SourceDataLine line;
    public int frequency;
    public boolean paused;
    public boolean kill = false;

    public void run() {
        line.start();

        ByteBuffer actual_buf = ByteBuffer.allocate(buffer_size);
        double wavePos = 0; //current sine
        while (!kill) {
            while (paused && !kill) {
                line.drain();
                sleep(buffer_cache_in_ms);
            }

            double frequency_for_loop = frequency; //store to avoid possible threading issues upon sudden change, stored as double to make (frequency_for_loop / SAMPLE_RATE) later possible.

            actual_buf.clear();

            for (int i = 0; i < buffer_size / 2; i++) {
                actual_buf.putShort((short) (Short.MAX_VALUE * Math.sin(2 * Math.PI * wavePos)));

                wavePos += frequency_for_loop / SAMPLE_RATE;
                if(wavePos > 1) wavePos -= 1; //reset sinus function
            }

            line.write(actual_buf.array(), 0, actual_buf.position());

            //wait till continuation is allowed
            while (buffer_size < (line.getBufferSize() - line.available()) && !kill) {
                sleep(1);
            }
        }

        line.drain();
        line.close();
        line=null;
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}