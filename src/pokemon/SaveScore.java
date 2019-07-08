/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokemon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MON MINA
 */
public class SaveScore implements Serializable{
    
    private static final long serialVersionUID = 1;
//    private String file = "D:\\highscore.txt";
    private String filePath = "src/highscore.txt";
    private File file = new File(filePath);
    private ArrayList<Player> listHighScore;
    
    public SaveScore(){
        try {
            if(!file.exists()){
                file.createNewFile();
            }
        } catch (IOException ex) {
                System.out.println("Không thể truy xuất IO");
        }
    }
    public SaveScore(String name, long score){
        SaveScore ss = null;
        try{
            file = new File(filePath);
            if(!file.exists()){
                file.createNewFile();
            }
            
            if(file.length()!=0){
                
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                ss = (SaveScore) ois.readObject();
                ois.close();

                ArrayList<Player> listHighScore = ss.getListHighScore();
                Player lastPlayer = listHighScore.get(listHighScore.size()-1);
                
                Player newScore = new Player(name, score);
                listHighScore.add(newScore);
                ss.setListHighScore(listHighScore);
                if(score > lastPlayer.getScore()){
                    ss.sortPlayer();
                }
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(ss);
                oos.close();
                
            }else{
                Player newScore = new Player(name, score);
                listHighScore = new ArrayList<>();
                listHighScore.add(newScore);
                ss = new SaveScore();
                ss.setListHighScore(listHighScore);
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
                oos.writeObject(ss);
                oos.close();
            }
            
        }catch(FileNotFoundException fne){
            System.out.println("Không tìm thấy file");
        }catch(IOException ie){
            System.out.println("Không truy xuất được IO");
        }catch(ClassNotFoundException cnfe){
            System.out.println("Không tìm thấy class");
        }
    }
    
    public ArrayList<Player> getListHighScore(){
        return listHighScore;
    }
    
    public void setListHighScore(ArrayList<Player> listHighScore){
        this.listHighScore = listHighScore;
    }
    
    public void sortPlayer(){
        for(int i=0; i<listHighScore.size()-1; i++){
            for(int j=i+1; j<listHighScore.size(); j++){
                if(listHighScore.get(i).getScore() < listHighScore.get(j).getScore()){
                    Player temp = listHighScore.get(i);
                    listHighScore.set(i, listHighScore.get(j));
                    listHighScore.set(j, temp);
                }
            }
        }
    }
    
    public void clearScores(){
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(SaveScore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Object[][] getPlayerList() {
        
        SaveScore ss = null;
        try{            
            if(file.length()!=0){
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                ss = (SaveScore) ois.readObject();
                ois.close();
            }
        }catch(FileNotFoundException fne){
            System.out.println("Không tìm thấy file");
        }catch(IOException ie){
            ie.printStackTrace();
            System.out.println("Không truy xuất được IO");
        }catch(ClassNotFoundException cnfe){
            System.out.println("Không tìm thấy class");
        }
        
        Object[][] data = null;
        if(ss != null){
            listHighScore = ss.getListHighScore();
            data = new Object[listHighScore.size()][2];

            for(int i=0; i<listHighScore.size(); i++){
                data[i][0] = listHighScore.get(i).getName();
                data[i][1] = listHighScore.get(i).getScore();
            }
        }
        
        return data;
    }
    
    class Player implements Serializable{
        private String name;
        private long score;
        
        public Player(){}
        public Player(String name, long score){
            this.name = name;
            this.score = score;
        }
        
        public void setName(String name) {
            this.name = name;
        }

        public void setScore(long score) {
            this.score = score;
        }


        public String getName() {
            return name;
        }

        public long getScore() {
            return score;
        }

        @Override
        public String toString() {
            return "Player{" + "name=" + name + ", score=" + score + '}';
        }   
    }
    
}
