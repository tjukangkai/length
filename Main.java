/**
 * 
 * @author kangkai
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class Main {
  public static void main(String[] args) {
		File inputFile = new File("src/input.txt");
		File outputFile = new File("src/output.txt");

		BufferedReader reader = null;
		StringBuilder sb = null;
		
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			sb = new StringBuilder();
			String tempString = null;

			// read rules
			Map<String, Double> ruleMap = new HashMap<String, Double>();

			while ((tempString = reader.readLine()) != null
					&& !"".equals(tempString)) {
				try {
					String[] splitStr = tempString.split("=");
					String key = splitStr[0]
							.substring(splitStr[0].indexOf(' ')).trim();
					Double value = Double.parseDouble(splitStr[1].substring(0,
							splitStr[1].length() - 2).trim());
					ruleMap.put(key, value);
				} catch (Exception e) {

				}
			}

			// process conversion and computation
			while ((tempString = reader.readLine()) != null) {
				if (tempString.contains("+") || tempString.contains("-")) {
					double computation = 0;
					String[] splitStr;
					
					if(tempString.contains("+")){
						splitStr = tempString.split("\\+");
					}else{
						splitStr = new String[]{tempString};
					}
					
					for (String string : splitStr) {
						if(string.contains("-")){
							double computationMinus = 0;
							
							String[] splitStr1 = string.split("-");
							for (int i = 0; i < splitStr1.length; i++) {
								if(i == 0){
									computationMinus =  process(ruleMap, splitStr1[0]);
								}else{
									computationMinus -=  process(ruleMap, splitStr1[i]);
								}
							}
							computation += computationMinus;
						}else{
							double computationPlus = process(ruleMap, string);
							computation += computationPlus;
						}
					}
					sb.append(String.format("%.2f", computation) + " m\n");
				} else {
					double result = process(ruleMap, tempString);
					sb.append(String.format("%.2f", result) + " m\n");
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		
		try {
			if(!outputFile.exists()){
				outputFile.createNewFile();
			}else{
				outputFile.delete();
				outputFile.createNewFile();
			}
			
			FileWriter writer = new FileWriter(outputFile, true);
            writer.write("tjukangkai@163.com\n\n");
            writer.write(sb.toString().trim());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private static double process(Map<String, Double> ruleMap, String tempString) {
		Iterator iter = ruleMap.keySet().iterator();
		String[] splitStr = tempString.trim().split(" ");
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Double value = ruleMap.get(key);

			if (splitStr[1].contains(key)
					|| splitStr[1].contains("feet")
					&& "foot".equals(key)) {
				double result = Double.parseDouble(splitStr[0]) * value;
				return result;
			}
		}
		return 0;
	}
}
