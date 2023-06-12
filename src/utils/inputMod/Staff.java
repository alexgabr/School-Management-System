//
//Copyright Alexandru Vrincianu
//2023
//

package utils.inputMod;

public class Staff extends Person {
    // atributes
    String position;

    // constructor
    public Staff(String fname, String lname, int birthDay, int birthMonth, int birthYear, int age,
            String positionHeld) {
        setFirstName(fname);
        setLastName(lname);
        setAge(age);
        setBirthDate(birthDay, birthMonth, birthYear);
        setPosition(positionHeld);
    }

    // setters
    public void setPosition(String position) {
        this.position = position;
    }

    // getters
    public String getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " is born on " + getBirthDate() + " and he's " + getAge()
                + " years old. He held the " + getPosition() + " position.";
    }
}
