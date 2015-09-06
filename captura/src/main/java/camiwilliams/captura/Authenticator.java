package camiwilliams.captura;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import camiwilliams.captura.objects.LanguageDictionary;
import camiwilliams.captura.objects.User;
import camiwilliams.captura.objects.Word;

public class Authenticator {

	private static Map<String, String> supportedLanguages;
	private static Map<String, User> mockDB;
	private static User currentUser;
	private static String currentImage;
	private static String currentWord;
	private static String currentLanguageDictionary;
	
	public Authenticator() {
		supportedLanguages = new HashMap<String, String>();
		mockDB = new HashMap<String, User>();
		supportedLanguages.put("English (US)", "en-us");
		supportedLanguages.put("Spanish", "es-es");
		supportedLanguages.put("French", "fr-fr");
		supportedLanguages.put("German", "de-de");
		
		Firebase firebaseUsers = new Firebase("https://captura.firebaseio.com/users");
		  // Attach an listener to read the data at our posts reference

		firebaseUsers.addValueEventListener(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		          System.out.println("There are " + snapshot.getChildrenCount() + " users");
		          for (DataSnapshot userSnapshot: snapshot.getChildren()) {
		            String username = (String) userSnapshot.child("username").getValue();
		            String email = (String) userSnapshot.child("email").getValue();
		            String name = (String) userSnapshot.child("name").getValue();
		            String password = (String) userSnapshot.child("password").getValue();
		            String nativeLang = (String) userSnapshot.child("nativeLang").getValue();
		            ArrayList<LanguageDictionary> dictionaries = new ArrayList<LanguageDictionary>();
		            for(DataSnapshot dictSnapshot : userSnapshot.child("dictionaries").getChildren()) {
		            	String language = (String) dictSnapshot.child("language").getValue();
			            ArrayList<Word> words = new ArrayList<Word>();
			            for(DataSnapshot wordSnapshot : dictSnapshot.child("words").getChildren()) {
			            	String image = (String) wordSnapshot.child("image").getValue();
			            	String word = (String) wordSnapshot.child("word").getValue();
			            	words.add(new Word(word, image));
			            }
			            LanguageDictionary temp = new LanguageDictionary(language);
			            temp.setWords(words);
			            dictionaries.add(temp);
		            }
		            
		            User user = new User(name, email, username, password, nativeLang);
		            user.setDictionaries(dictionaries);
		            mockDB.put(username, user);
		          }
		      }

		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
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
			Firebase firebaseNewUser = new Firebase("https://captura.firebaseio.com/users/"+username);
			firebaseNewUser.child("name").setValue(name);
			firebaseNewUser.child("email").setValue(email);
			firebaseNewUser.child("username").setValue(username);
			firebaseNewUser.child("password").setValue(password);
			firebaseNewUser.child("nativeLang").setValue(nativeLang);
			firebaseNewUser.child("dictionaries").child("0").child("language").setValue(nativeLang);

            User user = new User(name, email, username, password, nativeLang);
			mockDB.put(username, user);
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
