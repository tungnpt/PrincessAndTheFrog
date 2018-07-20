package game.player;

import base.FrameCounter;
import base.GameObject;
import base.GameObjectManager;
import base.Vector2D;
import game.enemy.BulletFlyingEnemy;
import game.island.FloatingIsland;
import input.KeyboardInput;
import physic.BoxCollider;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {
    public List<BulletPlayer> bulletPlayers = new ArrayList<>();

    public BufferedImage image;
    public boolean onIsland = false;
    public boolean isFalling = true;

    public int distance= 0;
    public int point = 0;

    public BoxCollider boxCollider;
    public FrameCounter frameCounter;

    float gravity =0.5f;
    //float friction = 0.99f;

    public Player() {
        super();
    }

    public Player(Vector2D position, BufferedImage image) {
        this.position.set(position);
        this.image = image;
        this.velocity = new Vector2D(5f, 5f);
        this.width = 81;
        this.height =88;
        this.boxCollider = new BoxCollider(this.width, this.height);
        this.frameCounter = new FrameCounter(5);
    }

    public void run() {
        if (KeyboardInput.instance.isSpace) {
            this.distance+=25;
            this.point = this.distance/400;
            System.out.println(this.point);
            if(frameCounter.run()){
                BulletPlayer bulletPlayer = new BulletPlayer();
                bulletPlayer.position.set(this.position.x + this.width/2, this.position.y+ this.height);
                //bullet.velocity.set(new base.Vector2D(0, 1f));
                bulletPlayers.add(bulletPlayer);
                frameCounter.reset();
            }
            for (int i=0; i<this.bulletPlayers.size(); i++){
                for (int j=0; j<GameObjectManager.instance.list.size(); j++){
                    if(this.bulletPlayers.get(i).boxCollider.checkCollision(((FloatingIsland) GameObjectManager.instance.list.get(j)).boxCollider)){
                        this.bulletPlayers.get(i).isAlive = false;
                    }
                }
            }



        }
        this.bulletPlayers.forEach(bulletFlyingEnemy -> bulletFlyingEnemy.run());

        for (int i=0; i<GameObjectManager.instance.list.size(); i++){
//            if(physic.OnIsland.checkOnIsland(this,(game.island.FloatingIsland)base.GameObjectManager.instance.list.get(i))){
////////                this.isFalling = false;
////            }
                if(this.boxCollider.checkCollision(((FloatingIsland) GameObjectManager.instance.list.get(i)).boxCollider)){
                    this.isFalling = false;
                }
        }
        if (!isFalling){
                this.velocity.y = -this.velocity.y;
                isFalling = true;
        }
        else{
            this.velocity.y +=  gravity;
        }
        this.position.y += this.velocity.y;
        this.boxCollider.position.set(this.position);
    }

    public void render(Graphics graphics) {
        graphics.drawImage(this.image, (int) this.position.x, (int) this.position.y, (int)this.width, (int)this.height, null);
        this.bulletPlayers.forEach(bulletFlyingEnemy -> bulletFlyingEnemy.render(graphics));

    }
}
