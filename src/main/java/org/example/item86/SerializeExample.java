package org.example.item86;

import java.io.*;

public class SerializeExample implements Serializable {

    private final String name;
    private final int age;
    private final transient String password;

    public SerializeExample(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    @Override
    public String toString() {
        return "SerializeExample{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                '}';
    }

    static void main() throws IOException {
        // 직렬화
        try(FileOutputStream fos = new FileOutputStream("example1.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new SerializeExample("gunha", 18, "super_secret" ));
            System.out.println("created and saved");
        }

        try(FileInputStream fis = new FileInputStream("example1.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            SerializeExample result = (SerializeExample) ois.readObject();
            System.out.println("result: " + result);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
