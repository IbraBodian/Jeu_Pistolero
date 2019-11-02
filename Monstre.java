/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.util.Random;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author galse
 */
public class Monstre {
    public ImageView monstre;
    public String sexe;
    DoubleProperty vitesse = new SimpleDoubleProperty();
    
    public Monstre(String image,String s){
        monstre=new ImageView(new Image(getClass().getResource(image).toExternalForm()));
        monstre.setFitHeight(30);monstre.setFitWidth(30);monstre.setPreserveRatio(true);
        sexe=s;
        vitesse.setValue(5);
    }
    
    public double getVitesse() {
        return vitesse.getValue();
    }
    DoubleProperty vitesseProperty(){
        return vitesse;
    }
    public void setImage(Image im){
        monstre.setImage(im);
        monstre.setFitHeight(50);monstre.setFitWidth(50);monstre.setPreserveRatio(true);
        sexe="dragon";
    }
    public void mouvementAleatoire(double a,double b){
        double rayonY=monstre.getBoundsInLocal().getHeight()/2;
        double rayonX=monstre.getBoundsInLocal().getWidth()/2;
        
        Random rand = new Random(System.currentTimeMillis());
        monstre.setLayoutX(monstre.getLayoutX()+a*getVitesse());
        monstre.setLayoutY(monstre.getLayoutY()+b*getVitesse());
        if(monstre.getLayoutX()+a*getVitesse()<0){
            a=10*(rand.nextDouble());
            monstre.setLayoutX(monstre.getLayoutX()+a*getVitesse());
        }
        if(monstre.getLayoutY()+b*getVitesse()<0){
            b=10*(rand.nextDouble());
            monstre.setLayoutY(monstre.getLayoutY()+b*getVitesse());
        }
        if(monstre.getLayoutX()+a*getVitesse()+2*rayonX>1400){
            a=10*(rand.nextDouble()-1);
            monstre.setLayoutX(monstre.getLayoutX()+a*getVitesse());
        }
        if(monstre.getLayoutY()+b*getVitesse()+2*rayonY>700){
            b=10*(rand.nextDouble()-1);
            monstre.setLayoutY(monstre.getLayoutY()+b*getVitesse());
        }
    }
    public void mouvementIntelligent(double a,Tireur t){
        double rayonX=monstre.getBoundsInLocal().getWidth()/2;
        
        Random rand = new Random(System.currentTimeMillis());
        monstre.setLayoutX(monstre.getLayoutX()+a*getVitesse());
        monstre.setLayoutY(t.tireur.getLayoutY()+a*getVitesse());
        
        if(monstre.getLayoutX()+a*getVitesse()<0){
            a=10*(rand.nextDouble());
            monstre.setLayoutX(monstre.getLayoutX()+a*getVitesse());monstre.setLayoutY(t.tireur.getLayoutY()+a*getVitesse());
        }
        if(monstre.getLayoutX()+a*getVitesse()+2*rayonX>1400){
            a=10*(rand.nextDouble()-1);
            monstre.setLayoutX(monstre.getLayoutX()+a*getVitesse());monstre.setLayoutY(t.tireur.getLayoutY()+a*getVitesse());
        }
    }
    public void mouvementSemi(Tireur t){
        
    }
}
