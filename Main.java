import edu.du.dudraw.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Map.Entry;
public class Proj4Driver {

	public static void main(String[] args) {

		File folder = new File("names");
		File[] files = folder.listFiles();
		Arrays.sort(files);
		//HashMap arrays for males and females
		HashMap<String, String>[] males = new HashMap[142];
		HashMap<String, String>[] females = new HashMap[142];

		//array containing the number of males and females for each year
		int[] malesperyear = new int[142];
		int[] femalesperyear = new int[142];

		//initiate HashMap Arrays
		for(int i = 0; i < males.length;i++) {
			males[i] = new HashMap<String, String>();
			females[i] = new HashMap<String, String>();
		}
		
		int year = 1880;
		//loop through files 
		for(File file:files) {
			try {			
				Scanner fileReader = new Scanner(new FileReader(file));
				int index = year-1880;
				int femalecount = 0;
				int malecount =0;
				while(fileReader.hasNext()) {
					String line = fileReader.nextLine();
					//Create array with name, gender, count
					String[] tokens = line.split(",");
					String num = tokens[2];
					String gender = tokens[1];
					if(gender.equals("M")) {
						//put name with count
						males[index].put(tokens[0],num);
						int number = Integer.parseInt(num);
						malecount+=number;
					}
					if(gender.equals("F")) {
						//put name with count
						females[index].put(tokens[0],num);
						int number = Integer.parseInt(num);
						femalecount+=number;
					}
				}
				malesperyear[index] = malecount;
				femalesperyear[index] = femalecount;
				year++;			
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}			
		}
			
		Scanner keyboard = new Scanner(System.in);
		while(true) {
			System.out.println("Enter a name or year");
			String input = keyboard.next();

			//try year query
			try {
				int newyear = Integer.parseInt(input);
				System.out.println("Enter M for male, F for female");
				String gender = keyboard.next();
				System.out.println("How many names would you like to see?");
				int numnames = keyboard.nextInt();
				//ArrayList of top names
				ArrayList<String> topnames = new ArrayList<String>();
				
				if(gender.equals("M")){
					for(int i = 0; i < numnames;i++) {
						//highest name count 
						int highestcount = 0;
						//name containing current highest count
						String currentname = "";
						//Loop through male entries in given year
						for(Entry<String, String> e: males[newyear-1880].entrySet()) {
							if(!(topnames.contains(e.getKey()))){
								int currentcount = Integer.parseInt(e.getValue());
								if(currentcount>highestcount) {
									highestcount = currentcount;
									currentname = e.getKey();
								}							
							}						
						}
						topnames.add(currentname);
					}					
				}
				if(gender.equals("F")) {
					for(int i = 0; i < numnames;i++) {
						//highest name count
						int highestcount = 0;
						//name containing current highest count
						String currentname = "";
						//loop through female entries in given year
						for(Entry<String, String> e: females[newyear-1880].entrySet()) {
							if(!(topnames.contains(e.getKey()))){
								int currentcount = Integer.parseInt(e.getValue());
								if(currentcount>highestcount) {
									highestcount = currentcount;
									currentname = e.getKey();
								}							
							}						
						}
						topnames.add(currentname);
					}						
				}			
				System.out.println("The top names in order in " + input +" were:");
				for(String s:topnames ) {
					System.out.println(s);		
				}
			}
			//do name query if cannot convert the input to an Integer
			catch(NumberFormatException e){
				boolean yesmale = false;
				boolean yesfemale = false;
				//check if male name exists
				for(int i = 0; i< males.length;i++) {
					//try to get the name, store it in maybe 
					String maybe = males[i].get(input);
					if(maybe!=null) {
						yesmale = true;
						break;
					}
				}
					
				//check if female name exists
				for(int i = 0; i< females.length;i++) {
					//try to get the name, store it in maybe
					String maybe = females[i].get(input);
					if(maybe!=null) {
						yesfemale = true;
						break;
					}	
				}
				// if there are both female and male names
				if(yesmale && yesfemale ) {
					System.out.println("Enter F for female, M for male");
					String fm = keyboard.next();
					//if male is chosen
					if(fm.equals("M")){
						DUDraw.clear();
						//year with highest ratio
						int highestyear =0;
						// highest ratio for name/names that year
						double highestratio = 0;
						//atotal is count of name at each year
						String atotal = "";
						//first appearance
						int firstyear =0;
						for(int i =141; i >=0;i--) {
							atotal = males[i].get(input);
							if(atotal==null) {
							}
							else {
								firstyear = i+1880;
								//if not null then check the ratio of name/names that year
								int btotal = Integer.parseInt(atotal);
								double currentratio = 0;
								currentratio = (double)btotal/malesperyear[i];
								if(currentratio>highestratio) {
									highestratio = currentratio;
									highestyear = i+1880;		
								}						
							}		
						}		
						DUDraw.setCanvasSize(500,500);
						DUDraw.setXscale(0,142);
						DUDraw.setYscale(0,highestratio*1.2);
						for(int i = 0; i < males.length;i++) {
							atotal = males[i].get(input);
							if(atotal==null) {
							}
							else {
								int btotal = Integer.parseInt(atotal);
								double currentratio = (double)btotal/malesperyear[i];
								DUDraw.line(i, 0, i, currentratio);				
							}
						}
						double converted = highestratio*100;
						String rounded = String.format("%.2f",converted);
						DUDraw.text(70, highestratio*1.05,input + ", first year "+ firstyear);
						DUDraw.text(70, highestratio*.95,"Maximum frequency " + rounded+"% in " + highestyear);
						DUDraw.show();
				
					}
					// if female is chosen 
					if(fm.equals("F")){
						DUDraw.clear();
						int highestyear=0;
						double highestratio = 0;
						String atotal = "";
						int firstyear =0;
						for(int i =141; i >=0;i--) {
							atotal = females[i].get(input);
							if(atotal==null) {
							}
							else {
								firstyear = i+1880;
								int btotal = Integer.parseInt(atotal);
								double currentratio = 0;
								currentratio = (double)btotal/femalesperyear[i];
								if(currentratio>highestratio) {
									highestratio = currentratio;
									highestyear = i+1880;		
								}						
							}		
						}		
						DUDraw.setCanvasSize(500,500);
						DUDraw.setXscale(0,142);
						DUDraw.setYscale(0,highestratio*1.2);
						for(int i = 0; i < females.length;i++) {
							atotal = females[i].get(input);
							if(atotal==null) {
							}
							else {
								int btotal = Integer.parseInt(atotal);
								double currentratio = (double)btotal/femalesperyear[i];
								DUDraw.line(i, 0, i, currentratio);				
							}
						}
						double converted = highestratio*100;
						String rounded = String.format("%.2f",converted);
						DUDraw.text(70, highestratio*1.05,input + ", first year "+ firstyear);
						DUDraw.text(70, highestratio*.95,"Maximum frequency " + rounded+"% in " + highestyear);
						DUDraw.show();
					}
				}
				// if only male names exist
				else if(yesmale) {
					DUDraw.clear();
					int highestyear=0;
					double highestratio = 0;
					String atotal = "";
					int firstyear =0;
					for(int i =141; i >=0;i--) {
						atotal = males[i].get(input);
						if(atotal==null) {

						}
						else {
							firstyear = i+1880;
							int btotal = Integer.parseInt(atotal);
							double currentratio = 0;
							currentratio = (double)btotal/malesperyear[i];
							if(currentratio>highestratio) {
								highestratio = currentratio;
								highestyear = i+1880;		
							}						
						}		
					}		
					DUDraw.setCanvasSize(500,500);
					DUDraw.setXscale(0,142);
					DUDraw.setYscale(0,highestratio*1.2);
					for(int i = 0; i < males.length;i++) {
						atotal = males[i].get(input);
						if(atotal==null) {
						}
						else {
							int btotal = Integer.parseInt(atotal);
							double currentratio = (double)btotal/malesperyear[i];
							DUDraw.line(i, 0, i, currentratio);				
						}
					}
					double converted = highestratio*100;
					String rounded = String.format("%.2f",converted);
					DUDraw.text(70, highestratio*1.05,input + ", first year "+ firstyear);
					DUDraw.text(70, highestratio*.95,"Maximum frequency " + rounded+"% in " + highestyear);
					DUDraw.show();
				}
				// if only female names exist
				else if(yesfemale) {
					DUDraw.clear();
					int highestyear=0;
					double highestratio = 0;
					String atotal = "";
					int firstyear =0;
					for(int i =141; i >=0;i--) {
						atotal = females[i].get(input);
						if(atotal==null) {
						}
						else {
							firstyear = i+1880;
							int btotal = Integer.parseInt(atotal);
							double currentratio = 0;
							currentratio = (double)btotal/femalesperyear[i];
							if(currentratio>highestratio) {
								highestratio = currentratio;
								highestyear = i+1880;		
							}						
						}		
					}	
					DUDraw.setCanvasSize(500,500);
					DUDraw.setXscale(0,142);
					DUDraw.setYscale(0,highestratio*1.2);
					for(int i = 0; i < females.length;i++) {
						atotal = females[i].get(input);
						if(atotal==null) {
						}
						else {
							int btotal = Integer.parseInt(atotal);
							double currentratio = (double)btotal/femalesperyear[i];
							DUDraw.line(i, 0, i, currentratio);				
						}
					}
					double converted = highestratio*100;
					String rounded = String.format("%.2f",converted);
					DUDraw.text(70, highestratio*1.05,input + ", first year "+ firstyear);
					DUDraw.text(70, highestratio*.95,"Maximum frequency " + rounded+"% in " + highestyear);
					DUDraw.show();
				}
			}
		}
	}
}
