package cubes;

import com.jogamp.opengl.GL2;
import java.util.ArrayList;

public class ParticleSystem {
    private ArrayList<Particle> stars = new ArrayList<>();

    public ParticleSystem(int count) {
        for (int i = 0; i < count; i++) {
            stars.add(new Particle());

            
            stars.get(i).y = (new java.util.Random().nextFloat() * 10) - 5; 
        }
    }

    public void update() {
        for (Particle p : stars) {
            p.update();
        }
    }

    public void display(GL2 gl) {
        for (Particle p : stars) {
            p.display(gl);
        }
    }
}