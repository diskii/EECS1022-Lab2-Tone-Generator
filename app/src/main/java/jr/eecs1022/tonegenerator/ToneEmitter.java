package jr.eecs1022.tonegenerator;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;


/**
 * This is a very simple tone Emitter class. It fires off a background thread
 * to feed audio samples to the android AudioTrack to generate output.
 *
 * @author Michael Jenkin
 * @version 1.0
 */
public class ToneEmitter implements Runnable
{
    public static final double NOTE_G_BELOW_C = 196;
    public static final double NOTE_A = 220;

    public static final double NOTE_A_SHARP = 233;
    public static final double NOTE_B = 247;
    public static final double NOTE_C = 262;
    public static final double NOTE_C_SHARP = 277;
    public static final double NOTE_D = 294;
    public static final double NOTE_D_SHARP = 311;
    public static final double NOTE_E = 330;
    public static final double NOTE_F = 349;
    public static final double NOTE_F_SHARP = 370;
    public static final double NOTE_G = 392;
    public static final double NOTE_G_SHARP = 415;
    public static final double NOTE_A_ABOVE_MIDDLE_C = 440;
    public static final double NOTE_B_ABOVE_MIDDLE_C = 494;
    public static final double NOTE_C_ABOVE_MIDDLE_C = 523;

    private double desiredAmplitude = 0.0f;
    private double curAmplitude = 0.0f;
    private double desiredFrequency = NOTE_C;
    private double curFrequency = NOTE_C;

    final static public int SAMPLING_RATE = 44100;           // our sampling rate

    private double fCyclePosition = 0;  // where we are in our cycle (0..2 pi)
    private boolean alive = true;
    private Thread player = null;
    private AudioTrack audioTrack;
    private short[] buffer;

    /**
     * Construct an Emitter. This will begin emitting a sine wave (fortunately at zero volume)
     */
    public ToneEmitter()
    {
        int buffsize = AudioTrack.getMinBufferSize(SAMPLING_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        buffer = new short[buffsize];

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLING_RATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, buffsize, AudioTrack.MODE_STREAM);
        player = new Thread(this);
        player.setPriority(Thread.MAX_PRIORITY);
        player.start();
    }

    /**
     * Set the frequency of the sound to be generated. If the frequency is invalid, this does
     * nothing.
     *
     * @param frequency the frequency (in hertz) of the output sound.
     */
    public void setFrequency(double frequency)
    {
        if (frequency >= 0)
            this.desiredFrequency = frequency;
    }

    /**
     * Return the frequency of the sound currently being generated.
     *
     * @return the frequency (in hertz) of the sound being generated.
     */
    public double getFrequency()
    {
        return this.desiredFrequency;
    }


    /**
     * Set the amplitude of the sound currently being generated.
     *
     * @param amplitude the volume of the sound being generated in the range (0..1).
     *                  0 is the lowest possible volume. 1 is the maximum. It is not possible to set the output to 11.
     */
    public void setAmplitude(double amplitude)
    {
        if ((amplitude < 0) || (amplitude > 1))
            this.desiredAmplitude = 1;
        this.desiredAmplitude = amplitude;
    }

    /**
     * Return the current volume of the sound being generated.
     *
     * @return the current volume in the range 0..1.
     */
    public double getVolume()
    {
        return this.desiredAmplitude;
    }

    /**
     * Shutdown the audio generation.
     */
    public void shutdown()
    {
        // shutdown the channel
        this.alive = false;
        try
        {
            player.join();
        } catch (InterruptedException e)
        {
        }
    }


    /**
     * This is the thread that services the output channel.
     */
    public void run()
    {
        audioTrack.play();
        while (alive)
        {
            double fCycleInc = this.curFrequency / SAMPLING_RATE;

            //Generate buffer.length samples based on the current fCycleInc from fFreq
            for (int i = 0; i < buffer.length; i++)
            {
                double signal = Math.sin(2 * Math.PI * fCyclePosition);
                if (Math.abs(signal) < 0.01)
                {
                    if (this.desiredAmplitude != this.curAmplitude)
                        this.curAmplitude = this.desiredAmplitude;
                    if (this.desiredFrequency != this.curFrequency)
                        this.curFrequency = this.desiredFrequency;
                }
                buffer[i] = (short) (Short.MAX_VALUE * this.curAmplitude * signal);

                fCyclePosition += fCycleInc;
                if (fCyclePosition > 1)
                    fCyclePosition -= 1;
            }
            audioTrack.write(buffer, 0, buffer.length); // will block until needed
        }
        audioTrack.stop();
        audioTrack.release();
        audioTrack = null;
    }
}