package com.first;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        File file = new File("gameFile.txt");
        Person archer = null;
        Person swordsman = null;
        Scanner scanner = new Scanner(System.in);
        String[] array = {"Archer's turn. ", "Swordsman's turn. "};
        String string = "";


        if (file.length() == 0) {
            archer = new Person("Archer", 1000);
            swordsman = new Person("Swordsman", 1000);
        } else {
            String newString = "";
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] bytes = fileInputStream.readAllBytes();
                newString = new String(bytes);
                string = newString;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (newString.endsWith(" wins! \n\n")) {
                string = "";
                archer = new Person("Archer", 1000);
                swordsman = new Person("Swordsman", 1000);
            } else {
                Person[] livesFromFile = getLivesFromFile(newString);
                if (livesFromFile[0].getName().equals("Archer")) {
                    archer = livesFromFile[0];
                    swordsman = livesFromFile[1];
                } else {
                    archer = livesFromFile[1];
                    swordsman = livesFromFile[0];
                }
            }
        }

        while (true) {
            scanner.nextLine();
            if (string.endsWith(" wins! \n\n")) {
                string = "";
                break;
            }

            String turn = array[(int) Math.round(Math.random() * 1)];
            if (turn.equals(array[0])) {
                string += nextHit(archer, swordsman, 10, turn) + "\n";
                System.out.println(string);
            } else if (turn.equals(array[1])) {
                string += nextHit(swordsman, archer, 20, turn) + "\n";
                System.out.println(string);
            }
            string += '\n';

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                bufferedWriter.write(string);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String nextHit(Person p1, Person p2, int percent, String turn) {
        double remainedLives = p2.getLives() - p2.getLives() * percent / 100;
        p2.setLives((int) (remainedLives));

        if (p1.getLives() == 0) return p2.getName() + " wins! ";
        else if (p2.getLives() == 0) return p1.getName() + " wins! ";
        else return turn + "\n" + p1 + "\n" + p2;
    }

    public static Person[] getLivesFromFile(String string) {
        String[] split = string.split("\\n");
        String[] split1 = split[split.length - 2].split(" ");
        String[] split2 = split[split.length - 1].split(" ");

        Person[] persons = {new Person(split1[0], Integer.parseInt(split1[1])),
                            new Person(split2[0], Integer.parseInt(split2[1]))};
        return persons;
    }

}
