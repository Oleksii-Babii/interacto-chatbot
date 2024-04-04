package griffith;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ChatbotProTest {

	private String input;
	private String expectedOutput;
	
	public ChatbotProTest(String input, String expectedOutput) {
		super();
		this.input = input;
		this.expectedOutput = expectedOutput;
	}
	@Parameters
	public static Collection<String[]> testConditions(){
		String expectedOutput[][] = {{"",""},{"", ""},{"", ""},{"", ""}}; 
							  //actual, expected
		return Arrays.asList(expectedOutput);
	}
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
