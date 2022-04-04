package entities;

public interface Entity {
	
	void takeDamage(int damage);
	
	void heal(double percentage);
	
	void addStat(String name, double value);
	
	void boostStat(String stat, Double factor);
	 
	void boostStat(String stat, int factor);
}
