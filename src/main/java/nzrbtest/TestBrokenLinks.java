package nzrbtest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBrokenLinks {

	public static void main(String[] args) throws IOException  
	{
		WebDriver driver;
	
		// Opening url in firefox browser
		 System.setProperty("webdriver.gecko.driver", "/Users/mohankrishnanadimpalli/Desktop/Selenium/geckodriver");
		 driver=new FirefoxDriver();
		 driver.get("https://www.tab.co.nz/racing/");
		 driver.manage().window().maximize();

		 File file = new File("NZRB broken links lists.txt");
		 FileWriter fw = new FileWriter(file);
		 PrintWriter pw = new PrintWriter(fw);
		
// getting all the objects with anchor and image tags and adding them to the list "links"
		 
		List<WebElement> links=driver.findElements(By.tagName("a"));
		links.addAll(driver.findElements(By.tagName("img")));
		List<WebElement> activelinks= new ArrayList<WebElement>();
	
		// filtering  all tags which have which have href value which is "not null" and contains "http" and saving them to activelinks list
		
		for(int i=0;i<links.size();i++)
		   {
			if(links.get(i).getAttribute("href") != null && links.get(i).getAttribute("href").startsWith("http"))
				{
				   activelinks.add(links.get(i));
//				   System.out.println(links.get(i).getAttribute("href")+ " i value is "+i);
				}
			
		}
		
// Validating all links, capturing their response and printing them in text file
		
		HttpURLConnection con;

		for(int j=0;j < activelinks.size();j++)
			{
			if(j != 25 && j != 224 ) {	
			con = (HttpURLConnection)new URL(activelinks.get(j).getAttribute("href")).openConnection();
				String response = con.getResponseMessage();
//				System.out.println(activelinks.get(j).getAttribute("href")+ " URL response is "+response+" j value"+j);
				pw.println(activelinks.get(j).getAttribute("href")+ " URL response is "+response+" j value"+j);
			}
			}
		pw.close();
		driver.close();
		}
	
	}
	