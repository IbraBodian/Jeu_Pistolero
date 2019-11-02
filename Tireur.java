/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author galse
 */
public class Tireur {
    public ImageView tireur;
    IntegerProperty balleDisponible=new SimpleIntegerProperty();//Possibilité pour l'utilisateur de choisir un nombre infini
    //public Munition balle;
    public String orientation; //gauche ou droite
    DoubleProperty vitesse = new SimpleDoubleProperty();//contrôler la vitesse du tireur avec un slider
    RotateTransition rt;
    
    public Tireur(String image,int bd){
        tireur=new ImageView(new Image(getClass().getResource(image).toExternalForm()));
        tireur.setFitHeight(100);tireur.setFitWidth(100);tireur.setPreserveRatio(true);
        balleDisponible.setValue(bd);
        orientation="gauche";
        vitesse.setValue(4);
        rt = new RotateTransition(Duration.seconds(1),tireur);
        rt.setAxis(new Point3D(0.0, 1.0, 0.0));
        //rt.setCycleCount(1);
    }
    IntegerProperty balleDisponibleProperty() {
        return balleDisponible;
    }
    void setBalleDisponible(int r) {
        balleDisponible.setValue(r);
    }
    int getBalleDisponible() {
        return balleDisponible.getValue();
    }
    DoubleProperty vitesseProperty() {
        return vitesse;
    }
    void setVitesse(double r) {
        vitesse.setValue(r);
    }
    double getVitesse() {
        return vitesse.getValue();
    }
    
    public void rotation(){
        if(orientation.equals("gauche")){
            rt.setFromAngle(0);
            rt.setToAngle(180);
            rt.play();
            orientation="droite";
        }else{
            rt.setFromAngle(180);
            rt.setToAngle(360);
            rt.play();
            orientation="gauche";
        }
    }    
    
   
    public void shoot(InputEvent e,Munition balle){
        if(e instanceof KeyEvent){                            //Au Clavier,Barre d'espace
            if(orientation.equals("gauche")){
                //afficher la balle dans l'interface graphique
		balle.setCenterX(tireur.getLayoutX()-15);
                balle.setCenterY(tireur.getLayoutY()+2);
		balle.relocate(tireur.getLayoutX()-15,tireur.getLayoutY()+2);
                balle.setVisible(true);
		AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                       balle.setCenterX(balle.getCenterX()-5);
                       
                    }
                };
                timer.start();
                setBalleDisponible(getBalleDisponible()-1);
            }else{
                //afficher la balle dans l'interface graphique
		balle.setCenterX(tireur.getLayoutX()+35);
                balle.setCenterY(tireur.getLayoutY()+2);
		balle.relocate(tireur.getLayoutX()+25,tireur.getLayoutY()+2);
                balle.setVisible(true);
		AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                       balle.setCenterX(balle.getCenterX()+5);
                       
                    }
                };
                timer.start();
                setBalleDisponible(getBalleDisponible()-1);
            }
            
        }else if(e instanceof MouseEvent){                          //A la souris
            if(orientation.equals("gauche") && ((MouseEvent) e).getX()>tireur.getLayoutX()+25){
                rotation();
            }
            if(orientation.equals("droite") && ((MouseEvent) e).getX()<tireur.getLayoutX()){
                rotation();
            }
            if(orientation.equals("gauche")){
                //afficher la balle dans l'interface graphique
		balle.setCenterX(tireur.getLayoutX()-15);
                balle.setCenterY(tireur.getLayoutY()+2);
		balle.relocate(tireur.getLayoutX()-15,tireur.getLayoutY()+2);
                balle.setVisible(true);
                double c=balle.getCenterX();
                double d=balle.getCenterY();
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                       balle.setCenterX(balle.getCenterX()-5);
                       balle.setCenterY(f(balle.getCenterX()-5,((MouseEvent) e).getX(),((MouseEvent) e).getY(),c,d));
                       
                    }
                };
                timer.start();
                setBalleDisponible(getBalleDisponible()-1);
            }else{
                 //afficher la balle dans l'interface graphique
		balle.setCenterX(tireur.getLayoutX()+35);
                balle.setCenterY(tireur.getLayoutY()+2);
		balle.relocate(tireur.getLayoutX()+25,tireur.getLayoutY()+2);
                balle.setVisible(true);
                double c=balle.getCenterX();
                double d=balle.getCenterY();
                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long now) {
                       balle.setCenterX(balle.getCenterX()+5);
                       balle.setCenterY(f(balle.getCenterX()+5,((MouseEvent) e).getX(),((MouseEvent) e).getY(),c,d));
                       
                    }
                }; 
                timer.start();
                setBalleDisponible(getBalleDisponible()-1);
            }
        }
        
    }
    public void move(String direction){
        double rayonY=tireur.getBoundsInLocal().getHeight()/2;
        double rayonX=tireur.getBoundsInLocal().getWidth()/2;
        
                if(direction.equals("Nord")){
                    double y=tireur.getLayoutY()+rayonY-3*getVitesse();
                    if(y-rayonY>=0 ){  //si ca ne deborde pas de l'écran
                       tireur.setLayoutY(tireur.getLayoutY()-3*getVitesse()); 
                       
                    }
                }    
                if(direction.equals("Ouest")){
                    double x=tireur.getLayoutX()+rayonX-3*getVitesse();
                    if(x-rayonX>=0){   //si ca ne deborde pas de l'écran
                       tireur.setLayoutX(tireur.getLayoutX()-3*getVitesse()); 
                       
                    }
                    //faire l'obstacle aussi
                }
                if(direction.equals("Sud")){
                    double y=tireur.getLayoutY()+rayonY+3*getVitesse();
                    if(y+rayonY<=700){   //si ca ne deborde pas de l'écran
                       tireur.setLayoutY(tireur.getLayoutY()+3*getVitesse()); 
                       
                    }
                    //faire l'obstacle aussi
                }
                 if(direction.equals("Est")){
                    double x=tireur.getLayoutX()+rayonX+3*getVitesse();
                    if(x+rayonX<=1400){   //si ca ne deborde pas de l'écran
                        tireur.setLayoutX(tireur.getLayoutX()+3*getVitesse()); 
                        
                    }
                    //faire l'obstacle aussi
                }
            
       
    }
    
    public double f(double e,double x1,double y1,double x2,double y2){
        double a=(y2-y1)/(x2-x1);
        double b=y1-a*x1;
        return a*e+b ;
    }
    
    
    
}
