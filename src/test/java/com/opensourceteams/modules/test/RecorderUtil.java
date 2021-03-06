package com.opensourceteams.modules.test;


import javax.sound.sampled.*;
import java.io.File;

public class RecorderUtil {
    AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, true);
    static TargetDataLine targetDataLine = null;

    public void start() {
        try {


            DataLine.Info targetInfo =
                    new DataLine.Info(
                            TargetDataLine.class,
                            audioFormat); // Set the system information to read from the microphone audio stream


            if (!AudioSystem.isLineSupported(targetInfo)) {
                System.out.println("麦克风不支持,直接退出");
                System.exit(0);
            }
            // Target data line captures the audio stream the microphone produces.
             targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);

            targetDataLine.open(audioFormat);
            targetDataLine.start();
            System.out.println("开始录制音频......");
            AudioInputStream audioInputStream = new AudioInputStream(targetDataLine);

            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE,
                    new File("1.wav"));

            while (true){
                byte[] data = new byte[6400];
                audioInputStream.read(data);


            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            stop();
        }
    }

    public static void main(String[] args) {
        new RecorderUtil();
    }



    /**
     * 关闭
     */
    public static void stop(){
        targetDataLine.stop();
        targetDataLine.close();
    }

    /**
     * 开始线程
     */
    public static void begin(){
        targetDataLine.stop();
        targetDataLine.close();
    }

    public RecorderUtil() {
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                targetDataLine.stop();
                targetDataLine.close();
                System.out.println("录制完成");


            }
        });
        stopper.start();
        start();
    }
}