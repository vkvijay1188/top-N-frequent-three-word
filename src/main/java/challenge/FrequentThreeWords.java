package challenge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FrequentThreeWords {

	public static final int FREQUENCY_LIMIT = 100;
	public static final String OUTPUT_FILE_EXTN = ".out";
	public static final String DEFAULT_FILE_OUTPUT = "/tmp/frequentThreeWords_";

	public static void main(String[] args) {
		FrequentThreeWords frequentThreeWords = new FrequentThreeWords();

		if (args.length < 1) { // No filename as argument then listen to system inn
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String outFilePath = DEFAULT_FILE_OUTPUT + System.currentTimeMillis();
			try {
				frequentThreeWords.performAllOperations(br, outFilePath);
			} catch (IOException e) {
				System.out.println("Exception during Std:inn Operations" + e.getMessage());
				e.printStackTrace();
			}

		} else { // fileName from argument present

			List<String> fileList = new ArrayList<String>();
			for (int argIndex = 0; argIndex < args.length; argIndex++) {
				String temp = args[argIndex];
				if (temp.startsWith("/") || temp.startsWith("./")) {
					fileList.add(temp);
				} else {
					fileList.add(FrequentThreeWords.class.getResource(temp).getPath());
				}
			}
			
			//Lets iterate thru  the  files 
			for (String filepath : fileList) {
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
					frequentThreeWords.performAllOperations(bufferedReader, filepath);
				} catch (IOException e) {
					System.out.println("Exception during File Operations" + e.getMessage());
					e.printStackTrace();
				}
			}
		}

	}

	// This method will invoke all other logic common for both fileinput and std:inn streams
	public void performAllOperations(BufferedReader bufferedReader, String filepath) throws IOException {
		List<String> wordsBubbleList = parseFileToWordArray(bufferedReader);
		Map<String, Integer> freqMap = wordBubbleToThreeWordList(wordsBubbleList);
		List<String> topFrequencyWordList = countAndSortWordArray(freqMap, FREQUENCY_LIMIT);
		writeResultToFile(filepath, topFrequencyWordList);
	}

	// This method will parse the input file line by line and convert them into list of words 
	// TODO : this may not work for huge files as we are storing everything in memory at this time
	public List<String> parseFileToWordArray(BufferedReader bufferedReader) throws IOException {
		List<String> wordList = new ArrayList<String>();
		String temp = null;
		try {
			while ((temp = bufferedReader.readLine()) != null) {

				temp = temp.toLowerCase().replaceAll("[\\n]", " ").replaceAll("[^a-zA-Z0-9 ]", "").replaceAll("\\s+",
						" ");
				String[] words = temp.split(" ");
				for (String word : words) {
					word = word.replaceAll("\\s+", "");
					if (!word.isEmpty()) {
						wordList.add(word);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Exception during File Processing ");
			throw e;
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				System.out.println("Unable to close the buffer after completed processing the file ");
				e.printStackTrace();
			}
		}

		return wordList;
	}

	//This method accept input as filepath and final list of Top N Frequency three words.
	public void writeResultToFile(String filepath, List<String> topFrequencyWordList) throws IOException {
		FileWriter fw = null;
		filepath = filepath + OUTPUT_FILE_EXTN;
		try {
			fw = new FileWriter(new File(filepath));
			for (String word : topFrequencyWordList) {
				fw.write(word);
				fw.write(System.lineSeparator());
			}
			fw.flush();
			System.out.println("SUCCESS Output file generated :" + filepath);
		} catch (IOException e) {
			System.out.println("Exception during Output File Write Operation : " + filepath);
			throw e;
		} finally {
			try {
				fw.close();
			} catch (IOException e) {
				System.out.println("Unable to close the filewriter after completed writing the file :  " + filepath);
				e.printStackTrace();
			}
		}
		
	}

	//This method accept input as filepath and final list of Top N Frequency three words.
	public Map<String, Integer> wordBubbleToThreeWordList(List<String> wordsBubbleList) {
		Map<String, Integer> freqMap = new HashMap<String, Integer>();
		for (int i = 1; i < wordsBubbleList.size() - 1; i++) {
			String groupOfThreeWords = wordsBubbleList.get(i - 1) + " " + wordsBubbleList.get(i) + " "
					+ wordsBubbleList.get(i + 1);
			freqMap.put(groupOfThreeWords, freqMap.getOrDefault(groupOfThreeWords, 0) + 1);
		}
		return freqMap;
	}

	public List<String> countAndSortWordArray(Map<String, Integer> freqMap, int topNumber) {

		return freqMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.limit(topNumber).map(x -> x.getValue() + " - " + x.getKey()).collect(Collectors.toList());

	}

}