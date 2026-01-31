package cubes;

import com.jogamp.opengl.GL2;
import java.util.Random;

public class ParticleExplosion {
    float x, y, z;
    float vx, vy, vz; 
    float life = 1.0f;   
    float r, g, b;
    static Random rand = new Random();

    public ParticleExplosion(float startX, float startY, float startZ) {
        x = startX; y = startY; z = startZ;
        
        vx = (rand.nextFloat() - 0.5f) * 0.15f;
        vy = (rand.nextFloat() - 0.5f) * 0.15f;
        vz = (rand.nextFloat() - 0.5f) * 0.15f;
        
        r = 1.0f; g = 0.9f; b = 0.5f;
    }

    public boolean update() {
        x += vx; y += vy; z += vz;
        
        // la vie diminue
        life -= 0.03f; 
        
        g -= 0.04f; if(g < 0) g = 0;
        b -= 0.08f; if(b < 0) b = 0;
        
        return life > 0;
    }

    public void display(GL2 gl) {
        gl.glColor4f(r, g, b, life); 
        gl.glVertex3f(x, y, z);
    }
}