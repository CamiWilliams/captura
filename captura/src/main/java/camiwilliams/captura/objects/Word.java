package camiwilliams.captura.objects;

public class Word {

	private String word;
	private String img;
	
	public Word(String word, String img) {
		this.word = word;
		this.img = img;	
	}
	
	public String getString() {
		return word;
	}
	
	public String getImage() {
		return img;
	}
	
	public void setString(String word) {
		this.word = word;
	}
	
	public void setImage(String img) {
		this.img = img;
	}
}
