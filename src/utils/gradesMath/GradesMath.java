//
//Copyright Alexandru Vrincianu
//2023
//

package utils.gradesMath;

import java.util.Scanner;

public abstract class GradesMath {
    protected int total, totalCurriculum; // total = total grades
    protected int[] localAverage = new int[50], grades = new int[50];
    protected float globalAverage;

    void setTotal(int total) { // good
        this.total = total;
    }

    public int getTotal() { // good
        return this.total;
    }

    public int getTotalCurriculum() { // good
        return this.totalCurriculum;
    }

    public void setTotalCurriculum(int totalCurriculum) { // good*
        this.totalCurriculum = totalCurriculum;
    }

    public abstract float globalAverageCalc(); // I NEED localAverage methods

    // public abstract int localAverageCalc(String name); I NEED A DATABASE TO
    // EXTRACT THE GRADES FOR A SPECIFIC DISCIPLINE

    public abstract void setGrades(int total, Scanner scan); // good

    public abstract int getGrade(int nr); // good

    public abstract float getGlobalAverage();

    public abstract int getLocalAverage(int nr);
}
