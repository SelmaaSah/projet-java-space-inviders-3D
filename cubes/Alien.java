package cubes;

public class Alien extends Cube {

    public boolean isDead = false;

    public Alien(float posX, float posY) {
        super(posX, posY, 0, 0.4f);
    }
    
    public void update() {
         
         // this.translate(0, -0.002f, 0); 
    }
}