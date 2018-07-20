package game.player;

import base.GameObject;
import base.Vector2D;
import physic.BoxCollider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BulletPlayer extends GameObject {
    public BufferedImage image = this.loadImage("resources/player.png");
    public BoxCollider boxCollider;
    public boolean isAlive = true;
    public BulletPlayer(){
        this.position= new Vector2D();
        this.velocity= new Vector2D(10,0);
        this.width = 30;
        this.height = 30;
        this.boxCollider = new BoxCollider(this.width, this.height);
    }

    public void run() {
        if (this.isAlive) {
            System.out.println("run");
            this.position.addUp(this.velocity);
            this.boxCollider.position.set(this.position);
        }

    }

    public void render(Graphics graphics) {

        if (this.isAlive) {
            graphics.drawImage(this.image, (int) this.position.x , (int) this.position.y, (int) this.width, (int) this.height, null);
        }
    }
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            return null;
        }
    }
}