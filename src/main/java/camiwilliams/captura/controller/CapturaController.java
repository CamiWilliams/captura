package camiwilliams.captura.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

import camiwilliams.captura.Authenticator;
import camiwilliams.captura.objects.LanguageDictionary;
import camiwilliams.captura.objects.User;
import camiwilliams.captura.objects.Word;

@Controller
public class CapturaController {

	private static String APP_ID = "EOMWfJk7qN516eOnS4K11J-PCiCGYf1qQ1B5U-up";
	private static String APP_SECRET = "vTRZoqMudBHKobLHzYNxGlQjVv2j8LlYJ1514vcs";
	private static Authenticator authenticator = new Authenticator();
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView handleIndex(Model map) {
		authenticator.removeCurrentUser();
		String message = "";
		return new ModelAndView("index", "message", message);
	}
	
	@RequestMapping(value = "/login_error", method = RequestMethod.POST)
	public ModelAndView handleLoginError(Model map) {
		String message = "";
		return new ModelAndView("login_error", "message", message);
	}

	@RequestMapping(value = "/new_user", method = RequestMethod.GET)
	public ModelAndView handleNewUser(Model map) {
		String message = "";
		return new ModelAndView("new_user", "message", message);
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public ModelAndView handleHomePOST(Model map) {
		return handleHomeGET(map);
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView handleHomeGET(Model map) {
		try {
			User user = authenticator.getCurrentUser();
			String message = user.getName();
			return new ModelAndView("home", "message", message);
		} catch (NullPointerException e) {
			return new ModelAndView("home", "message", "Guest");
		}
	}
	
	@RequestMapping(value = "/settings", method = RequestMethod.GET)
	public ModelAndView handleSettings(Model map) {
		try {
			User user = authenticator.getCurrentUser();

			List<LanguageDictionary> dictionaries = user.getDictionaries();
			String removeLanguages = "";

			if(dictionaries.size() == 0) {
				removeLanguages = "No dictionaries to remove!";
			} else {
				for(LanguageDictionary ld : dictionaries) {
					removeLanguages += "<option value='" + ld.getLanguage() + "'>" + ld.getLanguage() + "</option>";
				}
			}

			return new ModelAndView("settings", "removeLanguages", removeLanguages);
		} catch (NullPointerException e) {
			return new ModelAndView("settings", "message", "You are a guest, please login to view this feature<br>");
		}
	}
	
	@RequestMapping(value = "/settings", method = RequestMethod.POST)
	public ModelAndView handleRemoveLanguage(@RequestParam("removeDictionary") String removeLang, Model map) {
		try {
			User user = authenticator.getCurrentUser();
			LanguageDictionary dict = new LanguageDictionary(removeLang);
			user.removeDictionary(dict);	//TODO DB fix
			
			return handleSettings(map);
		} catch (NullPointerException e) {
			return new ModelAndView("settings", "message", "You are a guest, please login to view this feature<br>");
		}
	}
	
	@RequestMapping(value = "/dictionary", method = RequestMethod.GET)
	public ModelAndView handleDictionary(Model map) {
		try {
			User user = authenticator.getCurrentUser();
			Map<String, String> langs = new HashMap<String, String>(authenticator.getSupportedLanguages());
			String addLanguages = "";
			String message = "";
			List<LanguageDictionary> dictionaries = user.getDictionaries();
			
			for(LanguageDictionary ld : dictionaries) {
				if(langs.containsKey((ld.getLanguage()))) {
					langs.remove(ld.getLanguage());
				}
			}
			
			for(String str : langs.keySet()) {
				addLanguages +=  "<option value='" + str + "'>" + str + "</option>";
			}
			if(dictionaries.size() == 0) {
				message = "<h4>No words in your dictionary!</h4>";
			} else {
				message = "<div class='list-group'>";
				for(int i = dictionaries.size() - 1; i >= 0; i--) {
					LanguageDictionary ld = dictionaries.get(i);
					message += "<button type='submit' class='list-group-item' "
							+ "name='langSelect' value='"+ i +"'>"
							+ ld.getLanguage() + "</button>";
				}
				message += "</div>";	
			}
			Map<String, String> model = new HashMap<String, String>();
			model.put("addLanguages", addLanguages);
			model.put("message", message);
			return new ModelAndView("dictionary", "model", model);
		} catch (NullPointerException e) {
			return new ModelAndView("dictionary", "message", "You are a guest, please login to view this feature<br>");
		}
	}
	
	@RequestMapping(value = "/dictionary", method = RequestMethod.POST)
	public ModelAndView handleAddLangDictionary(@RequestParam("newDictionary") String newLang, Model map) {
		try {
			User user = authenticator.getCurrentUser();
			LanguageDictionary dict = new LanguageDictionary(newLang);
			user.addDictionary(dict);
			authenticator.updateUser(user, user.getUsername());

			return handleDictionary(map);
		} catch (NullPointerException e) {
			return new ModelAndView("dictionary", "message", "You are a guest, please login to view this feature<br>");
		}
	}
	
	@RequestMapping(value = "/lang_dictionary", method = RequestMethod.POST)
	public ModelAndView handleBranchDictionary(@RequestParam("langSelect") String lang_num, Model map) {
		try {
			System.out.println("Button clicked: " + lang_num);
			User user = authenticator.getCurrentUser();
			int index = Integer.parseInt(lang_num);
			LanguageDictionary ld = user.getDictionaries().get(index);
			Map<String, String> model = new HashMap<String, String>();
			model.put("lang", ld.getLanguage());
			model.put("message", prepDictionary(ld));

			return new ModelAndView("lang_dictionary", "model", model);
		} catch (NullPointerException e) {
			return new ModelAndView("lang_dictionary", "message", "You are a guest, please login to view this feature<br>");
		}
	}
	
	private String prepDictionary(LanguageDictionary ld) {
		List<Word> dictionary = ld.getWords();
		String lang = ld.getLanguage();
		String country_code = authenticator.getSupportedLanguages().get(lang);
		if(dictionary.size() == 0) {
			return "<h4>No words in your dictionary!</h4>";
		}
		String message = "<ul class='list-group'>";
		for(int i = dictionary.size() - 1; i >= 0; i--) {
			Word w = dictionary.get(i);
			message += "<li class='list-group-item'><div style='display:-webkit-box'>"
					+ "<div id=\"lang\" style=\"display:none\">" 
	    			+ country_code + "</div><img width='100' src='" + w.getImage() + "'/> " 
	    			+ "<div id='word' class='word'>" + w.getString() + "</div>"
	    			+ "<button type='button' class='form-control play'"
	    			+ " onclick='play(\""+ w.getString() +"\",\""
	    			+ country_code +"\")'><span class=\"glyphicon glyphicon-play\" "
	    			+ "aria-hidden=\"true\"></button></div></li>";
		}
		message += "</ul>";
		return message;
	}
	
	@RequestMapping(value = "/camera", method = RequestMethod.GET)
	public ModelAndView handleCamera(Model map) {
		try {
			User user = authenticator.getCurrentUser();
			List<LanguageDictionary> dictionaries = user.getDictionaries();
			String message = "";
			for(LanguageDictionary ld : dictionaries) {
				message += "<option value='" + ld.getLanguage() + "'>" + ld.getLanguage() + "</option>";
			}
			
			return new ModelAndView("camera", "message", message);
		} catch(NullPointerException e) {
			return new ModelAndView("camera", "notwork", "You are a guest, please login to view this feature<br>");
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ModelAndView handleFormUpload(@RequestParam("pic") MultipartFile file, @RequestParam("langSelect") String langDict, Model map) throws Exception{
		try {
			if (!file.isEmpty()) {
				StringBuilder sb = new StringBuilder();
	    		sb.append("data:image/png;base64,");
	    		sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(file.getBytes(), false)));

	    	    ClarifaiClient clarifai = new ClarifaiClient(APP_ID, APP_SECRET);
	    	    List<RecognitionResult> results = clarifai.recognize(new RecognitionRequest(file.getBytes()));

	    		authenticator.setCurrentImage(sb.toString());	   
	    		authenticator.setCurrentLanguageDictionary(langDict);	
	    		authenticator.setCurrentWord(translate(results.get(0).getTags().get(0).getName(), langDict));

	    		String curr_word = authenticator.getCurrentWord();
	    		String curr_img = authenticator.getCurrentImage();
	    		
	    		String message = "<img width='250' src='" + curr_img + "'>";
	    		User user = authenticator.getCurrentUser();
	    		
	    		LanguageDictionary curr_ld = getLangDict(user, langDict);
	    		
	    		if(curr_ld == null) {
	    			message = "<br><div style='text-align:center;'>"
	    					+ "<h3>This branch did not work... Try again!</h3> </div><br><br>";
	    			return new ModelAndView("home", "notwork", message);
	    		}
	    		
	    		String decide = "";
	    		for(Word word : curr_ld.getWords()) {
	    			if(word.getString().equals(curr_word)) {
	    				decide += "<div id=\"lang\" style=\"display:none\">" 
	    						+ authenticator.getSupportedLanguages().get(curr_ld.getLanguage()) 
	    						+ "</div><br>Your " + curr_ld.getLanguage() + " dictionary already contains this word!"
		    					+ "<br> Would you like to update the picture to this one?<br><br><br><button type='button'"
		    					+ "class='form-control'  onclick='window.location.href=\"updateWord.html\"' "
		    					+ ">Update</button>";
	    				Map<String, String> model = new HashMap<String, String>();
	    				model.put("word", curr_word);
	    				model.put("message", message);
	    				model.put("decide", decide);
	    				
	    				return new ModelAndView("upload", "model", model);
	    			}
	    		}
	    		decide += "<div id=\"lang\" style=\"display:none\">" 
	    			 + authenticator.getSupportedLanguages().get(curr_ld.getLanguage()) 
	    		 	 + "</div><br>Your " + curr_ld.getLanguage() + " dictionary does not contain this word!"
	    			 + "<br>Would you like to add this word to your " + curr_ld.getLanguage() 
	    			 + " dictionary?<br><br><br><button type='button' class='form-control' "
	    			 + "onclick='window.location.href=\"addWord.html\"'>Add</button>";

				Map<String, String> model = new HashMap<String, String>();
				model.put("word", curr_word);
				model.put("message", message);
				model.put("decide", decide);
				
				return new ModelAndView("upload", "model", model);
			}
			String message = "<br><div style='text-align:center;'>"
					+ "<h3>This image did not work... Try again!</h3> </div><br><br>";
			return new ModelAndView("home", "notwork", message);
		} catch (NullPointerException e) {
			return new ModelAndView("upload", "message", "You are a guest, please login to view this feature<br>");
		}
	}
	
	private LanguageDictionary getLangDict(User user, String langDict) {
		List<LanguageDictionary> branches = user.getDictionaries();

		LanguageDictionary curr_dict = null;
		for(LanguageDictionary ld : branches) {
			if(ld.getLanguage().equals(langDict)) {
				curr_dict = ld;
				break;
			}
		}
		
		return curr_dict;
	}
	
	@RequestMapping(value = "/addWord", method = RequestMethod.GET)
	public ModelAndView handleAddWord(Model map) {
		try {
			User user = authenticator.getCurrentUser();

    		String curr_word = authenticator.getCurrentWord();
    		String curr_img = authenticator.getCurrentImage();
    		LanguageDictionary curr_ld = getLangDict(user, authenticator.getCurrentLanguageDictionary());
    		
			Word dic = new Word(curr_word, curr_img);
			curr_ld.addWord(dic);

			Map<String, String> model = new HashMap<String, String>();
			model.put("lang", curr_ld.getLanguage());
			model.put("message", prepDictionary(curr_ld));
			return new ModelAndView("addWord", "model", model);
		} catch (NullPointerException e) {
			return new ModelAndView("addWord", "message", "You are a guest, please login to view this feature<br>");
		}
	}
	
	@RequestMapping(value = "/updateWord", method = RequestMethod.GET)
	public ModelAndView handleUpdateWord(Model map) {
		try {
			User user = authenticator.getCurrentUser();

    		String curr_word = authenticator.getCurrentWord();
    		String curr_img = authenticator.getCurrentImage();
    		LanguageDictionary curr_ld = getLangDict(user, authenticator.getCurrentLanguageDictionary());

			Word word = new Word(curr_word, curr_img);
			
			curr_ld.removeWord(word);
			curr_ld.addWord(word);
			Map<String, String> model = new HashMap<String, String>();
			model.put("lang", curr_ld.getLanguage());
			model.put("message", prepDictionary(curr_ld));

			return new ModelAndView("updateWord", "model", model);
		} catch (NullPointerException e) {
			return new ModelAndView("updateWord", "message", "You are a guest, please login to view this feature<br>");
		}
	}
	
	public String translate(String word, String lang) throws Exception {
		Translate.setClientId("captura");
	    Translate.setClientSecret("9AnUiVone9beKHlpardalpp2qcIWSzFtdvPamXgVN28=");
	    //String OAuth = Translate.getToken("captura", "9AnUiVone9beKHlpardalpp2qcIWSzFtdvPamXgVN28");
	    
	    String translation = word;
	    
	    if(lang.equals("Spanish")) {
	    	translation = Translate.execute(word, Language.ENGLISH, Language.SPANISH);
	    } else if(lang.equals("French")) {
	    	translation = Translate.execute(word, Language.ENGLISH, Language.FRENCH);
	    } else if(lang.equals("German")) {
	    	translation = Translate.execute(word, Language.ENGLISH, Language.GERMAN);
	    }
	    
	    return translation;
	}
	
}
