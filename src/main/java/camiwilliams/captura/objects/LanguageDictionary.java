package camiwilliams.captura.objects;

import java.util.ArrayList;
import java.util.List;

public class LanguageDictionary {

	private String language;
	private List<Word> words;

	public LanguageDictionary(String language) {
		this.language = language;
		words = new ArrayList<Word>();
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}
	
	public List<Word> getWords() {
		return words;
	}
	
	public void setWords(ArrayList<Word> words) {
		this.words = words;
	}
	
	public void addWord(Word w) {
		words.add(w);
	}
	
	public void removeWord(Word w) {
		for(int i = 0; i < words.size(); i++) {
			Word curr = words.get(i);
			if(curr.getString().equals(w.getString())) {
				words.remove(i);
				break;
			}
		}
	}
}
