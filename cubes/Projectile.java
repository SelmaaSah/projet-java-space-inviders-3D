package cubes;

import com.jogamp.opengl.GL2;

public class Projectile extends Square {

    private float speed = 0.3f; // vitessse du missile
    public boolean isDead = false;

    public Projectile(float pX, float pY) {
        super(pX, pY, 0, 0.1f);
        
        this.setColor(1f, 1f, 0f);
    }

    public void update() {
        this.translate(0, speed, 0);
        
        // si le missile sort de l'écran par le haut n le tue
        if (this.getY() > 3.5f) {
            isDead = true;
        }
    }
}