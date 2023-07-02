package ru.eugene.restest.utils;


import java.util.Random;

public class StringConverter {

    private static final char[] cyrillic;
    private static final String[] english;
    private static Random random;

    static  {
        cyrillic = new char[]{' ', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м',
                'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я'};
        english = new String[]{" ", "a", "b", "v", "g", "d", "e", "jo", "jh", "z", "i", "i", "k", "l",
                "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "c", "ch", "sh", "sh", "", "y", "", "e", "yu", "ya"};
        random = new Random();
    }

    public static String uniqueReference(String word){
        word = word.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < word.length(); i++){
            // Если сможем поменять с кириллицы на английский
            boolean changed = false;
            for(int j = 0; j < cyrillic.length; j++){
                // Если символ в слове можно заменить на английский, то меняем
                if(word.charAt(i) == cyrillic[j]){
                    sb.append(english[j]);
                    changed = true;
                    break;
                }
            }
            // Если такой символ не смогли заменить, то добавляем какой стоит изначально
            if(!changed)
                sb.append(word.charAt(i));
        }

        return sb.toString();
    }

    public static String toEnglish(String word){
        word = word.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < word.length(); i++){
            // Если сможем поменять с кириллицы на английский
            boolean changed = false;
            for(int j = 0; j < cyrillic.length; j++){
                // Если символ в слове можно заменить на английский, то меняем
                if(word.charAt(i) == cyrillic[j]){
                    sb.append(english[j]);
                    changed = true;
                    break;
                }
            }
            // Если такой символ не смогли заменить, то добавляем какой стоит изначально
            if(!changed)
                sb.append(word.charAt(i));
        }

        return sb.toString();
    }
}
