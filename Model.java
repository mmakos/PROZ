import java.util.Random;
import static java.lang.Math.abs;

/**Klasa Model przechowuje wszystkie dane o rozgrywce oraz metody, które dzialaja na tych danych*/
class Model
{
    /**Szerokosc pola minowego*/
    private int fieldWidth;
    /**Wysokosc pola minowego*/
    private int fieldHeight;
    /**Procentowa ilosc min (wpisana w ustawieniach)*/
    private int minesPercent;
    /**Wlasciwa ilosc min*/
    private int minesQuantity;
    /**Ilosc pol poprawnie odkrytych (potrzebna do ustalenia kiedy gra wygrana)*/
    private int fieldsOpened;
    /**Informacja, czy miny zostaly juz obsadzone*/
    private boolean fieldDrawed;

    /**Tablica przechowuje obecny stan pola: 0 - niewcisniety, 1 - flaga, 2 - znak zapytania*/
    private int[][] displayed;
    /**Tablica przechowuje informacje czy na danym polu znajduje sie mina, lub z jaka iloscia min dane pol graniczy
     * 0-8 ilosc min graniczacych z polem
     * 9 mina
     */
    private int[][] minesField;

    /**Konstruktor tworzy model, nowe pole minowe, jeszcze niewypelnione
     *
     * @param width szerokosc tworzonego pola
     * @param height wysokosc tworzonego pola
     * @param mines ilosc procentowa min
     */
    Model( int width, int height, int mines )
    {
        fieldWidth = width;
        fieldHeight = height;
        minesPercent = mines;
        fieldDrawed = false;

        displayed = new int[ fieldWidth ][ fieldHeight ];
        for( int i = 0; i < fieldHeight; ++i ){          //zerujemy tablice
            for( int j = 0; j < fieldWidth; ++j ){
                displayed[ j ][ i ] = 0;
            }
        }
    }

    /**Funkcja zeruje odpowiednie wartosci dla nowej gry*/
    void newModel()
    {
        fieldsOpened = 0;
        fieldDrawed = false;

        displayed = new int[ fieldWidth ][ fieldHeight ];
        for( int i = 0; i < fieldHeight; ++i ){          //zerujemy tablice
            for( int j = 0; j < fieldWidth; ++j ){
                displayed[ j ][ i ] = 0;
            }
        }
    }

    /**funkcja ustawia, zostawia, inkrementuje badz dekrementuje szerokosc
     *
     * @param width szerokosc do ustawienia, -1 inkremetuje, -2 dekrementuje
     * @return szerokosc ktora pojawi sie w polu tekstowym
     */
    int setWidth( int width )
    {
        if( ( fieldWidth >= 50 && width == -1 ) || ( fieldWidth <= 8 && width == -2 ) || width == 0 )
            return fieldWidth;
        if( width == -1 )
            return ++fieldWidth;
        if( width == -2 )
            return --fieldWidth;
        fieldWidth = width;
        return fieldWidth;
    }
    /**funkcja ustawia, zostawia, inkrementuje badz dekrementuje wysokosc
     *
     * @param height wysokosc do ustawienia, -1 inkremetuje, -2 dekrementuje
     * @return wysokosc ktora pojawi sie w polu tekstowym
     */
    int setHeight( int height )
    {
        if( ( fieldHeight >= 50 && height == -1 ) || ( fieldHeight <= 8 && height == -2 ) || height == 0 )
            return fieldHeight;
        if( height == -1 )
            return ++fieldHeight;
        if( height == -2 )
            return --fieldHeight;
        fieldHeight = height;
        return fieldHeight;
    }
    /**funkcja ustawia, zostawia, inkrementuje badz dekrementuje ilosc min
     *
     * @param mines ilosc min do ustawienia, -1 inkremetuje, -2 dekrementuje
     * @return ilosc min ktora pojawi sie w polu tekstowym
     */
    int setMines( int mines )
    {
        if( ( minesPercent >= 80 && mines == -1 ) || ( minesPercent <= 10 && mines == -2 ) || mines == 0 )
            return minesPercent;
        if( mines == -1 )
            return ++minesPercent;
        if( mines == -2 )
            return --minesPercent;
        minesPercent = mines;
        return minesPercent;
    }

    /**funkcja przelicza procentowa ilosc min na faktyczna, jednostkowa*/
    int countMinesQuantity()
    {
        minesQuantity = minesPercent * fieldHeight * fieldWidth / 100;
        return minesQuantity;
    }

    /**funkcja losowo rozklada miny na polu i oblicza wartosc wszystkich pozostalych pol
     *
     * @param x wspolrzedna kliknietego punktu na ktorym musi byc 0
     * @param y wspolrzedna kliknietego punktu na ktorym musi byc 0
     * @return zwraca wypelnione pole minowe
     */
    int[][] putMines( int x, int y )
    {
        minesField = new int[ fieldWidth ][ fieldHeight ];
        int minesLeft = countMinesQuantity();
        int size = fieldHeight * fieldWidth;
        int cord, cordx, cordy;
        Random rand = new Random();

        while( minesLeft != 0 ){
            cord = rand.nextInt( size );
            cordx = cord % fieldWidth;
            cordy = cord / fieldWidth;
            if( ( abs( cordx - x ) > 1 || abs( cordy - y ) > 1 ) && minesField[ cordx ][ cordy ] != 9 ){         //kiedy nie graniczy z kliknietym polem
                minesField[ cordx ][ cordy ] = 9;                       //9 to mina

                for( int i = cordy - 1; i <= cordy + 1; ++i ){          //Petla inkrementuje pola dookola wstawionej miny
                    for( int j = cordx - 1; j <= cordx + 1; ++j ){
                        if( i >= 0 && j >= 0 && i < fieldHeight && j < fieldWidth && minesField[ j ][ i ] < 9 )
                            ++minesField[ j ][ i ];
                    }
                }
                --minesLeft;
            }
        }
        fieldDrawed = true;
        return minesField;
    }

    /**Funkcja odkrywa guzik, sprawdza co jest pod nim i generuje odpowiednie zdarzenia
     * 0 - trafiono na mine
     * 1 - trafiono na pole
     * 2 - wszystkie pola odsloniete
     * -3 - pierwsze klikniecie, pole jeszcze nieobsadzone minami
     * -2 - do menu
     * -1 - retart
     *
     * @param x wspolrzedna x kliknietego guzika
     * @param y wspolrzedna y kliknietego guzika
     * @return zwraca kod zdarzenia
     */
    int clickButton( int x, int y, View view )
    {
        if( x == -2 && y == -2 )
            return -2;
        if( x == -1 && y == -1 )
            return -1;
        if( !fieldDrawed )
            return -3;
        if( minesField[ x ][ y ] == 9 ){
            view.setImageLabel( x, y, 10 );
            view.openAllMines( minesField, displayed );
            return 0;
        }
        if( minesField[ x ][ y ] >= 0 && minesField[ x ][ y ] < 9 ){
            openFields( x, y, view );
            if( fieldsOpened >= fieldWidth * fieldHeight - minesQuantity )
                return 2;
            return 1;
        }
        return 1;
    }

    /**Otwiera rekurencyjnie dane pole i jesli jest zerem to wszystkie dookola
     *
     * @param x wspolrzedna x pola odkrywanego
     * @param y wspolrzedna y pola odkrywanego
     * @param view obiekt klasy view
     */
    private void openFields( int x, int y, View view )
    {
        if( x < 0 || x >= fieldWidth || y < 0 || y >= fieldHeight || minesField[ x ][ y ] >= 9 )
            return;
        if( minesField[ x ][ y ] != 0 ){
            view.hideButton( x, y );
            minesField[ x ][ y ] = 10;
            ++fieldsOpened;
            return;
        }
        minesField[ x ][ y ] = 10;
        view.hideButton( x, y );
        ++fieldsOpened;
        openFields( x - 1, y - 1, view );
        openFields( x - 1, y, view );
        openFields( x - 1, y + 1, view );
        openFields( x, y - 1, view );
        openFields( x, y + 1, view );
        openFields( x + 1, y - 1, view );
        openFields( x + 1, y, view );
        openFields( x + 1, y + 1, view );
    }

    /**Funkcja ustawia odpowiednia wartosc pola, ktore oznacza co sie wyswietla na guziku
     *
     * @param value watrosc
     * @param x wspolrzedna x pola
     * @param y wspolrzedna y pola
     */
    void setDisplayed( int value, int x, int y )
    {
        displayed[ x ][ y ] = value;
    }

    /**Funckja zwraca wartosc pola, ktore oznacza co sie wyswietla na guziku
     *
     * @param x wspolrzedna x pola
     * @param y wspolrzedna y pola
     * @return wartosc pola
     */
    int getDisplayed( int x, int y )
    {
        if( x >= 0 && y >= 0 )
            return displayed[ x ][ y ];
        return -1;
    }
}