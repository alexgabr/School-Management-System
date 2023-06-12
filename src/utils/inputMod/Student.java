//
//Copyright Alexandru Vrincianu
//2023
//

package utils.inputMod;

public class Student extends Person {
    // atributes
    protected String classroom, registrationNr;

    // constructor
    public Student(String fname, String lname, int birthDay, int birthMonth, int birthYear, int age,
            String classroom, String regNr) {

        setFirstName(fname);
        setLastName(lname);
        setAge(age);
        setBirthDate(birthDay, birthMonth, birthYear);
        setClassroom(classroom);
        setRegistrationNr(regNr);
    }

    // setters
    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public void setRegistrationNr(String registrationNr) {
        this.registrationNr = registrationNr;
    }

    // getters
    public String getClassroom() {
        return classroom;
    }

    public String getRegistrationNr() {
        return registrationNr;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " is born on " + getBirthDate() + " and he's " + getAge()
                + " years old. He is student in the " + getClassroom() + " grade. He's registration number is " + getRegistrationNr();
    }
}
