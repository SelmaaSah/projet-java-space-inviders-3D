package cubes;

import com.jogamp.opengl.GL2;

public class Square extends GraphicalObject {

    // constructeur 
    public Square(float pX, float pY, float pZ, float scale) {
        super(pX, pY, pZ, 0, 0, 0, 1.0f, 1.0f, 1.0f, scale);
    }

    // constructeur complet (pour les faces colorées du cube)
    public Square(float pX, float pY, float pZ, 
                  float angX, float angY, float angZ, 
                  float r, float g, float b, 
                  float scale) {
        super(pX, pY, pZ, angX, angY, angZ, r, g, b, scale);
    }

    @Override
    public void displayNormalized(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-1f, 1f, 0f);
        gl.glVertex3f(1f, 1f, 0f);
        gl.glVertex3f(1f, -1f, 0f);
        gl.glVertex3f(-1f, -1f, 0f);
        gl.glEnd();
    }
}