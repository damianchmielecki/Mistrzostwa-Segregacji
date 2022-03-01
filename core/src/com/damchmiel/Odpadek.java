package com.damchmiel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;

public class Odpadek {
    private Texture grafika;
    private GridPoint2 pozycja;
    private String rodzaj;
    private int h=100, w=100;
    public int spadanie = 10;

    /**Deklaracja odpadka
     * @param rodzaj Rodzaj odpadka, bedzkie decydujacy jesli chodzi o poprawna odpowiedz
     * @param grafika Parametr grafiki dla odpadku
     * @param pozycja Parametr pozycji dla odpadka
     */
    public Odpadek(String rodzaj, Texture grafika, GridPoint2 pozycja){
        this.rodzaj = rodzaj;
        this.grafika = grafika;
        this.pozycja = new GridPoint2(pozycja);
    }

    /**
     * Funkcja odpowiadajaca za wyswietlenie odpadku
     */
    public void render(SpriteBatch batch){
        batch.draw(grafika, pozycja.x, pozycja.y, w,h);
    }

    /**
     *Funkcja wylywająca na ruch odpadka, nie tylko w dol ale rowniez wprowadza zmiany pozycji podczas wykrycia naciskania klawiszy
     */
    public void update (float deltaTime, int przyspieszenie) {
        int tu_przyspieszenie = 100;
        if(przyspieszenie == 1) tu_przyspieszenie = 1;
        if(przyspieszenie == 2) tu_przyspieszenie = 100;
        if(przyspieszenie == 3) tu_przyspieszenie = 150;

        pozycja.y -= tu_przyspieszenie * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && pozycja.x < 1180){
            pozycja.x += spadanie;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && pozycja.x > 0){
            pozycja.x -= spadanie;
        }
    }

    /**Funkcja sprawdza poprawnosc podjetej decyzji jesli chodzi o poprawnosc segregacji danego odpadka
     * @return zwraca informacje o poprawnosci podjetej decyzji podczas gry
     */
    public boolean sprawdzenie (Odpadek odpadek,float deltaTime) {
        if ((pozycja.x > 680) && (pozycja.x < 800) && odpadek.getRodzaj() == "plastik" && pozycja.y < 380 && pozycja.y > 350){
            return true;
        } else if((pozycja.x > 970) && (pozycja.x < 1090) && odpadek.getRodzaj() == "inne" && pozycja.y < 380 && pozycja.y > 350){
            return true;
        } else if((pozycja.x > 100) && (pozycja.x < 240) && odpadek.getRodzaj() == "papier" && pozycja.y < 380 && pozycja.y > 350){
            return true;
        } else if((pozycja.x > 380) && (pozycja.x < 530) && odpadek.getRodzaj() == "szklo" && pozycja.y < 380 && pozycja.y > 350){
            return true;
        } else return false;
    }

    /**
     * @return zwraca rodzaj odpadku
     */
    public String getRodzaj (){

        return rodzaj;
    }
}
