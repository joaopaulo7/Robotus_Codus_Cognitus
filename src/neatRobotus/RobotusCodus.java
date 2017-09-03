package neatRobotus;

import robocode.AdvancedRobot;
import robocode.BulletHitEvent;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class RobotusCodus extends AdvancedRobot{

	Genoma genoma;
	int fit = 0;
		public void run() {
			// Set colors
			setBodyColor(Color.black);
			setGunColor(new Color(50, 50, 20));
			setRadarColor(Color.red);
			setScanColor(Color.white);
			setBulletColor(Color.blue);
		   // setAdjustGunForRobotTurn(true);
		     try {
		         FileInputStream fileIn = new FileInputStream("/home/joao/eclipse-workspace/Robotus_Codus_Cognitus/src/neatRobotus/ultimoGenoma.ser");
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
				System.out.println("kill me");
				while(true) {
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
								 	0,
								 	0,
								 	0,
								 	0,
								 	0,
								 	0,
								 	0,
								 	0,
								 	0,
								 	0,
								 	0,
								 	0,
								 	0
								};
							double r[] = genoma.ativar(v);
							ahead( r[0]);
							back( r[1]);
							if( r[2] > 0)
								fire( r[2]);
							turnLeft( r[3]);
							turnRight( r[4]);
							turnGunLeft( r[5]);
							turnGunRight( r[6]);
							if(r[7] == 1)
								doNothing();
							if(r[8] == 1)
								stop();
							if(r[9] == 1)
								resume();

			}
		}
		
		public void onBulletHit( BulletHitEvent e){
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
				 	1,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
			if(r[7] == 1)
				doNothing();
			if(r[8] == 1)
				stop();
			if(r[9] == 1)
				resume();
		}
		
		public void onHitByBullet( HitByBulletEvent e){
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
				 	0,
				 	1,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
			if(r[7] == 1)
				doNothing();
			if(r[8] == 1)
				stop();
			if(r[9] == 1)
				resume();
	}
		public void onHitRobot( HitRobotEvent e){
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
				 	0,
				 	0,
				 	1,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
			if(r[7] == 1)
				doNothing();
			if(r[8] == 1)
				stop();
			if(r[9] == 1)
				resume();
	}
		public void onHitWall( HitWallEvent e){
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
				 	0,
				 	0,
				 	0,
				 	1,
				 	0,
				 	0,
				 	0,
				 	0,
				 	0
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
			if(r[7] == 1)
				doNothing();
			if(r[8] == 1)
				stop();
			if(r[9] == 1)
				resume();
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
				 	0,
				 	0,
				 	0,
				 	0,
				 	roboto.getBearing(),
				 	roboto.getDistance(),
				 	roboto.getHeading(),
				 	roboto.getVelocity()
				};
			double r[] = genoma.ativar(v);
			ahead( r[0]);
			back( r[1]);
			if( r[2] > 0)
				fire( r[2]);
			turnLeft( r[3]);
			turnRight( r[4]);
			turnGunLeft( r[5]);
			turnGunRight( r[6]);
			if(r[7] == 1)
				doNothing();
			if(r[8] == 1)
				stop();
			if(r[9] == 1)
				resume();
			
			if( getEnergy() == 0) {
				this.fit = -100;
			}
		}
}