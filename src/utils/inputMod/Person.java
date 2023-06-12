//
//Copyright Alexandru Vrincianu
//2023
//

package utils.inputMod;

public abstract class Person {
    // atributes
    protected String firstName, lastName, birthDate;
    protected int age;

    // setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBirthDate(int day, int month, int year) {
        String birth;
        if (day / 10 == 0) {
            birth = "0" + Integer.toString(day) + ".";
        } else {
            birth = Integer.toString(day) + ".";
        }

        if (month / 10 == 0) {
            birth = birth + "0" + Integer.toString(month) + "." + Integer.toString(year);
        } else {
            birth = birth + Integer.toString(month) + "." + Integer.toString(year);
        }

        this.birthDate = birth;
    }

    // getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public int getAge() {
        return age;
    }
}
