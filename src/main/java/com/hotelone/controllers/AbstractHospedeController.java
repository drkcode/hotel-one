package com.hotelone.controllers;

import com.hotelone.entities.Hospede;
import com.hotelone.services.HospedeService;
import com.hotelone.utils.Alerta;
import com.hotelone.utils.SceneRender;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public abstract class AbstractHospedeController {
    @FXML
    protected TextField inputNome;

    @FXML
    protected TextField inputSobrenome;

    @FXML
    protected TextField inputTelefone;

    @FXML
    protected DatePicker inputDataNascimento;

    @FXML
    protected ChoiceBox<String> inputNacionalidade;

    protected Hospede hospedeData = new Hospede();

    protected final HospedeService hospedeService;

    public AbstractHospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    public void initialize() {
        loadListaNacionalidades();
    }

    public void botaoCancelarHandler(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        new SceneRender(source, "menu-view.fxml").update();
    }

    @FXML
    public void inputNomeHandler(KeyEvent event) {
        if(inputNome.getText().length() < 2) {
            desabilitarBotao();
            return;
        }
        inputSobrenome.setDisable(false);
        habilitarBotao();
    }

    @FXML
    public void inputSobrenomeHandler(KeyEvent event) {
        if(inputSobrenome.getText().length() < 2) {
            desabilitarBotao();
            return;
        }
        inputDataNascimento.setDisable(false);
        habilitarBotao();
    }

    @FXML
    public void inputDataNascimentoHandler(ActionEvent event)  {
        if(inputDataNascimento.getValue() == null) return;
        Stage modal = getStage(event);
        LocalDate limite = LocalDate.now().minusYears(18);
        if(inputDataNascimento.getValue().isAfter(limite)) {
            modal.setAlwaysOnTop(false);
            new Alerta("O hospede n??o pode ser menor de idade.").aviso();
            modal.setAlwaysOnTop(true);
            inputDataNascimento.setValue(hospedeData.getDataNascimento());
            desabilitarBotao();
            return;
        }
        inputTelefone.setDisable(false);
        habilitarBotao();
    }

    @FXML
    public void inputTelefoneHandler(KeyEvent event) {
        if(inputTelefone.getText().length() < 10) {
            desabilitarBotao();
            return;
        }
        inputNacionalidade.setDisable(false);
        habilitarBotao();
    }

    @FXML
    public abstract void inputNacionalidadeHandler(ActionEvent event);

    public abstract void desabilitarBotao();

    public abstract void habilitarBotao();

    private void loadListaNacionalidades(){
        String[] nacionalidades = new String[]{"alem??o", "andorrano", "angolano", "antiguano", "saudita", "argelino", "argentino", "arm??nio", "australiano", "austr??aco", "azerbaijano", "bahamense", "banglad??s, bangladense", "barbadiano", "bahreinita", "belga", "belizenho", "benin??s", "belarusso", "boliviano", "b??snio", "botsuan??s", "brasileiro", "brune??no", "b??lgaro", "burkineonse, burkinab??", "burund??s", "butan??s", "cabo-verdiano", "cameroun??s", "cambojano", "catariano", "canadense", "cazaque", "chadiano", "chileno", "chin??s", "cipriota", "colombiano", "comoriano", "congol??s", "congol??s", "sul-coreano", "norte-coreano", "costa-marfinense, marfinense", "costa-ricense", "croata", "cubano", "dinamarqu??s", "djiboutiano", "dominiquense", "eg??pcio", "salvadorenho", "emiradense, emir??tico", "equatoriano", "eritreu", "eslovaco", "esloveno", "espanhol", "estadunidense, (norte-)americano", "estoniano", "et??ope", "fijiano", "filipino", "finland??s", "franc??s", "gabon??s", "gambiano", "gan??s ou ganense", "georgiano", "granadino", "grego", "guatemalteco", "guian??s", "guineense", "guineense, bissau-guineense", "equato-guineense", "haitiano", "hondurenho", "h??ngaro", "iemenita", "cookiano", "marshall??s", "salomonense", "indiano", "indon??sio", "iraniano", "iraquiano", "irland??s", "island??s", "34", "jamaicano", "japon??s", "jordaniano", "kiribatiano", "kuwaitiano", "laosiano", "lesotiano", "let??o", "liban??s", "liberiano", "l??bio", "liechtensteiniano", "lituano", "luxemburgu??s", "maced??nio", "madagascarense", "mal??sio37", "malawiano", "maldivo", "maliano", "malt??s", "marroquino", "mauriciano", "mauritano", "mexicano", "myanmarense", "micron??sio", "mo??ambicano", "moldovo", "monegasco", "mongol", "montenegrino", "namibiano", "nauruano", "nepal??s", "nicaraguense", "nigerino", "nigeriano", "niuiano", "noruegu??s", "neozeland??s", "omani", "neerland??s", "palauano", "palestino", "panamenho", "papua, papu??sio", "paquistan??s", "paraguaio", "peruano", "polon??s, polaco", "portugu??s", "queniano", "quirguiz", "brit??nico", "centro-africano", "tcheco", "dominicano", "romeno", "ruand??s", "russo", "samoano", "santa-lucense", "s??o-cristovense", "samarin??s", "santomense", "s??o-vicentino", "seichelense", "senegal??s", "s??rvio", "singapurense", "s??rio", "somaliano, somali", "sri-lank??s", "su??zi", "sudan??s", "sul-sudan??s", "sueco", "su????o", "surinam??s", "tajique", "tailand??s", "tanzaniano", "timorense", "togol??s", "tongan??s", "trinit??rio", "tunisiano", "turcomeno", "turco", "tuvaluano", "ucraniano", "ugand??s", "uruguaio", "uzbeque", "vanuatuense", "vaticano", "venezuelano", "vietnamita", "zambiano", "zimbabueano"};
        inputNacionalidade.setItems(FXCollections.observableArrayList(nacionalidades));
    }

    protected void setHospedeData() {
        hospedeData.setNome(inputNome.getText());
        hospedeData.setSobrenome(inputSobrenome.getText());
        hospedeData.setDataNascimento(inputDataNascimento.getValue());
        hospedeData.setTelefone(inputTelefone.getText());
        hospedeData.setNacionalidade(inputNacionalidade.getValue());
    }

    public Stage getStage (Event event) {
        Node source = (Node) event.getSource();
        return (Stage) source.getScene().getWindow();
    }
}
