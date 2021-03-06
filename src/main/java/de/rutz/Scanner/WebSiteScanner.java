package de.rutz.Scanner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import de.rutz.kanji.JapaneseVocab;
import de.rutz.kanji.Kanji;

public class WebSiteScanner {
	private String website;
	private JapaneseVocab japaneseVocab;
	private List<Kanji> kanjiList = new ArrayList<Kanji>();
	private WebDriver driver=null;
//	private String savedURL=null;
//	private String session_id=null;

	public WebSiteScanner(Map<Integer, Integer> map) throws ParserConfigurationException, IOException, JAXBException {
		japaneseVocab = new JapaneseVocab();
		for (Entry<Integer, Integer> e : map.entrySet()) {
			for (int i = 1; i <= e.getValue(); i++) {
				buildDOM(e.getKey(), i);
			}
		}
		japaneseVocab.setKanji(kanjiList);
//		writeXML(japaneseVocab);
		writetoDB(kanjiList);
		driver.close();
	}

	private void buildDOM(int level, int page) throws IOException, ParserConfigurationException, JAXBException {
		website = "https://jisho.org/search/%23jlpt-n" + level + "%20%23kanji?page=";
		Document doc = Jsoup.connect(website + page).timeout(10*1000).get();
		Elements divs = doc.getElementsByClass("kanji_light_content");
		for (Element div : divs) {
			System.out.println("WAIT..");
			Elements kanjis = div.getElementsByClass("character");
			Kanji newKanji = new Kanji();
			newKanji.setJlptLevel(level);
			for (Element e : kanjis) {
				Element kanji = e.child(0);
				newKanji.setCharacter(kanji.ownText());
				String url ="https://kanjivg.tagaini.net/viewer.html?kanji=";
				if(driver==null) {
				System.setProperty("webdriver.edge.driver","D:\\Users\\Nikolai\\Downloads\\MicrosoftWebDriver.exe");
				driver = new EdgeDriver();
				}
				driver.get(url + kanji.ownText());
				Document kanjiDoc =Jsoup.parse(driver.getPageSource());
				Element svg=kanjiDoc.getElementById("kanjiViewer");
				System.out.println(svg);
				newKanji.setStrokeOrderSVG(svg.html());
			}
			Elements meanings = div.getElementsByClass("meanings");
			List<String> meaningList = new ArrayList<String>();
			for (Element e : meanings) {
				Elements meaningElements = e.children();
				for (Element meaning : meaningElements) {
					String m = meaning.ownText();
					if (m.charAt(m.length() - 1) == ',') {
						m = m.substring(0, m.length() - 1);
					}
					meaningList.add(m);
				}

			}

			Elements kuns = div.getElementsByClass("kun");
			List<String> kunList = new ArrayList<String>();
			for (Element e : kuns) {
				Elements kunElements = e.children();
				for (Element kunElement : kunElements) {
					kunElement.getElementsByTag("a").forEach(a -> kunList.add(a.ownText()));

				}

			}

			Elements ons = div.getElementsByClass("on");
			List<String> onList = new ArrayList<String>();
			for (Element e : ons) {
				Elements onElements = e.children();
				for (Element onElement : onElements) {
					onElement.getElementsByTag("a").forEach(a -> onList.add(a.ownText()));

				}

			}
			newKanji.setMeaning(meaningList);
			newKanji.setKunyomi(kunList);
			newKanji.setOnyomi(onList);
			
			kanjiList.add(newKanji);
		}

	}

	private void writeXML(JapaneseVocab japaneseVocab) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(JapaneseVocab.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(japaneseVocab, new File("C:\\Users\\Nikolai\\Desktop\\japaneseVocab.xml"));
	}
	
	private void writetoDB(List<Kanji> kanjiList) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("japanesevocab");
		EntityManager em = factory.createEntityManager();
		EntityTransaction trans=em.getTransaction();
		trans.begin();
		for(Kanji kanji: kanjiList) {
			em.persist(kanji);
		}
		trans.commit();
		em.close();
		factory.close();
	}
}
