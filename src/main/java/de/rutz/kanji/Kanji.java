package de.rutz.kanji;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Kanji {
	
	@Id
	@GeneratedValue
	int id;
	String character;
	//@XmlElementWrapper(name = "meanings")
	@OneToMany(mappedBy="kanji")
	List<String> meaning;
	//@XmlElementWrapper(name = "onyomis")
	@OneToMany(mappedBy="kanji")
	List<String> onyomi;
	//@XmlElementWrapper(name = "kunyomis")
	@OneToMany(mappedBy="kanji")
	List<String> kunyomi;
	int jlptLevel;
	String strokeOrderSVG;

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public int getJlptLevel() {
		return jlptLevel;
	}

	public void setJlptLevel(int jlptLevel) {
		this.jlptLevel = jlptLevel;
	}

	public String getCharacter() {
		return character;
	}

	public void setCharacter(String character) {
		this.character = character;
	}

	public List<String> getMeaning() {
		return meaning;
	}

	public void setMeaning(List<String> meaning) {
		this.meaning = meaning;
	}

	public List<String> getOnyomi() {
		return onyomi;
	}

	public void setOnyomi(List<String> onyomi) {
		this.onyomi = onyomi;
	}

	public List<String> getKunyomi() {
		return kunyomi;
	}

	public void setKunyomi(List<String> kunyomi) {
		this.kunyomi = kunyomi;
	}
	
	public String getStrokeOrderSVG() {
		return strokeOrderSVG;
	}
	
	public void setStrokeOrderSVG(String strokeOrderSVG) {
		this.strokeOrderSVG = strokeOrderSVG;
	}

}
