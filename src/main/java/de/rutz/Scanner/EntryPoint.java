package de.rutz.Scanner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

public class EntryPoint {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer,Integer> map= new HashMap<Integer,Integer>();
		map.put(5, 4);
//		map.put(4, 9);
//		map.put(3, 19);
//		map.put(2, 19);
//		map.put(1, 62);
		try {
			new WebSiteScanner(map);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
