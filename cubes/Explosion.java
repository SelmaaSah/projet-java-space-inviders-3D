package cubes;

import java.util.ArrayList;
import java.util.Iterator;
import com.jogamp.opengl.GL2;

public class Explosion {
     private ArrayList<ParticleExplosion> particles = new ArrayList<>();
     public boolean isFinished = false;

     public Explosion(float x, float y, float z) {
         for(int i=0; i<50; i++) {
             particles.add(new ParticleExplosion(x, y, z));
         }
     }

     public void update() {
        Iterator<ParticleExplosion> it = particles.iterator();
        while(it.hasNext()) {
            ParticleExplosion p = it.next();
            if(!p.update()) {
                it.remove();
            }
        }
        if(particles.isEmpty()) {
            isFinished = true;
        }
     }

     public void display(GL2 gl) {

         gl.glPointSize(5.0f); 
         
         
         
         gl.glDisable(GL2.GL_LIGHTING); 
         
         gl.glBegin(GL2.GL_POINTS);
         for(ParticleExplosion p : particles) {
             p.display(gl);
         }
         gl.glEnd();
         
         gl.glEnable(GL2.GL_LIGHTING); 
     }
}