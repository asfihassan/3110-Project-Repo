package Lara_files;
public class Student_2 {
    private String name;
    private int id;
    private double gpa; 
    public Student_2(String n, int i) {
        this.name = n;
        this.id = i;
        this.gpa = 0.0;
    }
    public void print() {
        System.out.println(name + " : " + id + " : " + gpa);
    }
}

