
package projet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 *
 * @author galse
 */
public final class UniversJeu {
    private Scene scene;//pour le background
    private List <Monstre> mechant;//Méchants
    private Tireur pistolero;//Le tireur
    private List<Node> obstacles;//Les obstacles
    private Slider vitMechant;
    private Slider vitPistolero;
    IntegerProperty NbMonstre=new SimpleIntegerProperty();
    
    public UniversJeu(Stage primaryStage){
        pistolero=new Tireur("shooting-stick-man.png",10);
        obstacles=new ArrayList<Node>();
        mechant=new ArrayList<Monstre>();
        Pane pane=new Pane(pistolero.tireur);pistolero.tireur.setLayoutX(700);pistolero.tireur.setLayoutY(350);
        BorderPane root=new BorderPane();
        root.setCenter(pane);
        Random rand = new Random(System.currentTimeMillis());
        for(int i=0;i<2;i++){
            obstacles.add(new ImageView(new Image(getClass().getResource("Philosophers-Stone-2-icon.png").toExternalForm())));
            int x=rand.nextInt(1200);
            int y=rand.nextInt(600);
            pane.getChildren().add(obstacles.get(i));
            obstacles.get(i).setLayoutX(x);
            obstacles.get(i).setLayoutY(y);
        }
        for(int j=0;j<7;j++){
            if(j%2==0){
                mechant.add(new Monstre("monster-orange-icon.png","male"));
                int x=rand.nextInt(600);
                mechant.get(j).monstre.setLayoutX(x);
            }else{
                mechant.add(new Monstre("monster-violet-icon.png","femelle"));
                mechant.get(j).monstre.setLayoutX(550);
            }
            pane.getChildren().add(mechant.get(j).monstre);
            mechant.get(j).monstre.setLayoutY(350);
        }
        setNbMonstre(7);
        root.setId("back");
        affichage(root);
        scene=new Scene(root,1400,700);
        actionUtilisateur(pane,obstacles,mechant);
        actionOrdinateur(mechant);
        monsterCollision(mechant,pane,obstacles,pistolero);
        scene.getStylesheets().addAll(this.getClass().getResource("Background.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    IntegerProperty nbMonstreProperty() {
        return NbMonstre;
    }
    void setNbMonstre(int r) {
        NbMonstre.setValue(r);
    }
    int getNbMonstre() {
        return NbMonstre.getValue();
    }    
    public void actionUtilisateur(Pane pane,List<Node> obstacles,List <Monstre> monster){
        pistolero.tireur.setFocusTraversable(true);
        pistolero.tireur.requestFocus();
        pane.setFocusTraversable(true);
        pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                switch (e.getCode()){
                    case UP: pistolero.move("Nord"); break;
                    case LEFT:  pistolero.move("Ouest"); break;
                    case DOWN:  pistolero.move("Sud"); break;
                    case RIGHT: pistolero.move("Est"); break;
                }
            }
        });
        
            
        pane.addEventFilter(KeyEvent.KEY_PRESSED,e->{
            if(pistolero.getBalleDisponible()>0){
                    if(e.getCode()==KeyCode.SPACE){
                        Munition b=new Munition();
                        b.setVisible(false);
                        pane.getChildren().add(b);
                        pistolero.shoot(e,b);
                        for(int j=0;j<obstacles.size();j++){
                            final int jj=j;
                            b.boundsInParentProperty().addListener((ob,ov,nv)->{
                                if(nv.intersects(obstacles.get(jj).getBoundsInParent()))
                                    pane.getChildren().remove(b);
                            });        
                        }
                        List <Monstre> bool=new ArrayList <Monstre>();
                        for(int i=0;i<monster.size();i++){
                            final int ii=i;
                            monster.get(i).monstre.boundsInParentProperty().addListener((ob,ov,nv)->{
                                if(nv.intersects(b.getBoundsInParent())){
                                    pane.getChildren().remove(monster.get(ii).monstre);
                                    setNbMonstre(getNbMonstre()-1);
                                    bool.add(monster.get(ii));
                                }
                            });        
                        }
                        monster.removeAll(bool);
                    }
            }else{
                    System.out.println("Vous n'avez plus de munition");
            }    
        });
        pane.addEventFilter(MouseEvent.MOUSE_CLICKED,e->{
             if(pistolero.getBalleDisponible()>0){
                        Munition b=new Munition();
                        b.setVisible(false);
                        pane.getChildren().add(b);
                        pistolero.shoot(e,b);
                        for(int j=0;j<obstacles.size();j++){
                            final int jj=j;
                            b.boundsInParentProperty().addListener((ob,ov,nv)->{
                                if(nv.intersects(obstacles.get(jj).getBoundsInParent()))
                                    pane.getChildren().remove(b);
                            });        
                        }
                        List <Monstre> bool=new ArrayList <Monstre>();
                        for(int i=0;i<monster.size();i++){
                            final int ii=i;
                            monster.get(ii).monstre.boundsInParentProperty().addListener((ob,ov,nv)->{
                                if(nv.intersects(b.getBoundsInParent())){
                                    pane.getChildren().remove(monster.get(ii).monstre);
                                    setNbMonstre(getNbMonstre()-1);
                                    bool.add(monster.get(ii));
                                }
                            });        
                        }
                        monster.removeAll(bool);
                 }else
                      System.out.println("Vous n'avez plus de munition"); 
         });             
                
             
        
    }                     
                        
                        
    
    
    public void actionOrdinateur(List <Monstre> monster){
        Random rand = new Random(System.currentTimeMillis());
        AnimationTimer timer=new AnimationTimer(){
            @Override
            public void handle(long now){
               for(Monstre m:monster){
                   double a=10*(rand.nextDouble()-0.5);
                   double b=10*(rand.nextDouble()-0.5);
                   m.mouvementAleatoire(a,b);
                   //m.mouvementIntelligent(a, pistolero);
               }
            }
        };
        timer.start();
    }
    
    public void monsterCollision(List <Monstre> monster,Pane pane,List<Node> obstacles,Tireur t){
       List <Monstre> bool=new ArrayList <Monstre>();
       for(int i=0;i<monster.size()-1;i++){
            for(int j=i+1;j<monster.size();j++){
                final int ii=i;final int jj=j;
                monster.get(i).monstre.boundsInParentProperty().addListener((ob,ov,nv)->{
                    if(nv.intersects(monster.get(jj).monstre.getBoundsInParent()))
                        if(monster.get(ii).sexe.equals(monster.get(jj).sexe) && monster.get(ii).sexe.equals("male")){
                           pane.getChildren().remove(monster.get(ii).monstre);
                           setNbMonstre(getNbMonstre()-1);
                           bool.add(monster.get(ii));
                        }else if((!monster.get(ii).sexe.equals(monster.get(jj).sexe))){
                            pane.getChildren().remove(monster.get(ii).monstre);
                            setNbMonstre(getNbMonstre()-1);
                            monster.get(jj).setImage(new Image(getClass().getResource("dragon.png").toExternalForm()));
                            bool.add(monster.get(ii));
                        }            
                 });        
            }    
        }                
        monster.removeAll(bool);
              
                        
        
       for(int k=0;k<monster.size();k++){
           for(int l=0;l<obstacles.size();l++){
                final int kk=k;final int ll=l;
                monster.get(k).monstre.boundsInParentProperty().addListener((ob,ov,nv)->{
                    if(nv.intersects(obstacles.get(ll).getBoundsInParent())){
                       intersectionObstacle(monster.get(kk).monstre,obstacles.get(ll));
                    }
                        
                });        
            }   
        }           
            
        for(int m=0;m<obstacles.size();m++){
            final int mm=m;
            t.tireur.boundsInParentProperty().addListener((ob,ov,nv)->{
                if(nv.intersects(obstacles.get(mm).getBoundsInParent())){
                   intersectionObstacle(t.tireur,obstacles.get(mm));
                }
                        
            });        
        }   
       
       /*for(int n=0;n<monster.size();n++){
           monster.get(n).monstre.boundsInParentProperty().addListener((ob,ov,nv)->{
               if(nv.intersects(t.tireur.getBoundsInParent())){
                    pane.getChildren().clear();
                    Text text = new Text("Fin de Partie: vous avez perdue"); 
                    text.setFont(Font.font("Times", FontWeight.SEMI_BOLD, 35));
                    text.setLayoutX(400);text.setLayoutY(300);
                    pane.getChildren().add(text);
               }
               
           });
        }*/
       
       
    }   
       
    /*public boolean intersectionDetecte(Node a,Node b){
        boolean collision=false;
        if(a.getBoundsInParent().intersects(b.getBoundsInParent()))
            collision=true;
        return collision;
    }*/
    
    public void intersectionObstacle(Node a,Node b){
        if(a.getLayoutX()<=b.getLayoutX() && a.getLayoutY()<=b.getLayoutY()){
            a.setLayoutX(a.getLayoutX()-1);
            a.setLayoutY(a.getLayoutY()-1);
        }
        if(a.getLayoutX()<=b.getLayoutX() && a.getLayoutY()>b.getLayoutY()){
            a.setLayoutX(a.getLayoutX()-1);
            a.setLayoutY(a.getLayoutY()+1);
        }
        if(a.getLayoutX()>b.getLayoutX() && a.getLayoutY()<=b.getLayoutY()){
            a.setLayoutX(a.getLayoutX()+1);
            a.setLayoutY(a.getLayoutY()-1);
        }
        if(a.getLayoutX()>b.getLayoutX() && a.getLayoutY()>b.getLayoutY()){
            a.setLayoutX(a.getLayoutX()+1);
            a.setLayoutY(a.getLayoutY()+1);
        }
    }
    
    public void affichage(BorderPane border){
       vitPistolero=new Slider();vitMechant=new Slider();Label vp=new Label("Vitesse du pistolero");Label vm=new Label("Vitesse du mechant");
       vitPistolero.setOrientation(Orientation.HORIZONTAL);vitPistolero.setMin(0);vitPistolero.setMax(10); vitPistolero.setValue(2);
       vitMechant.setOrientation(Orientation.HORIZONTAL);vitMechant.setMin(0);vitMechant.setMax(10);vitMechant.setValue(2);
       vitPistolero.setFocusTraversable(false);vitMechant.setFocusTraversable(false);
       HBox hbox=new HBox();hbox.getChildren().addAll(vp,vitPistolero,vm,vitMechant);
       border.setTop(hbox);
       
       Label n1=new Label("Nombre de démon à éliminer");Label n2=new Label("Nombre de démon éliminé");
       Label n3=new Label("Nombre de balles restant");Button b=new Button("Pause");
       TextField dEli=new TextField(0+""); TextField dRes=new TextField(mechant.size()+"");
       TextField bRes=new TextField();
       dEli.setFocusTraversable(false);dRes.setFocusTraversable(false);bRes.setFocusTraversable(false);b.setFocusTraversable(false);
       ToolBar toolbar=new ToolBar();
       toolbar.getItems().addAll(n1,dEli,n2,dRes,n3,bRes,b);
       border.setBottom(toolbar);
       
       for(int i=0;i<mechant.size();i++){
           mechant.get(i).vitesseProperty().bind(vitMechant.valueProperty());
       }
       pistolero.vitesseProperty().bind(vitPistolero.valueProperty());
       bRes.textProperty().bindBidirectional(pistolero.balleDisponibleProperty(),new NumberStringConverter());
       dRes.textProperty().bindBidirectional(nbMonstreProperty(),new NumberStringConverter());
       /*b.setOnAction(actionEvent -> {
            if(animation){
                animation=false;
            }else{
                animation=true;
            }
       });
       BooleanProperty anim=new SimpleBooleanProperty(animation);
       b.textProperty().bind(Bindings.when(anim).then("pause").otherwise("jouer"));*/
                
       
    }
}                

        
       
        
    
    
   
  
    
  