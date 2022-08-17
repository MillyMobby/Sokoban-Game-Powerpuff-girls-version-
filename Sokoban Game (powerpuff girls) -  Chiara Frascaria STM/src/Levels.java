
public class Levels {
	private String level1;
	private String level2;
	
	public Levels() {
								level1 = 	    "###############\n"
											  + "#--------@----#\n" 	// # = walls 
											  + "#--$---#---$--#\n"		// $ = crates
											  + "#------#------#\n" 	// @ = pusher										  	
											  + "#--#########--#\n"		// . = storage
											  + "#--.---#------#\n" 	//'-'= empty	
											  + "#------#------#\n"
											  + "#--$---#---$--#\n"
											  + "#--.-.-.------#\n"
											  + "###############\n";
								
								level2	    =   "     #####     \n"
											  + "######..-######\n"
											  + "#---.......---#\n"
											  + "#---###$###---#\n"
											  + "#---#-----#---#\n"
											  + "#---#$-$-$#---#\n"
											  + "#---$--@--$---#\n"
											  + "#---#$-$-$#---#\n"
											  + "#---#-----#---#\n"
											  + "###############\n";
	}
	
	public String getLevel1() {
		return level1;
	}
	

	public String getLevel2() {
		return level2;
	}
											 

}
