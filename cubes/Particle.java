package cubes;

import com.jogamp.opengl.GL2;
import java.util.Random;

public class Particle {
    public float x, y, z;
    public float speed;
    
    public Particle() {
        respawn();
    }

    public void respawn() {
        Random rand = new Random();
        x = (rand.nextFloat() * 10) - 5;
        y = 5.0f; 
        z = (rand.nextFloat() * -5) - 5; 
        speed = 0.05f + (rand.nextFloat() * 0.1f); // vitesse random
    }

    public void update() {
        y -= speed; 
        if (y < -5.0f) {
            respawn(); 
        }
    }

    public void display(GL2 gl) {
        gl.glPointSize(2.0f); // Taille du point
        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3f(1f, 1f, 1f); // clr blanc
        gl.glVertex3f(x, y, z);
        gl.glEnd();
    }
}