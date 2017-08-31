package neatRobotus;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class RobotusCodus extends AdvancedRobot{

	Genoma genoma;
	
		public void run() {
			// Set colors
			setBodyColor(new Color(128, 128, 50));
			setGunColor(new Color(50, 50, 20));
			setRadarColor(new Color(200, 200, 70));
			setScanColor(Color.white);
			setBulletColor(Color.blue);
		     try {
		         FileInputStream fileIn = new FileInputStream("/home/joao/workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
		         ObjectInputStream in = new ObjectInputStream(fileIn);
		         genoma = (Genoma) in.readObject();
		         in.close();
		         fileIn.close();
		      }catch(IOException i) {
		         i.printStackTrace();
		         return;
		      }catch(ClassNotFoundException c) {
		         System.out.println("Genoma não encontrado");
		         c.printStackTrace();
		         return;
		      }
			// Loop forever
			while (true) {
					double[] r = {
							getBattleFieldHeight(),
							getBattleFieldWidth(),
							getEnergy(),
							getGunCoolingRate(),
							getGunHeading(),
							getGunHeat(),
							getHeading(),
							getHeight(),
							getVelocity(),
							getWidth(),
							getX(),
						 	getY(),
						 	0,
						 	0,
						 	0,
						 	0,
						 	0
						};
					this.agir(r);
			}
		}
		public void onScannedRobot(ScannedRobotEvent roboto){
			
			double v[] = {
					getBattleFieldHeight(),
					getBattleFieldWidth(),
					getEnergy(),
					getGunCoolingRate(),
					getGunHeading(),
					getGunHeat(),
					getHeading(),
					getHeight(),
					getVelocity(),
					getWidth(),
					getX(),
				 	getY(),
				 	roboto.getBearing(),
				 	roboto.getDistance(),
				 	roboto.getEnergy(),
				 	roboto.getHeading(),
				 	roboto.getVelocity()
				};
			this.agir(genoma.ativar(v));
		}
		
		
		protected void agir( double[] r){
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
		}

}