package camiwilliams.captura;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import camiwilliams.captura.objects.User;

public class Authenticator {

	private static String DBurl = "/Users/camiwilliams/Documents/Demos/Maven/captura/src/main/java/camiwilliams/captura/mockDB.json";
	private static Map<String, String> supportedLanguages;
	private static Map<String, User> mockDB;
	private static User currentUser;
	private static String currentImage;
	private static String currentWord;
	private static String currentLanguageDictionary;
	
	public Authenticator() {
		supportedLanguages = new HashMap<String, String>();
		supportedLanguages = new HashMap<String, String>();
		supportedLanguages.put("English (US)", "en-us");
		supportedLanguages.put("Spanish", "es-es");
		supportedLanguages.put("French", "fr-fr");
		supportedLanguages.put("Chinese", "ca-es");
		supportedLanguages.put("German", "de-de");
		
		//Read from mockDB.json and fill mockDB
		mockDB = new HashMap<String, User>();
		JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader(DBurl));
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = (JSONObject) obj;
			
		JSONObject users = (JSONObject) jsonObject.get("users");
		Set set = users.keySet();
		for(Object set_obj : set) {
			JSONObject curr_user =(JSONObject) users.get(set_obj.toString());
			User temp = new User((String) curr_user.get("name"), (String) curr_user.get("email"),
					(String) curr_user.get("username"),(String) curr_user.get("password"),
					(String) curr_user.get("nativeLang"));
			mockDB.put(temp.getUsername(), temp);
		}
		
	}
	
	public String authenticate(String username, String password) {
		if(!mockDB.containsKey(username)) {
			return "fail";
		} else if(password.equals(mockDB.get(username).getPassword())) {
			return "success";
		} else {
			return "fail";
		}
	}
	
	public String newUser(String name, String email, String username, String password, String nativeLang) {
		if(mockDB.containsKey(username)) {
			return "fail";
		} else {
			JSONObject newObj = new JSONObject();
			newObj.put("name", name);
			newObj.put("email", email);
			newObj.put("username", username);
			newObj.put("password", password);
			newObj.put("nativeLang", nativeLang);
			newObj.put("dictionaries", new ArrayList<JSONObject>());
			
			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(new FileReader(DBurl));
				JSONObject jsonObject = (JSONObject) obj;
				JSONObject users = (JSONObject) jsonObject.get("users");

				users.put(username, newObj);
		        FileWriter file = new FileWriter(DBurl);
	            file.write(jsonObject.toJSONString());
	            file.flush();
	            file.close();
				User temp = new User(name, email, username, password, nativeLang);
				mockDB.put(username, temp);

			} catch (Exception e) {
				return "fail";
			}
			return "success";
		}
	}
	
	public Map<String, String> getSupportedLanguages() {
		return supportedLanguages;
	}
	
	
	public User getUser(String username) {
		return mockDB.get(username);
	}
	
	public void updateUser(User user, String username) {
		mockDB.put(username, user);
	}
	
	public void setCurrentUser(String username) {
		currentUser = mockDB.get(username);
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void removeCurrentUser() {
		currentUser = null;
	}
	
	public String getCurrentWord() {
		return currentWord;
	}
	
	public String getCurrentImage() {
		return currentImage;
	}
	
	public String getCurrentLanguageDictionary() {
		return currentLanguageDictionary;
	}
	
	public void setCurrentWord(String currentWord) {
		Authenticator.currentWord = currentWord;
	}
	
	public void setCurrentImage(String currentImage) {
		Authenticator.currentImage = currentImage;
	}
	
	public void setCurrentLanguageDictionary(String currentLanguageDictionary) {
		Authenticator.currentLanguageDictionary = currentLanguageDictionary;
	}
}
