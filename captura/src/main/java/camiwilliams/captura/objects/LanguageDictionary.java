package camiwilliams.captura.objects;

import java.util.ArrayList;
import java.util.List;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import camiwilliams.captura.Authenticator;

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
		
		Authenticator auth = new Authenticator();
		User curr = auth.getCurrentUser();
		Firebase firebaseDict = new Firebase("https://captura.firebaseio.com/users/"+curr.getUsername()+"/dictionaries/");
		firebaseDict.addListenerForSingleValueEvent(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  String num = "";
		    	  for(DataSnapshot langDicts : snapshot.getChildren()) {
		    		  if(langDicts.child("language").getValue().equals(language)) {
		    			  num = langDicts.getKey();
		    			  break;
		    		  }
		    	  }
		  		  Firebase firebaseWordDict = new Firebase("https://captura.firebaseio.com/users/"+curr.getUsername()+"/dictionaries/"+num+"/words/");
		  		  firebaseWordDict.addListenerForSingleValueEvent(new ValueEventListener() {
				      @Override
				      public void onDataChange(DataSnapshot snapshot) {
				          firebaseWordDict.child("" + snapshot.getChildrenCount()).child("word").setValue(w.getString());
				          firebaseWordDict.child("" + snapshot.getChildrenCount()).child("image").setValue(w.getImage());
				      }

				      @Override
				      public void onCancelled(FirebaseError firebaseError) {
				          System.out.println("The read failed: " + firebaseError.getMessage());
				      }
				  });
		      }

		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
	}
	
	public void removeWord(Word w) {
		for(int i = 0; i < words.size(); i++) {
			Word curr = words.get(i);
			if(curr.getString().equals(w.getString())) {
				words.remove(i);
				break;
			}
		}
		
		Authenticator auth = new Authenticator();
		User curr = auth.getCurrentUser();
		Firebase firebaseDict = new Firebase("https://captura.firebaseio.com/users/"+curr.getUsername()+"/dictionaries/");
		firebaseDict.addListenerForSingleValueEvent(new ValueEventListener() {
		      @Override
		      public void onDataChange(DataSnapshot snapshot) {
		    	  String num = "";
		    	  for(DataSnapshot langDicts : snapshot.getChildren()) {
		    		  if(langDicts.child("language").getValue().equals(language)) {
		    			  num = langDicts.getKey();
		    			  break;
		    		  }
		    	  }
		  		  Firebase firebaseWordDict = new Firebase("https://captura.firebaseio.com/users/"+curr.getUsername()+"/dictionaries/"+num+"/words/");
		  		  firebaseWordDict.addListenerForSingleValueEvent(new ValueEventListener() {
				      @Override
				      public void onDataChange(DataSnapshot snapshot) {
				    	  for(DataSnapshot word : snapshot.getChildren()) {
				    		  if(word.child("word").getValue().equals(w.getString())) {
				    			  firebaseWordDict.child(word.getKey()).setValue(null);
				    			  return;  
				    		  }
				    	  }
				      }

				      @Override
				      public void onCancelled(FirebaseError firebaseError) {
				          System.out.println("The read failed: " + firebaseError.getMessage());
				      }
				  });
		      }

		      @Override
		      public void onCancelled(FirebaseError firebaseError) {
		          System.out.println("The read failed: " + firebaseError.getMessage());
		      }
		  });
	}
}
