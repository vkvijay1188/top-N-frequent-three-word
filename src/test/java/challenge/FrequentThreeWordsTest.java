package challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class FrequentThreeWordsTest {
	FrequentThreeWords frequentThreeWords = new FrequentThreeWords();
	
	@Test
	public void test_ParseFileToWordArrayTest() throws IOException {
		
		String resourceName = "ilovesandwich.txt";
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(resourceName).getFile());
		String absolutePath = file.getAbsolutePath();
		BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutePath));
		
		List<String> compareList = new ArrayList<String>();
		compareList.add("i");
		compareList.add("love");
		compareList.add("sandwich");
		compareList.add("i");
		compareList.add("love");
		compareList.add("sandwich");
		compareList.add("i");
		compareList.add("love");
		compareList.add("sandwich");
		
		List<String> result = frequentThreeWords.parseFileToWordArray(bufferedReader);
		
		assertNotNull("parseFileToWordArray should not return null object",result);
		assertEquals(compareList,result);
	}
	
	
	@Test
	public void test_WordBubbleToThreeWordList() {
		
		Map<String, Integer> compareMap = new HashMap<String, Integer>();
		compareMap.put("i love sandwich", 2);
		compareMap.put("love sandwich i", 1);
		compareMap.put("sandwich i love", 1);
		List<String> inputList = new ArrayList<String>();
		inputList.add("i");
		inputList.add("love");
		inputList.add("sandwich");
		inputList.add("i");
		inputList.add("love");
		inputList.add("sandwich");
		Map<String, Integer> result = frequentThreeWords.wordBubbleToThreeWordList(inputList);
		assertNotNull("wordBubbleToThreeWord should not return null object",result);
		assertEquals(compareMap,result);
		
	}
	
	
	@Test
	public void test_CountAndSortWordArrayTest() {
		
		Map<String, Integer> inputMap = new HashMap<String, Integer>();
		inputMap.put("i love sandwich", 2);
		inputMap.put("of the same", 1);
		inputMap.put("welcome to home", 5);
		inputMap.put("Hello world program", 3);
		
		List<String> compareList = new ArrayList<String>();
		compareList.add("5 - welcome to home");
		compareList.add("3 - Hello world program");
		compareList.add("2 - i love sandwich");
		
		List<String>  result = frequentThreeWords.countAndSortWordArray(inputMap,3);
		assertNotNull("countAndSortWordArray should not return null object",result);
		assertEquals(compareList,result);
	}
	
}
