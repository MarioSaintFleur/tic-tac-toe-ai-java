import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Player extends ticTac{
	
		Seed player;
	
		
		//player construtor
		public Player(Seed player){
			this.player = player;
		}
		
		public static Seed User(){
			Seed[] userPiece = {Seed.Cross, Seed.Nought};
			
			int p = userPiece.length;
			
			Random random = new Random();
			
			int r = random.nextInt(p);
			
			
			return userPiece [r];
		}
}
