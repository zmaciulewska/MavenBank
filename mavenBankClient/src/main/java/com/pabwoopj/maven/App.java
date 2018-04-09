package com.pabwoopj.maven;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App extends Application {
    private static final Logger log = LogManager.getLogger();

    private Stage window;
    private Scene sceneMenu, sceneCreate, sceneFind, sceneDeposit, sceneGetBalance, sceneWithdraw, sceneTransfer;
    private Bank bank = new Bank();

    public static void main( final String[] args) {
        log.info("Uruchomienie aplikacji.");
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BankZTestami");
        window = primaryStage;

        Button buttonCreate = new Button();
        buttonCreate.setText("Utwórz konto");
        buttonCreate.setOnAction(e -> window.setScene(sceneCreate));

        Button buttonFind = new Button();
        buttonFind.setText("Znajdź konto");
        buttonFind.setOnAction(e -> window.setScene(sceneFind));

        Button buttonDeposit = new Button();
        buttonDeposit.setText("Wpłać na konto");
        buttonDeposit.setOnAction(e -> window.setScene(sceneDeposit));

        Button buttonGetBalance = new Button();
        buttonGetBalance.setText("Sprawdź środki na koncie");
        buttonGetBalance.setOnAction(e -> window.setScene(sceneGetBalance));

        Button buttonWithdraw = new Button();
        buttonWithdraw.setText("Wypłać z konta");
        buttonWithdraw.setOnAction(e -> window.setScene(sceneWithdraw));

        Button buttonTransfer = new Button();
        buttonTransfer.setText("Zrób przelew");
        buttonTransfer.setOnAction(e -> window.setScene(sceneTransfer));

        //SCENE MENU-1
        VBox layoutMenu = new VBox();
        layoutMenu.setSpacing(25);
        layoutMenu.setAlignment(Pos.CENTER);
        layoutMenu.getChildren().addAll(buttonCreate, buttonFind, buttonDeposit, buttonGetBalance, buttonWithdraw, buttonTransfer);
        sceneMenu = new Scene(layoutMenu, 400, 400);



        //SCENE CREATE-2
        GridPane layoutCreate = new GridPane();
        layoutCreate.setAlignment(Pos.CENTER);
        layoutCreate.setVgap(20);
        layoutCreate.setHgap(15);

        Label label = new Label("Podaj imie i nazwisko oraz adres");
        GridPane.setConstraints(label, 0, 0);

        TextField nameInput = new TextField();
        nameInput.setPromptText("Imie i nazwisko");
        GridPane.setConstraints(nameInput, 0, 1);

        TextField addressInput = new TextField();
        addressInput.setPromptText("Adres");
        GridPane.setConstraints(addressInput, 0, 2);

        Button buttonZatw = new Button();
        buttonZatw.setText("Zatwierdź");
        GridPane.setConstraints(buttonZatw, 0, 3);
        buttonZatw.setOnAction(e -> {
            Integer nr = bank.createAccount(nameInput.getText(), addressInput.getText());
            nameInput.clear();
            addressInput.clear();
            window.setScene(sceneMenu);
            InformationWindow.display("Informacja", "Operacja zakończona pomyślnie, numer podanego konta: " + nr);
        });

        layoutCreate.getChildren().addAll(label, nameInput, addressInput, buttonZatw);
        sceneCreate = new Scene(layoutCreate, 400, 400);

        //SCENE FIND-3
        GridPane layoutFind = new GridPane();
        layoutFind.setAlignment(Pos.CENTER);
        layoutFind.setVgap(20);
        layoutFind.setHgap(15);

        Label label3 = new Label("Podaj imie i nazwisko oraz adres");
        GridPane.setConstraints(label3, 0, 0);

        TextField nameInput3 = new TextField();
        nameInput3.setPromptText("Imie i nazwisko");
        GridPane.setConstraints(nameInput3, 0, 1);

        TextField addressInput3 = new TextField();
        addressInput3.setPromptText("Adres");
        GridPane.setConstraints(addressInput3, 0, 2);

        Button buttonZatw3 = new Button();
        buttonZatw3.setText("Zatwierdź");
        GridPane.setConstraints(buttonZatw3, 0, 3);
        buttonZatw3.setOnAction(e -> {
            Integer nr  = bank.findAccount(nameInput3.getText(), addressInput3.getText());
            nameInput3.clear();
            addressInput3.clear();
            window.setScene(sceneMenu);
            if(nr == null) {
                InformationWindow.display("Informacja", "Podane konto nie istnieje.");
            }
            else {
                InformationWindow.display("Informacja", "Numer szukanego konta: " + nr);
            }
        });

        layoutFind.getChildren().addAll(label3, nameInput3, addressInput3, buttonZatw3);
        sceneFind = new Scene(layoutFind, 400, 400);


        //SCENE DEPOSIT-4
        GridPane layoutDeposit = new GridPane();
        layoutDeposit.setAlignment(Pos.CENTER);
        layoutDeposit.setVgap(20);
        layoutDeposit.setHgap(15);

        Label label4 = new Label("Podaj numer konta i kwotę jaką chcesz wpłacić");
        GridPane.setConstraints(label4, 0, 0);

        TextField accNrInput = new TextField();
        accNrInput.setPromptText("Numer konta");
        GridPane.setConstraints(accNrInput, 0, 1);

        TextField amountInput = new TextField();
        amountInput.setPromptText("Kwota");
        GridPane.setConstraints(amountInput, 0, 2);

        Button buttonZatw4  = new Button();
        buttonZatw4.setText("Zatwierdź");
        GridPane.setConstraints(buttonZatw4, 0, 3);
        buttonZatw4.setOnAction(e -> {
            try {
                bank.deposit(Integer.parseInt(accNrInput.getText()),Long.parseLong(amountInput.getText()));
                InformationWindow.display("Informacja", "Wpłata została dokonana");
            }
            catch(BankInterface.AccountIdException aiexc) {
                log.error("Nieprawidlowe id konta.");
                InformationWindow.display("Informacja", "Konto o podanym numerze nie istnieje");
            }
            catch(NumberFormatException nfexc) {
                log.error("Nieprawidłowy format danych.");
                InformationWindow.display("Informacja", "Nieprawidłowy format danych");
            }
            finally {
                accNrInput.clear();
                amountInput.clear();
                window.setScene(sceneMenu);
            }
        });

        layoutDeposit.getChildren().addAll(label4, accNrInput, amountInput, buttonZatw4);
        sceneDeposit = new Scene(layoutDeposit, 400, 400);


        //SCENE GETBALANCE-5
        GridPane layoutGetBalance = new GridPane();
        layoutGetBalance.setAlignment(Pos.CENTER);
        layoutGetBalance.setVgap(20);
        layoutGetBalance.setHgap(15);

        Label label5 = new Label("Podaj numer konta którego stan chcesz sprawdzić");
        GridPane.setConstraints(label4, 0, 0);

        TextField accNrInput5 = new TextField();
        accNrInput5.setPromptText("Numer konta");
        GridPane.setConstraints(accNrInput5, 0, 1);

        Button buttonZatw5 = new Button();
        buttonZatw5.setText("Zatwierdź");
        GridPane.setConstraints(buttonZatw5, 0, 2);
        buttonZatw5.setOnAction(e -> {
            try {
                long amount = bank.getBalance(Integer.parseInt(accNrInput5.getText()));
                InformationWindow.display("Informacja","Na koncie jest "+amount+" zł");
            }
            catch(BankInterface.AccountIdException aiexc) {
                log.error("Nieprawidlowe id konta.");
                InformationWindow.display("Informacja","Konto o podanym numerze nie istnieje");
            }
            catch(NumberFormatException nfexc) {
                log.error("Nieprawidłowy format danych.");
                InformationWindow.display("Informacja","Nieprawidłowy format danych");
            }
            finally {
                accNrInput5.clear();
                window.setScene(sceneMenu);
            }

        });

        layoutGetBalance.getChildren().addAll(label5, accNrInput5, buttonZatw5);
        sceneGetBalance = new Scene(layoutGetBalance, 400, 400);


        //SCENE WITHDRAW-6
        GridPane layoutWithdraw = new GridPane();
        layoutWithdraw.setAlignment(Pos.CENTER);
        layoutWithdraw.setVgap(20);
        layoutWithdraw.setHgap(15);

        Label label6 = new Label("Podaj numer konta i kwotę jaką chcesz wypłacić");
        GridPane.setConstraints(label6, 0, 0);

        TextField accNrInput6 = new TextField();
        accNrInput6.setPromptText("Numer konta");
        GridPane.setConstraints(accNrInput6, 0, 1);

        TextField amountInput6 = new TextField();
        amountInput6.setPromptText("Kwota");
        GridPane.setConstraints(amountInput6, 0, 2);

        Button buttonZatw6 = new Button();
        buttonZatw6.setText("Zatwierdź");
        GridPane.setConstraints(buttonZatw6, 0, 3);
        buttonZatw6.setOnAction(e -> {
            try {
                bank.withdraw(Integer.parseInt(accNrInput6.getText()), Long.parseLong(amountInput6.getText()));
                InformationWindow.display("Informacja", "Wypłata została dokonana");
            }
            catch(BankInterface.AccountIdException aiexc) {
                log.error("Nieprawidlowe id konta.");
                InformationWindow.display("Informacja", "Konto o podanym numerze nie istnieje");
            }
            catch(NumberFormatException nfexc) {
                log.error("Nieprawidłowy format danych.");
                InformationWindow.display("Informacja", "Nieprawidłowy format danych");
            }
            catch(BankInterface.InsufficientFundsException ifexc) {
                log.error("Brak srodkow na koncie.");
                InformationWindow.display("Informacja", "Brak środków na koncie");
            }
            finally {
                accNrInput6.clear();
                amountInput6.clear();
                window.setScene(sceneMenu);
            }
        });

        layoutWithdraw.getChildren().addAll(label6, accNrInput6, amountInput6, buttonZatw6);
        sceneWithdraw=new Scene(layoutWithdraw, 400, 400);


        //SCENE TRANSFER-7
        GridPane layoutTransfer = new GridPane();
        layoutTransfer.setAlignment(Pos.CENTER);
        layoutTransfer.setVgap(20);
        layoutTransfer.setHgap(15);

        Label label7 = new Label("Podaj numery kont nadawcy, odbiorcy i kwotę");
        GridPane.setConstraints(label7, 0, 0);

        TextField accNrInput7 = new TextField();
        accNrInput7.setPromptText("Numer konta nadawcy");
        GridPane.setConstraints(accNrInput7, 0, 1);

        TextField amountInput7 = new TextField();
        amountInput7.setPromptText("Kwota");
        GridPane.setConstraints(amountInput7, 0, 2);

        TextField NrOdbInput7 = new TextField();
        NrOdbInput7.setPromptText("Numer konta odbiorcy");
        GridPane.setConstraints(NrOdbInput7, 0, 3);

        Button buttonZatw7 = new Button();
        buttonZatw7.setText("Zatwierdź");
        GridPane.setConstraints(buttonZatw7, 0, 4);
        buttonZatw7.setOnAction(e -> {
            try {
                bank.transfer(Integer.parseInt(accNrInput7.getText()), Integer.parseInt(NrOdbInput7.getText()), Long.parseLong(amountInput7.getText()));
                InformationWindow.display("Informacja","Transakcja została dokonana");
            }
            catch(BankInterface.AccountIdException aiexc) {
                log.error("Nieprawidlowe id konta.");
                InformationWindow.display("Informacja", "Konto o podanym numerze nie istnieje");
            }
            catch(NumberFormatException nfexc) {
                log.error("Nieprawidłowy format danych.");
                InformationWindow.display("Informacja", "Nieprawidłowy format danych");
            }
            catch(BankInterface.InsufficientFundsException ifexc) {
                log.error("Brak srodkow na koncie.");
                InformationWindow.display("Informacja", "Brak środków na koncie");
            }
            finally {
                accNrInput7.clear();
                amountInput7.clear();
                NrOdbInput7.clear();
                window.setScene(sceneMenu);
            }
        });

        layoutTransfer.getChildren().addAll(label7, accNrInput7, NrOdbInput7, amountInput7, buttonZatw7);
        sceneTransfer = new Scene(layoutTransfer, 400, 400);

        //WYSWIETLENIE SCENE MENU
        window.setScene(sceneMenu);
        window.show();
    }
}


//