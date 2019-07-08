/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author MON MINA
 */
public class Audio extends Thread{
    private Clip clip;
    private AudioInputStream audioInputStream; 
    private String filePath;
    
    public Audio(String filePath){
        try{
            this.filePath = filePath;
            audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch(UnsupportedAudioFileException uaf){
            
        }catch(IOException ie){
        
        }catch(LineUnavailableException lue){
            
        }
    }
    
    @Override
    public void run(){
        clip.start();
    }
    
    public void mute(){
        clip.stop();
    }
}
