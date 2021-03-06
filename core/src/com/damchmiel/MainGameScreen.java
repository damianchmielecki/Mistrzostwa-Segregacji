package com.damchmiel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;

/**
 * Jest to klasa gry gdy gra jest uruchamiana.
 * Znajdują się w niej podstawowe deklaracje dla tekstur czy listy odpadków.
 */
public class MainGameScreen implements Screen {
    Segregacja game;
    /**
     * Lista obiektów ktore będą spadac z gornej czesci ekranu.
     */
    public ArrayList <Odpadek> OdpadekBank;
    Texture panel_dolny, tlo, multiTexture, przycisk_start, odp_tak, odp_nie, kwadrat, przycisk_zasady, zasady;
    Texture przycisk_0, przycisk_minus_1, przycisk_1, przycisk_szybciej, przycisk_wolniej;
    TextureRegion s_niebieski, s_zielony, s_zolty, s_inny;
    static int s_szerokosc = 140, s_wysokosc = 200, licznik = 0 , praw_pozycja = 1024, i = 0, szybkosc = 0;
    static int przyspieszenie = 2;
    public Odpadek odpadek;
    GridPoint2 pozycja;
    boolean punkt = true, pokaz = true, start = false, nowa_gra = true;
    public MainGameScreen (Segregacja game){
        this.game = game;
        OdpadekBank = new ArrayList<>();
        fillOdpadekBank(OdpadekBank);
    }

    @Override
    public void show () {
        panel_dolny = new Texture("beton.jpg");
        tlo = new Texture("wall.jpg");
        multiTexture = new Texture("layer1-8.png");
        s_niebieski = new TextureRegion(multiTexture,0,0,920,1375);
        s_zielony = new TextureRegion(multiTexture,2040,0,920,1325);
        s_zolty = new TextureRegion(multiTexture,1020,0,920,1325);
        s_inny = new TextureRegion(multiTexture,3060,0,920,1325);
        przycisk_start = new Texture ("przycisk_start.png");
        przycisk_szybciej = new Texture("przycisk_szybciej.png");
        przycisk_wolniej = new Texture("przycisk_wolniej.png");
        przycisk_minus_1 = new Texture("przycisk_-1.png");
        przycisk_0 = new Texture("przycisk_0.png");
        przycisk_1 = new Texture("przycisk_1.png");
        odp_tak = new Texture("yes.png");
        odp_nie = new Texture("no.png");
        kwadrat = new Texture("kwadrat.png");
        przycisk_zasady = new Texture("przycisk_zasady.png");
        zasady = new Texture("zasady.png");
    }

    @Override
    public void render (float delta) {
        game.batch.begin();
        game.batch.draw(tlo,0,0,1280,1024);
        game.batch.draw(panel_dolny,0,0,1280,200);
        game.batch.draw(s_niebieski,144,200, s_szerokosc,s_wysokosc);
        game.batch.draw(s_zielony, 428,200, s_szerokosc,s_wysokosc);
        game.batch.draw(s_zolty,712,200,s_szerokosc,s_wysokosc);
        game.batch.draw(s_inny,996,200,s_szerokosc,s_wysokosc);
        game.batch.draw(przycisk_start,900,60);
        if(szybkosc == 0) game.batch.draw(przycisk_0,600,60);
        if(szybkosc == -1) game.batch.draw(przycisk_minus_1,600,60);
        if(szybkosc == 1) game.batch.draw(przycisk_1,600,60);
        game.batch.draw(przycisk_szybciej,700,60);
        game.batch.draw(przycisk_wolniej,500,60);
        game.batch.draw(kwadrat,300,60,90,90);
        game.batch.draw(przycisk_zasady,60,60);
        if(pokaz == true) game.batch.draw(zasady, 180,500,900,450);

        if(start == true){
            nowa_gra = false;
            if (licznik == i && licznik < 7){
                Odpadek o = OdpadekBank.get(i);
                praw_pozycja -= 1 * przyspieszenie ;
                renderOdpadek(o, delta, przyspieszenie, start);
                if( 370 > praw_pozycja && praw_pozycja > 350){
                    if (o.sprawdzenie(o, delta) == true ){
                        punkt = true;
                    } else {
                        punkt = false;
                    }
                    licznik ++;
                    i++;
                    praw_pozycja = 1024;
                }
            }
        }
        if (start == false){
            Odpadek o = OdpadekBank.get(i);
            renderOdpadek(o, delta, przyspieszenie, start);
        }
        if(punkt == true && (licznik > 0 || nowa_gra == true)){
            game.batch.draw(odp_tak,300,60, 90,90);
        }
        if(punkt == false && (licznik > 0 || nowa_gra == true)) {
            game.batch.draw(odp_nie, 300, 60, 90, 90);
        }

        if(start == true && licznik == 7){
            start = false;
            licznik = 0;
            for(int x = 0; x!=i;x++){
                OdpadekBank.remove(0);
            }
            i = 0;
            fillOdpadekBank(OdpadekBank);
            randomizeMyOdpadki(OdpadekBank);
            nowa_gra = true;
        }

        if(60 < 1024 - Gdx.input.getY() && 1024 - Gdx.input.getY() < 150 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && 700 < Gdx.input.getX() && Gdx.input.getX() < 790){
            if (szybkosc == -1){
                szybkosc++;
                przyspieszenie = 2;
            }else if (szybkosc == 0){
                szybkosc++;
                przyspieszenie = 3;
            }
        }
        if(60 < 1024 - Gdx.input.getY() && 1024 - Gdx.input.getY() < 150 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && 500 < Gdx.input.getX() && Gdx.input.getX() < 590){
            if (szybkosc == 1){
                szybkosc--;
                przyspieszenie = 2;
            } else if(szybkosc == 0){
                szybkosc--;
                przyspieszenie = 1;
            }
        }
        if(60 < 1024 - Gdx.input.getY() && 1024 - Gdx.input.getY() < 150 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && 900 < Gdx.input.getX() && Gdx.input.getX() < 1150){
            if(start == false)  start = true;
            else if (start == true)  start = false;
        }
        if(60 < 1024 - Gdx.input.getY() && 1024 - Gdx.input.getY() < 150 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && 60 < Gdx.input.getX() && Gdx.input.getX() < 260){
            if (pokaz == false){
                pokaz = true;
            } else if (pokaz == true) pokaz = false;
        }
        if(850 < 1024 - Gdx.input.getY() && 1024 - Gdx.input.getY() < 950 && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && 980 < Gdx.input.getX() && Gdx.input.getX() < 1070 ){
            pokaz = false;
        }
        game.batch.end();
    }

    /** Funkcja odpowiadajaca za wyswietlenie obiektu odpadek zwiazanego z klasa Odpadek
     * Funkcja zawiera rowniez odniesienie do fukncji zwiazanej z ruchem obiektu
     * @param odpadek element zwiazany z klasa Odpadek posiadajace unikatowe wartosci
     * @param delta parametr powiazany z czasem gry
     * @param przyspieszenie paratetr zwiazny z szybkoscia spadania odpadkow
     * @param start parametr informujacy o tym czy gra jest w zamrozeniu czy jednak dziala
     */
    private void renderOdpadek(Odpadek odpadek, float delta, int przyspieszenie, boolean start) {
        odpadek.render(game.batch);
        if(start == true){
            odpadek.update(delta, przyspieszenie);
        }
    }

    @Override
    public void resize (int width, int height) {

    }

    @Override
    public void pause () {

    }

    @Override
    public void resume () {

    }

    @Override
    public void hide () {

    }

    @Override
    public void dispose () {

    }

    /**
     * Funkcja odpowiadajaca za losowosc odpadkow ktore beda sie generowac na podstawie listy
     */
    private void randomizeMyOdpadki (ArrayList<Odpadek> bank){
        for (int i =0; i < 7; i++){
                int wybrany = wyb_poz(i);
            Odpadek o = OdpadekBank.get(wybrany);
            OdpadekBank.remove(wybrany);
        }
    }

    /**
     * Funkcja odpowiadajaca za wypelnienie listy konkretnymi odpadkami
     */
    public void fillOdpadekBank (ArrayList<Odpadek> odpadkiBank){

        odpadkiBank.add(createOdpadek("butelka"));
        odpadkiBank.add(createOdpadek("mleko"));
        odpadkiBank.add(createOdpadek("gazeta"));
        odpadkiBank.add(createOdpadek("karton"));
        odpadkiBank.add(createOdpadek("kosc"));
        odpadkiBank.add(createOdpadek("puszka"));
        odpadkiBank.add(createOdpadek("szklana_butelka"));
        odpadkiBank.add(createOdpadek("kaczka"));
        odpadkiBank.add(createOdpadek("waza"));
        odpadkiBank.add(createOdpadek("torba"));
        odpadkiBank.add(createOdpadek("jar"));
        odpadkiBank.add(createOdpadek("bukiet"));
        odpadkiBank.add(createOdpadek("origami"));
        odpadkiBank.add(createOdpadek("wino"));
        odpadkiBank.add(createOdpadek("apple"));
        return;
    }

    /**
     * @param value dzieki temu parametrowi algorytm wie jakie grafiki przypisac danemu odpadkowi
     * @return zwraca zapelniona liste odpadkow z przypisanymi wszystkimi wartosciami jak grafika czy polozenie
     */
    public Odpadek createOdpadek (String value){
        int los_pozycja = los_poz();
        pozycja = new GridPoint2(los_pozycja,1024);
        String imgName = "";
        String rodzaj = value;
        if(value.equals("butelka")){
            imgName = "butelka_plastikowa.png";
            rodzaj = "plastik";
        }else if (value.equals("mleko")){
            imgName = "mleko.png";
            rodzaj = "plastik";
        } else if(value.equals("gazeta")){
            imgName = "gazeta.png";
            rodzaj = "papier";
        } else if(value.equals("karton")){
            imgName = "karton.png";
            rodzaj = "papier";
        } else if(value.equals("kosc")){
            imgName = "kosc.png";
            rodzaj = "inne";
        } else if(value.equals("puszka")) {
            imgName = "puszka.png";
            rodzaj = "plastik";
        } else if(value.equals("szklana_butelka")) {
            imgName = "szklana_butelka.png";
            rodzaj = "szklo";
        } else if(value.equals("kaczka")) {
            imgName = "kaczka.png";
            rodzaj = "plastik";
        } else if(value.equals("waza")) {
            imgName = "waza.png";
            rodzaj = "inne";
        } else if(value.equals("torba")) {
            imgName = "torba.png";
            rodzaj = "papier";
        } else if(value.equals("jar")) {
            imgName = "jar.png";
            rodzaj = "szklo";
        } else if(value.equals("bukiet")) {
            imgName = "bukiet.png";
            rodzaj = "inne";
        } else if(value.equals("origami")) {
            imgName = "origami.png";
            rodzaj = "papier";
        } else if(value.equals("wino")) {
            imgName = "wino.png";
            rodzaj = "szklo";
        } else if(value.equals("apple")) {
            imgName = "apple.png";
            rodzaj = "inne";
        }
        Texture grafika = new Texture(imgName);
        Odpadek odpadek = new Odpadek(rodzaj, grafika, pozycja);
        return odpadek;
    }

    /**
     * @return parametr losowania pozycji dla wybranego odpadka
     */
    public int los_poz (){
        int losowaPoz = MathUtils.random.nextInt(824);
        return losowaPoz;
    }

    public int wyb_poz (int i) {
        int wyb = MathUtils.random.nextInt(15 - i);
        return wyb;
    }

}
