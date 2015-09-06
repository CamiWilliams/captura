package camiwilliams.captura.objects;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class User {

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
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
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
	
	public void setDictionaries(List<LanguageDictionary> languages) {
		this.languages = languages;
	}
	
	public void addDictionary(final LanguageDictionary dict) {
		languages.add(dict);
		Firebase firebaseAddDict = new Firebase("https://captura.firebaseio.com/users/"+username+"/dictionaries");
		firebaseAddDict.addListenerForSingleValueEvent(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		  		  Firebase firebaseAddDict = new Firebase("https://captura.firebaseio.com/users/"+username+"/dictionaries");
		          System.out.println("There are " + snapshot.getChildrenCount() + " dictionaries for " + name);
		          firebaseAddDict.child("" + snapshot.getChildrenCount()).child("language").setValue(dict.getLanguage());
		          for(int i = 0; i < dict.getWords().size(); i++) {
		        	  Word word = dict.getWords().get(i);
			          firebaseAddDict.child("" + snapshot.getChildrenCount()).child("words").child("" + i).child("word").setValue(word.getString());
			          firebaseAddDict.child("" + snapshot.getChildrenCount()).child("words").child("" + i).child("image").setValue(word.getImage());
		          }
		      }

		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
	}
	
	public void removeDictionary(final LanguageDictionary dict) {
		for(int i = 0; i < languages.size(); i++) {
			LanguageDictionary curr = languages.get(i);
			if(dict.getLanguage().equals(curr.getLanguage())) {
				languages.remove(i);
				break;
			}
		}
		
		Firebase firebaseAddDict = new Firebase("https://captura.firebaseio.com/users/"+username+"/dictionaries");
		firebaseAddDict.addListenerForSingleValueEvent(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  for(DataSnapshot lds : snapshot.getChildren()) {
		    		  for(DataSnapshot lds_child : lds.getChildren()) {
		    			  if(lds_child.getValue().equals(dict.getLanguage())) {
			    			  Firebase toremove = new Firebase("https://captura.firebaseio.com/users/"+username+"/dictionaries/" + lds.getKey());
			    			  toremove.setValue(null);
			    			  return;  
			    		  }  
		    		  }
		    	  }
		      }

		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });

		
	}
}
