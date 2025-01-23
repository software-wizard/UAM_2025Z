package pl.psi.spells;

import java.util.ArrayList;

public class SystemSpellBook {

    private static SpellBook spellBookInstance = null;

    public static synchronized SpellBook getSpellBookInstance(){
        int SYSTEM_SPELL_MANA = 100_000;
        if(spellBookInstance == null){
            System.out.println("INIT SYSTEM SPELL BOOK");
            spellBookInstance = new SpellBook(SYSTEM_SPELL_MANA, new ArrayList<>());
        }

        System.out.println("RETURN SYSTEM SPELL BOOK");
        return spellBookInstance;
    }
}
