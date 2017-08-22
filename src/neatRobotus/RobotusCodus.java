package neatRobotus;

import robocode.AdvancedRobot;
import robocode.ScannedRobotEvent;

import java.awt.*;


public class RobotusCodus extends AdvancedRobot {

	
		public void run() {
			// Set colors
			setBodyColor(new Color(128, 128, 50));
			setGunColor(new Color(50, 50, 20));
			setRadarColor(new Color(200, 200, 70));
			setScanColor(Color.white);
			setBulletColor(Color.blue);
			Genoma genoma = Populacao.ultimoGenoma;
			// Loop forever
			while (true) {
					double[] r = genoma.ativar(this.estadoNormal());
					this.agir(r);
			}
		}
		public void onScannedRobot(ScannedRobotEvent roboto){
			
		}
		
		protected double[] estadoNormal(){
			double v[] = {
					getBattleFieldHeight(),
					getBattleFieldWidth(),
					getEnergy(),
					getGunCoolingRate(),
					getGunHeading(),
					getGunHeat(),
					getHeading(),
					getHeight(),
					getOthers(),
					getRadarHeading(),
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
			return 	v; 
		}
		
		protected void agir( double[] r){
			ahead( r[0]);
			back( r[1]);
			if( r[2] == 1)
				doNothing();
			fire( r[3]);
			turnLeft( r[4]);
			turnRight( r[5]);
			turnGunLeft( r[6]);
			turnGunRight( r[7]);
			turnRadarLeft( r[8]);
			turnRadarRight( r[9]);
		}

}