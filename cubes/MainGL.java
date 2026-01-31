package cubes;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.GLCapabilities;

public class MainGL extends GLJPanel implements GLEventListener, KeyListener {

    private static final long serialVersionUID = 1L;
    
    public Square player;
    public ParticleSystem starField; 
    public ArrayList<Projectile> missiles = new ArrayList<Projectile>();
    public ArrayList<Alien> aliens = new ArrayList<Alien>();
    
    // liste des explosions
    public ArrayList<Explosion> explosions = new ArrayList<Explosion>();
    
    private float speed = 0.2f; 
    private int score = 0;
    private JFrame parentFrame; 
    
    private float alienSpeed = 0.02f; 
    private float alienDirection = 1.0f; 
    
    private boolean isGameOver = false;

    public MainGL(GLCapabilities caps, JFrame frame) {
        super(caps); 
        this.parentFrame = frame;
        this.addGLEventListener(this);
        this.addKeyListener(this);
        this.setFocusable(true); 
    }

    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        this.player = new Square(0f, -2f, 0f, 0.5f);
        this.player.setColor(0f, 1f, 0f);
        
        this.starField = new ParticleSystem(200); 
        
        spawnAliens();
    }
    
    public void spawnAliens() {
        aliens.clear();
        missiles.clear(); // On nettoie aussi les missiles
        explosions.clear(); // Et les explosions
        
        for (int row = 0; row < 3; row++) { 
            for (int col = 0; col < 6; col++) { 
                float x = -2.5f + (col * 1.0f);
                float y = 2.5f - (row * 0.8f);
                Alien a = new Alien(x, y);
                if (row == 0) a.setColor(1, 0, 0); 
                else if (row == 1) a.setColor(1, 1, 0); 
                else a.setColor(0.2f, 0.2f, 1f); 
                aliens.add(a);
            }
        }
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        if (height == 0) height = 1;
        float aspect = (float) width / height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, aspect, 0.1, 100.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        if (isGameOver) return;

        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0, 0, -10);

        // etoiles
        gl.glDisable(GL2.GL_LIGHTING); 
        starField.update();
        starField.display(gl);
        gl.glEnable(GL2.GL_LIGHTING); 

        if (player != null) player.display(gl);
        
        Iterator<Projectile> itMissile = missiles.iterator();
        while (itMissile.hasNext()) {
            Projectile m = itMissile.next();
            m.update();
            m.display(gl);
            if (m.isDead) itMissile.remove();
        }
        
        updateAliens(); 
        for (Alien a : aliens) {
            a.rotate(1.0f, 1.0f, 1.0f); 
            a.display(gl);
        }
        
        // --- FX : Gestion des explosions ---
        // On utilise le même principe d'Iterator pour les mettre à jour et les supprimer
        Iterator<Explosion> itExp = explosions.iterator();
        while(itExp.hasNext()) {
            Explosion exp = itExp.next();
            exp.update();
            exp.display(gl);
            if(exp.isFinished) {
                itExp.remove();
            }
        }
        
        checkCollisions();
    }
    
    public void finDuJeu(boolean gagne) {
        isGameOver = true;
        SwingUtilities.invokeLater(() -> {
            String message;
            String titre;
            int iconType;
            if (gagne) {
                titre = "BRAVO !";
                message = "Victoire ! Score final  : " + score;
                iconType = JOptionPane.INFORMATION_MESSAGE;
            } else {
                titre = "ÉCHOUÉ...  :'(";
                message = "Game Over. Score final : " + score;
                iconType = JOptionPane.ERROR_MESSAGE;
            }
            JOptionPane.showMessageDialog(parentFrame, message, titre, iconType);
            System.exit(0);
        });
    }

    public void updateAliens() {
        if (isGameOver) return;
        boolean hitEdge = false;

        for (Alien a : aliens) {
            a.translate(alienSpeed * alienDirection, 0, 0);
            
            if (a.getX() > 3.8f || a.getX() < -3.8f) hitEdge = true;
            
            if (a.getY() < -1.5f) {
                finDuJeu(false); 
                return; 
            }
        }

        if (hitEdge) {
            alienDirection = -alienDirection; 
            for (Alien a : aliens) {
                a.translate(0, -0.3f, 0); 
            }
        }
    }
    
    public void checkCollisions() {
        if (isGameOver) return;

        		Iterator<Alien> itAlien = aliens.iterator();
        while (itAlien.hasNext()) {
            Alien a = itAlien.next();
            boolean alienTouche = false;
            
            for (Projectile m : missiles) {
                if (m.isDead) continue;
                float distX = Math.abs(m.getX() - a.getX());
                float distY = Math.abs(m.getY() - a.getY());
                
                if (distX < 0.5f && distY < 0.5f) {
                    m.isDead = true;
                    alienTouche = true;
                    score += 100;
                    updateTitle(); 
                    
                    
                    explosions.add(new Explosion(a.getX(), a.getY(), a.getZ()));
                    
                    break; 
                }
            }
            if (alienTouche) {
                itAlien.remove();
            }
        }
        
        if (aliens.isEmpty()) {
            finDuJeu(true);
        }
    }
    
    public void updateTitle() {
        SwingUtilities.invokeLater(() -> {
        		if (parentFrame != null) parentFrame.setTitle("Space Invaders 3D | Score: " + score);
        });
    	}

    public void dispose(GLAutoDrawable drawable) { }

    @Override
    public void keyPressed(KeyEvent e) {
        if (player == null || isGameOver) return;
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT && player.getX() > -3.5f) player.translate(-speed, 0, 0);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && player.getX() < 3.5f) player.translate(speed, 0, 0);
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            missiles.add(new Projectile(player.getX(), player.getY() + 0.5f));
        }
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyReleased(KeyEvent e) { }

    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        
        final JFrame frame = new JFrame(); 
        MainGL panel = new MainGL(caps, frame); 
        
        panel.setPreferredSize(new Dimension(800, 600));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setTitle("Space Invaders 3D Ultimate");
        frame.pack();
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.requestFocusInWindow();
        
        final FPSAnimator animator = new FPSAnimator(panel, 60, true);
        animator.start();
    }
}