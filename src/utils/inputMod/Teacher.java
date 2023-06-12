//
//Copyright Alexandru Vrincianu
//2023
//

package utils.inputMod;

public class Teacher extends Person {
    // atributes
    protected String subject;

    // constructor
    public Teacher(String fname, String lname, int birthDay, int birthMonth, int birthYear, int age, String subject) {
        setFirstName(fname);
        setLastName(lname);
        setAge(age);
        setBirthDate(birthDay, birthMonth, birthYear);
        setSubject(subject);
    }

    // setters
    public void setSubject(String subject) {
        this.subject = subject;
    }

    // getters
    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " is born on " + getBirthDate() + " and he's " + getAge()
                + " years old. He teach the subject named " + getSubject() + ".";
    }
}
