import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class Snake {
    public Rectangle[] body = new Rectangle[100];

    public Rectangle bg;
    public double bodyWidth, bodyHeight;

    public int size;
    public int tail = 0;
    public int head = 0;

    public Directions direction = Directions.RIGHT;

    public double ogWaitBetweenUpdate = 0.1;
    public double waitTimeLeft = ogWaitBetweenUpdate;

    public Snake (int size, double startX, double startY, double bodyWidth, double bodyHeight, Rectangle bg){
        this.size = size;
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;
        this.bg = bg;


        for (int i = 0; i <= size; i++){
            Rectangle bodyPiece = new Rectangle(startX + i * bodyWidth, startY, bodyWidth, bodyHeight);
            body[i] = bodyPiece;
            head++;
        }

        head--;

    }

    public void changeDirection(Directions newDirection){
        //direction = newDirection;
        if(newDirection == Directions.RIGHT && direction != Directions.LEFT){
            direction = newDirection;
        } else if (newDirection == Directions.LEFT && direction != Directions.RIGHT){
            direction = newDirection;
        }
        else if (newDirection == Directions.UP && direction != Directions.DOWN){
            direction = newDirection;
        }else if (newDirection == Directions.DOWN && direction != Directions.UP){
            direction = newDirection;
        }
    }

    public void update(double dt) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if ( waitTimeLeft > 0){
            waitTimeLeft -= dt;
            return;
        }

        if(selfIntersecting()){

            if(GameScene.score > GameScene.highScore){
                ScoreWriter.writeNewHighscore(GameScene.score);
                Window.getWindow().setTitle("NEW HIGHSCORE: " + GameScene.score + " POINTS!");
            }

            GameScene.score = 0;
            GameScene.getGameMusic().stop();
            GameScene.setSnakeSize(2);
            Window.getWindow().changeState(2);
        }

        waitTimeLeft = ogWaitBetweenUpdate;
        double newX = 0;
        double newY = 0;

        if(direction == Directions.RIGHT){
            newX = body[head].x + bodyWidth;
            newY = body[head].y;
        } else if (direction == Directions.LEFT){
            newX = body[head].x - bodyWidth;
            newY = body[head].y;
        } else if (direction == Directions.UP)
        {
            newX = body[head].x;
            newY = body[head].y - bodyHeight;
        }else if (direction == Directions.DOWN)
        {
            newX = body[head].x;
            newY = body[head].y + bodyHeight;
        }

        //Picking up Tail, moving it to the heads position
        body[(head + 1) % body.length] = body[tail];
        body[tail] = null;
        head = (head +1) % body.length;
        tail = (tail+1) % body.length;


        body[head].x = newX;
        body[head].y = newY;


    }

    public boolean selfIntersecting(){
      Rectangle rectHead = body[head];
      return intersectingWithRect(rectHead) || intersectingWithBorders(rectHead);
    }

    public boolean intersecting (Rectangle r1 , Rectangle r2){
        return (r1.x >= r2.x && r1.x + r1.width <= r2.x + r2.width &&
                r1.y >= r2.y && r1.y + r1.height <= r2.y + r2.height);
    }

    public boolean intersectingWithBorders (Rectangle head){
        return (head.x < bg.x || (head.x + head.width) > bg.x + bg.width || head.y < bg.y ||
                (head.y + head.height) > bg.y + bg.height);
    }

    public void grow(){
        double newX = 0;
        double newY = 0;

        if(direction == Directions.RIGHT){
            newX = body[tail].x - bodyWidth;
            newY = body[tail].y;
        } else if (direction == Directions.LEFT){
            newX = body[tail].x + bodyWidth;
            newY = body[tail].y;
        } else if (direction == Directions.UP)
        {
            newX = body[tail].x;
            newY = body[tail].y + bodyHeight;
        }else if (direction == Directions.DOWN)
        {
            newX = body[tail].x;
            newY = body[tail].y - bodyHeight;
        }

        Rectangle newBodyPiece = new Rectangle(newX, newY, bodyWidth, bodyHeight);

        if(tail == 0){
            body[99] = newBodyPiece;
        } else {
            tail = (tail - 1) % body.length;
            body[tail] = newBodyPiece;
        }


    }

    public boolean intersectingWithRect(Rectangle item){
        for(int i = tail; i !=head; i = (i+1) % body.length){
            if(intersecting(item, body[i])) return true;
        }
        return false;
    }



    public void draw(Graphics2D graphic){
        for(int i = tail; i !=head; i = (i+1) % body.length){
            Rectangle piece = body[i];
            double subWidth = (piece.width - 6.0) / 2.0;
            double subHeight = (piece.height - 6.0) / 2.0;

            //Draws 4 pieces for each of the body pieces
            graphic.setColor(Color.WHITE);
            graphic.fill(new Rectangle2D.Double(piece.x + 2.0, piece.y + 2.0, subWidth, subHeight));
            graphic.fill(new Rectangle2D.Double(piece.x + 4.0 + subWidth, piece.y + 2.0, subWidth, subHeight));
            graphic.fill(new Rectangle2D.Double(piece.x + 2.0, piece.y + 4.0 + subHeight, subWidth, subHeight));
            graphic.fill(new Rectangle2D.Double(piece.x + 4.0 + subWidth, piece.y + 4.0 + subHeight, subWidth, subHeight));
        }
    }

    public void setOgWaitBetweenUpdate(double ogWaitBetweenUpdate) {
        this.ogWaitBetweenUpdate = ogWaitBetweenUpdate;
    }
}
