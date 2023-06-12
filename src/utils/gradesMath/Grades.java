//
//Copyright Alexandru Vrincianu
//2023
//

package utils.gradesMath;

import java.util.Scanner;

public class Grades extends GradesMath {

    public Grades(int total, int totalCurriculum, Scanner scan) {
        setTotalCurriculum(totalCurriculum);
        setTotal(total);

        for (int i = 0; i < total; i++) {
            this.grades[i] = scan.nextInt();
        }
    }

    @Override
    public void setGrades(int total, Scanner scan) { // good
        for (int i = getTotal(); i < getTotal() + total; i++) {
            grades[i] = scan.nextInt();
        }

        setTotal(total + this.total);
    }

    @Override
    public int getGrade(int nr) { // good
        return grades[nr];
    }

    @Override
    public int getLocalAverage(int nr) {
        return localAverage[nr];
    }

    @Override
    public float getGlobalAverage() {
        return globalAverage;
    }

    @Override
    public float globalAverageCalc() {
        int sum = 0;

        for (int i = 0; i < getTotalCurriculum(); i++) {
            sum = sum + getLocalAverage(i);
        }

        return (float) sum / getTotalCurriculum(); // ****
    }
}