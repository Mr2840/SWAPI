import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dictionary {

    ArrayList<DictionaryEntry> ListOfContent = new ArrayList<>(List.of(
            new DictionaryEntry("darth vader", "https://swapi.dev/api/people/4/", "дарт вейдер", "Person"),
            new DictionaryEntry("luke skywalker", "https://swapi.dev/api/people/1/", "люк скайвокер", "Person"),
            new DictionaryEntry("leia organa", "https://swapi.dev/api/people/5/", "лея органа", "Person"),

            new DictionaryEntry("tatooine", "https://swapi.dev/api/planets/1/", "татуин","Planet"),
            new DictionaryEntry("alderaan", "https://swapi.dev/api/planets/2/", "альдеран","Planet"),
            new DictionaryEntry("endor", "https://swapi.dev/api/planets/7/", "эндор","Planet"),

            new DictionaryEntry("human", "https://swapi.dev/api/species/1/", "человек","Specie"),
            new DictionaryEntry("droid", "https://swapi.dev/api/species/2/", "дроид","Specie"),
            new DictionaryEntry("wookie", "https://swapi.dev/api/species/3/", "вуки","Specie"),

            new DictionaryEntry("star destroyer", "https://swapi.dev/api/starships/3/", "звездный разрушитель","Starship"),
            new DictionaryEntry("millennium falcon", "https://swapi.dev/api/starships/10/", "тысячилетний сокол","Starship"),
            new DictionaryEntry("death star", "https://swapi.dev/api/starships/9/", "звезда смерти","Starship"),

            new DictionaryEntry("sand crawler", "https://swapi.dev/api/vehicles/4/", "песчаная подкрадуля","Vehicle"),
            new DictionaryEntry("at-at", "https://swapi.dev/api/vehicles/18/", "ат-ат","Vehicle"),
            new DictionaryEntry("sail barge", "https://swapi.dev/api/vehicles/24/", "парусная баржа","Vehicle"),

            new DictionaryEntry("a new hope", "https://swapi.dev/api/films/1/", "новая надежда","Film"),
            new DictionaryEntry("return of the jedi",  "https://swapi.dev/api/films/3/", "возвращение джедая","Film"),
            new DictionaryEntry("attack of the clones", "https://swapi.dev/api/films/5/", "атака клонов","Film")
            ));

    private String castPrompt(String prompt){
        String r = "";
        String finalPrompt = prompt.toLowerCase();
        if (ListOfContent.stream().anyMatch(o -> o.name.equals(finalPrompt))){
            r=finalPrompt;
        } else if (ListOfContent.stream().anyMatch(o -> o.nameRu.equals(finalPrompt))) {
            for (int i = 0; i < ListOfContent.size(); i++) {
                if (Objects.equals(ListOfContent.get(i).nameRu, finalPrompt)) {
                    r = ListOfContent.get(i).name;
                }
            }
        }
        return r;
    }

    public String getType(String prompt){
        String Evseev = castPrompt(prompt);
        String r = "Не найдено";
        for (int i = 0; i < ListOfContent.size(); i++) {
            if (Objects.equals(ListOfContent.get(i).name, Evseev)) {
                r = ListOfContent.get(i).type;
            }
        }
        return r;
    }
    public String getURL(String prompt){
        String finalPrompt = castPrompt(prompt);
        String r = "Не найдено";
        for (int i = 0; i < ListOfContent.size(); i++) {
            if (Objects.equals(ListOfContent.get(i).name, finalPrompt)) {
                r = ListOfContent.get(i).URL;
            }
        }
        return r;
    }
}
