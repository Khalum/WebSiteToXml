package de.rutz.kanji;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="JapaneseVocab")
@XmlAccessorType(XmlAccessType.FIELD)
public class JapaneseVocab {
	@XmlElementWrapper(name = "kanjis")
	List<Kanji> kanji;
	List<Vocabulary> vocabularies;
	
	public List<Kanji> getKanji() {
		return kanji;
	}
	public void setKanji(List<Kanji> kanji) {
		this.kanji = kanji;
	}
	public List<Vocabulary> getVocabularies() {
		return vocabularies;
	}
	public void setVocabularies(List<Vocabulary> vocabularies) {
		this.vocabularies = vocabularies;
	}
	
}
