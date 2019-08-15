import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class MystSafeguard{
	int indexMin = 0;
	int indexMax = 0;
	int indexOfChoices = 0;
	int lastNumOfRange = 0;
	
	ArrayList<timetable> timetables = new ArrayList<timetable>();
	int result = indexMax-indexMin;
	
	public String getFireGuard(){
		lastNumOfRange = indexMax-indexMin;
		result = lastNumOfRange;
		for(int k=0;k<indexOfChoices;k++){		
			int startTimeT = timetables.get(k).getStartTime();
			int endTimeT = timetables.get(k).getEndTime();
			
			for(int k2=0;k2<indexOfChoices;k2++){
				if(k!=k2){
					int startTimeN = timetables.get(k2).getStartTime();
					int endTimeN = timetables.get(k2).getEndTime();
					if(startTimeT>startTimeN && startTimeT<endTimeN ){
						for(int j = startTimeT;j<=endTimeT;j++){
							if(j>startTimeT && j<=endTimeN){
								startTimeT = j;
							}
						}
					}else if(endTimeT>startTimeN && endTimeT<endTimeN){
						for(int j = endTimeT;j>=startTimeT;j--){
							if(j>=startTimeN){
								endTimeT = j;
							}
						}
					}
					
				}
			}
			if((endTimeT-startTimeT)<result){
				result = endTimeT-startTimeT;
			}
		}
		timetables.clear();
		
		return String.valueOf(lastNumOfRange-result);
	}
	
	public void getMaxIndex(){
		for(timetable t:timetables){
			if(t.getEndTime()>indexMax){
				indexMax = t.getEndTime();
			}
		}
	}
	
	public void getMinIndex(){
		indexMin = indexMax;
		for(timetable t:timetables){
			if(t.getStartTime()<indexMin){
				indexMin = t.getStartTime();
			}
		}
	}
	
	public void readFromFile(String fileName){
		// setup indexOfChoices
		
		BufferedReader reader;
		try{
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			int readerIndex = 1;
			while(line!=null){
				
				if(readerIndex==1){
					this.indexOfChoices = Integer.valueOf(line.trim());
				}else{
					String[] times = line.split("\\s+");
					timetable t = new timetable();
					t.setStartTime(Integer.valueOf(times[0].trim()));
					t.setEndTime(Integer.valueOf(times[1].trim()));
					timetables.add(t);
				}
				line = reader.readLine();
				readerIndex++;
			}
			reader.close();
			this.getMaxIndex();
			this.getMinIndex();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void writeToFile(String fileName, String content){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(content);
			writer.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void execute(){
		String[] inputs = {"inputs/1.in", "inputs/2.in", "inputs/3.in", "inputs/4.in", "inputs/5.in", "inputs/6.in", "inputs/7.in", "inputs/8.in", "inputs/9.in", "inputs/10.in"};
		String[] outputs = {"outputs/1.out", "outputs/2.out", "outputs/3.out", "outputs/4.out", "outputs/5.out", "outputs/6.out", "outputs/7.out", "outputs/8.out", "outputs/9.out", "outputs/10.out"};
		
		for(int i=0;i<10;i++){
			System.out.println("start task no."+i);
			this.readFromFile(inputs[i]);
			writeToFile(outputs[i], this.getFireGuard());
			System.gc();
		}
		
		
	}
	
	public static void main(String[] args){
		MystSafeguard ms = new MystSafeguard();
		ms.execute();
	}
}
