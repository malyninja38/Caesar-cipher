package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller implements Initializable {


    @FXML
    Button encrypt;
    @FXML
    Button decrypt;
    @FXML
    Button fromfile;
    @FXML
    public TextArea left;
    @FXML
    public TextArea right;
    @FXML
    public TextField textfield;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public int k = 1;

    private static char[] znaki = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ', '.', ',', '-', ':','?','!'};
    // 33 znaki

    public void encode() throws FileNotFoundException {

        if(textfield.getText().equals("")) return;
        if(textfield.getText().equals("3") || textfield.getText().equals("6") || textfield.getText().equals("9") || textfield.getText().equals("11") ||
                textfield.getText().equals("12") || textfield.getText().equals("15") || textfield.getText().equals("18") || textfield.getText().equals("21") ||
                textfield.getText().equals("22") || textfield.getText().equals("24") || textfield.getText().equals("27") || textfield.getText().equals("30")){

            left.setText("Dla następujących wartości: \n 3, 6, 9, 11, 12, 15, 18, 21, 22, 24, 27, 30 \n nie da się poprawnie wczytać tekstu. \n Wybierz inną wartość.");
            return;
        }

        k = Integer.parseInt(textfield.getText());

        String text = left.getText();

        System.out.println("Tekst wejściowy: " + text);

        char[] letters = text.toCharArray();

        int index = 0;

        System.out.print("Tekst zakodowany: ");

        for (int i = 0; i < letters.length; i++) {

            char let = letters[i];                                 

            for (int j = 0; j < znaki.length; j++) {
                if (znaki[j] == let) {
                    index = j;
                }
            }

            int let2 = ((index * k) % (33)) % 33;                       
            letters[i] = znaki[let2];

            System.out.print(letters[i]);
        }
        System.out.println();

        String str = new String(letters);
        right.setText(str);
        left.setText("");

        PrintWriter zapis = new PrintWriter("zakodowany.txt");
        zapis.println(letters);
        zapis.close();
    }

    static private int modInv(int keyVal, int charCount) {           // Odwrotność modulo -> k^-1 z pierwszej części wzoru
        var a = new int[1024];
        var q = new int[1024];
        var x = new int[1024];

        a[0] = charCount; a[1] = keyVal;
        int i = 0;
        while (a[i] % a[i + 1] != 0)
        {
            a[i + 2] = a[i] % a[i + 1];
            q[i + 1] = (int)Math.floor((double)a[i] / a[i + 1]);
            i++;
        }
        int n = i + 1;
        x[n] = 0;
        x[n - 1] = 1;
        for (int j = n - 2; j >= 0; j--) {
            x[j] = x[j + 1] * q[j + 1] + x[j + 2];
        }
        if (a[n] != 1) return -1;
        if ((x[1] * a[0]) > (x[0] * a[1])) return charCount - x[0];
        return x[0];
    }

    public void decode() throws FileNotFoundException {

        if(textfield.getText().equals("")) return;
        if(textfield.getText().equals("3") || textfield.getText().equals("6") || textfield.getText().equals("9") || textfield.getText().equals("11") ||
                textfield.getText().equals("12") || textfield.getText().equals("15") || textfield.getText().equals("18") || textfield.getText().equals("21") ||
                textfield.getText().equals("22") || textfield.getText().equals("24") || textfield.getText().equals("27") || textfield.getText().equals("30")){

                right.setText("Dla następujących wartości: \n 3, 6, 9, 11, 12, 15, 18, 21, 22, 24, 27, 30 \n nie da się poprawnie wczytać tekstu. \n Wybierz inną wartość.");
                return;
        }

        String text = right.getText();
        System.out.println("Tekst zakodowany: " + text);

        char[] letters = text.toCharArray();

        int index = 0;

        System.out.print("Tekst odkodowany: ");

        for (int i = 0; i < letters.length; i++) {

            char let = letters[i];

            for (int j = 0; j < znaki.length; j++) {
                if (znaki[j] == let) {
                    index = j;
                }
            }

            int temp = modInv(k, 33);

            if(temp != -1) {
                int let2 = (index * temp) % 33;
                letters[i] = znaki[let2];
            }
            else{
                System.out.print("Dla tej wartości nie da się wyznaczyć odwrotnego modulo.");
                break;
            }

            System.out.print(letters[i]);
        }
        System.out.println();System.out.println();System.out.println();

        String str = new String(letters);
        left.setText(str);
        right.setText("");

        PrintWriter zapis = new PrintWriter("odkodowany.txt");
        zapis.println(letters);
        zapis.close();
    }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void buttonEncrypt() throws FileNotFoundException {
        encode();
    }

    public void buttonDecrypt() throws FileNotFoundException {
        decode();
    }

    public void buttonFromfile() throws FileNotFoundException {
        File file = new File("cezar.txt");
        Scanner text = new Scanner(file);
        left.setText(text.nextLine());
    }

}
