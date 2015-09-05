package camiwilliams.captura.objects;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class User {

	private static String DBurl = "/Users/camiwilliams/Documents/Demos/Maven/captura/src/main/java/camiwilliams/captura/mockDB.json";
	private String name;
	private String email;
	private String username;
	private String password;
	private String nativeLang;
	private List<LanguageDictionary> languages;
	
	public User(String name, String email, String username, String password, String nativeLang) {
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.nativeLang = nativeLang;
		languages = new ArrayList<LanguageDictionary>();
		languages.add(new LanguageDictionary(nativeLang));
	}
	
	public String getName() {
		return name;
	}
	
	public void set(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNativeLang() {
		return nativeLang;
	}
	
	public void setNativeLang(String nativeLang) {
		this.nativeLang = nativeLang;
	}
	
	public List<LanguageDictionary> getDictionaries() {
		return languages;
	}
	
	public void addDictionary(LanguageDictionary dict) {
		languages.add(dict);
		
		//DB work
		JSONObject newObj = new JSONObject();
		newObj.put("language", dict.getLanguage());
		newObj.put("words", new ArrayList<JSONObject>());
		
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(DBurl));
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject users = (JSONObject) jsonObject.get("users");
			JSONObject specificUser = (JSONObject) users.get(username);
			JSONArray userDictionary = (JSONArray) specificUser.get("dictionaries");
			userDictionary.add(newObj);
	        FileWriter file = new FileWriter(DBurl);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();

		} catch (Exception e) {
			System.out.println(e.getClass().getName());
		}
	}
	
	public void removeDictionary(LanguageDictionary dict) {
		LanguageDictionary removed = dict;
		for(int i = 0; i < languages.size(); i++) {
			LanguageDictionary curr = languages.get(i);
			removed = curr;
			if(dict.getLanguage().equals(curr.getLanguage())) {
				languages.remove(i);
				break;
			}
		}
		
		//DB work
		JSONObject newObj = new JSONObject();
		newObj.put("language", dict.getLanguage());
		ArrayList<JSONObject> jsonWords = new ArrayList<JSONObject>();
		for(Word w : removed.getWords()) {
			JSONObject temp = new JSONObject();
			temp.put("word", w.getString());
			temp.put("image", w.getImage());
			
			jsonWords.add(temp);
		}
		newObj.put("words", jsonWords);
		
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(DBurl));
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject users = (JSONObject) jsonObject.get("users");
			JSONObject specificUser = (JSONObject) jsonObject.get(username);
			JSONArray userDictionary = (JSONArray) jsonObject.get("dictionaries");
			
			userDictionary.remove(newObj);
	        FileWriter file = new FileWriter(DBurl);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();

		} catch (Exception e) {
			
		}
	}
}
