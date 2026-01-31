package cubes;

import java.util.ArrayList;
import com.jogamp.opengl.GL2;

public class Cube extends GraphicalObject {

    private ArrayList<Square> faces;

    public Cube(float posX, float posY, float posZ, float scale) {
        // On initialise le Cube (gris par défaut)
        super(posX, posY, posZ, 0, 0, 0, 0.5f, 0.5f, 0.5f, scale);
        
        faces = new ArrayList<Square>();
        
        // face avant 
        faces.add(new Square(0, 0, 1, 0, 0, 0, 0.2f, 0.2f, 1.0f, 1));
        // face arrière
        faces.add(new Square(0, 0, -1, 0, 0, 0, 0.2f, 0.2f, 0.8f, 1));
        // droite
        faces.add(new Square(1, 0, 0, 0, 90, 0, 0.2f, 0.2f, 0.6f, 1));
        // gauche
        faces.add(new Square(-1, 0, 0, 0, -90, 0, 0.2f, 0.2f, 0.6f, 1));
        // en haut
        faces.add(new Square(0, 1, 0, 90, 0, 0, 0.2f, 0.2f, 0.4f, 1));
        // bas
        faces.add(new Square(0, -1, 0, 90, 0, 0, 0.2f, 0.2f, 0.4f, 1));
    }

    @Override
    public void displayNormalized(GL2 gl) {
        for (Square face : faces) {
            face.display(gl);
        }
    }
}