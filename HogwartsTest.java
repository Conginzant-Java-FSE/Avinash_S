package oops;

abstract class Wizard {
    protected String name;
    protected int powerLevel;

    public Wizard(String name, int powerLevel){
    this.name=name;
    this.powerLevel=powerLevel;
    }
    public abstract int castSpell(String spellName);
    public String getName(){
        return name;
    }
}

class DarkWizard extends Wizard {

    public DarkWizard(String name, int powerLevel) {
        super(name, powerLevel);
    }

    @Override
    public int castSpell(String spellName) {
        switch (spellName) {
            case "Crucio":
                return powerLevel * 3;
            case "AvadaKedavra":
                return powerLevel * 5;
            default:
                return powerLevel;
        }
    }
}

class HealingWizard extends Wizard {

    public HealingWizard(String name, int powerLevel) {
        super(name, powerLevel);
    }

    @Override
    public int castSpell(String spellName) {
        switch (spellName) {
            case "Heal":
                return powerLevel * 2;
            case "Revive":
                return powerLevel * 4;
            default:
                return 0;
        }
    }
}

class ElementalWizard extends Wizard {

    public ElementalWizard(String name, int powerLevel) {
        super(name, powerLevel);
    }

    @Override
    public int castSpell(String spellName) {
        switch (spellName) {
            case "Fireball":
                return powerLevel * 2;
            case "Lightning":
                return powerLevel * 3;
            default:
                return powerLevel;
        }
    }
}

public class HogwartsTest {

    public static void main(String[] args) {
        Wizard wizard1 = new DarkWizard("Voldemort", 80);
        printResult(wizard1, "AvadaKedavra");
        System.out.println();
        Wizard wizard2 = new HealingWizard("Hermione", 50);
        printResult(wizard2, "Fireball");
        System.out.println();
        Wizard wizard3 = new ElementalWizard("Dumbledore", 60);
        printResult(wizard3, "Lightning");
    }

    public static void printResult(Wizard wizard, String spell) {
        int damage = wizard.castSpell(spell);
        System.out.println("Wizard: " + wizard.getName());
        System.out.println("Spell Casted: " + spell);
        System.out.println("Damage Dealt: " + damage);
    }
}
