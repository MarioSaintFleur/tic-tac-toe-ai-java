import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Computer extends Player{
	
		Seed playerName;
		
		public Computer(Seed playerName) {
			super(playerName);
			
		}
			
		public static Seed Com() {
		
			Seed comPiece;
			
			if(User() == Seed.Cross){
				comPiece = Seed.Nought;
			}else {
				comPiece = Seed.Cross;
			}
			
			return comPiece;
		}
}
