package Lara_files;

public class Student_1 {
    private String name;
    private int id;
    public Student_1(String n, int i) {
        name = n;
        id = i;
    }
    public void print() {
        System.out.println(name + " : " + id);
    }
}
