import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**Klasa Game dziedziczy po view i odpowiada za wszystko co wyswietla sie na ekranie podczas rozgrywki*/
public class Game extends View
{
    /**Przycisk do wyjscia do ustawien*/
    private JButton settingsButton;
    /**Przycisk do zrestartowania gry*/
    private JButton restartButton;
    /**Wyswietla ile min zostalo*/
    private JLabel minesLeft;

    /**Pole przyciskow*/
    private JButton[][] buttonField;
    /**Pole wartosci (odslaniaja sie po wcisnieciu danego przycisku)*/
    private JLabel[][] valueField;
    /**Pole par przycisk - wartosc*/
    private JPanel[][] panelField;

    /**Pole zbierajace wszystkie pola paska opcji*/
    private JPanel options;

    /**Wymiary pojedynczego kwadratu*/
    private int fieldSide;
    /**Wysokosc pola min*/
    private int fieldWidth;
    /**Szerokosc pola min*/
    private int fieldHeight;
    /**Ilosc min pozostalych do zaflagowania*/
    private int minesLeftCounter;

    /**Tablica przeskalowanych obrazkow
     * 0-8 ilosc min przyleglych
     * 9 mina
     * 10 mina kliknieta
     * 11 flaga
     * 12 zle postawiona flaga
     * 13 znak zapytania
     */
    private ImageIcon[] imageIcons;


    /**Konstruktor rysuje pole oraz opcje
     *
     * @param width szerokosc pola
     * @param height wysokosc pola
     * @param mines miny, ktore zostaly do zaflagowania
     */
    Game( int width, int height, int mines )
    {
        fieldWidth = width;
        fieldHeight = height;
        minesLeftCounter = mines;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        countFieldSide( dimension.width * 3 / 4, dimension.height * 3 / 4);
        resizeAllImages();

        this.frame.setSize( fieldSide * fieldWidth + 120, fieldSide * fieldHeight );  // 24 px na pole i 120 na przyciski
        this.frame.setLayout( new BorderLayout() );

        drawField();
        drawOptions( mines );
        this.frame.setTitle( "Gra" );
        this.frame.setVisible( true );
    }

    /**Funkcja rysuje pole minowe
     */
    private void drawField()
    {
        JPanel field = new JPanel( new GridLayout( fieldHeight, fieldWidth ) );
        field.setPreferredSize( new Dimension( fieldSide * fieldWidth, fieldSide * fieldHeight ) );

        buttonField = new JButton[ fieldWidth ][ fieldHeight ];
        panelField = new JPanel[ fieldWidth ][ fieldHeight ];

        for( int i = 0; i < fieldHeight; ++i ){
            for( int j = 0; j < fieldWidth; ++j ){
                panelField[ j ][ i ] = new JPanel( new GridLayout() );
                buttonField[ j ][ i ] = new JButton();
                panelField[ j ][ i ].add( buttonField[ j ][ i ] );  //Do panelu dodajemy guzik
                field.add( panelField[ j ][ i ] );                  //Do pola calego dodajemy pojedynczy panel

            }
        }
        this.frame.add( field, BorderLayout.CENTER );
    }

    /**Funkcja rysuje opcje dostepne obok pola minowego*/
    private void drawOptions( int mines )
    {
        options = new JPanel( new GridLayout( fieldHeight / 2, 1 ) );
        options.setPreferredSize( new Dimension( 120, fieldHeight ) );
        restartButton = new JButton( "Od nowa" );
        options.add( restartButton );
        settingsButton = new JButton( "Ustawienia" );
        options.add( settingsButton );
        minesLeft = new JLabel( mines + " min pozostalo" );
        options.add( minesLeft );
        this.frame.add( options, BorderLayout.LINE_END );
    }

    /**Funkcja rysuje odpowiednie obrazki (miny lub numerki) pod przyciskami
     *
     * @param values tablica wartosci ktora zawiera informacje o tym, co znajduje cie na danym polu
     */
    public void drawLabelField( int [][] values )
    {
        valueField = new JLabel[ fieldWidth ][ fieldHeight ];
        for( int i = 0; i < fieldHeight; ++i ){
            for( int j = 0; j < fieldWidth; ++j ){
                valueField[ j ][ i ] = new JLabel();
                valueField[ j ][ i ].setIcon( imageIcons[ values[ j ][ i ] ] );
            }
        }
    }

    /**Funkcja ustawia obrazek pod danym przyciskiem
     *
     * @param x wspolrzedna x pola
     * @param y wspolrzedna y pola
     * @param imageNumber numer obrazka z imageIcons
     */
    public void setImageLabel( int x, int y, int imageNumber )
    {
        valueField[ x ][ y ].setIcon( imageIcons[ imageNumber ] );
    }

    /**Funkcja usuwa dany przycisk wyświetla zamiast niego obrazek spod przycisku
     *
     * @param x wspolrzedna x przycisku
     * @param y wspolrzedna y przycisku
     */
    void hideButton( int x, int y )
    {
        buttonField[ x ][ y ].setVisible( false );
        panelField[ x ][ y ].remove( buttonField[ x ][ y ] );
        panelField[ x ][ y ].add( valueField[ x ][ y ] );
        valueField[ x ][ y ].setVisible( true );
    }

    /**Funkcja (wywolywana w momencie klikniecia w mine) pokazuje wszystkie niezaflagowane miny i pokazuje zle postawione flagi
     *
     * @param minesField tablica z informacjami o polozeniu min
     * @param displayed tablica z informacjami o postawionych flagach
     */
    void openAllMines( int[][] minesField, int[][] displayed )
    {
        for( int i = 0; i < fieldHeight; ++i ){
            for( int j = 0; j < fieldWidth; ++j ){
                if( minesField[ j ][ i ] == 9 && displayed[ j ][ i ] != 1 )
                    hideButton( j, i );
                else if( minesField[ j ][ i ] != 9 && displayed[ j ][ i ] == 1 )
                    buttonField[ j ][ i ].setIcon( imageIcons[ 12 ] );
            }
        }
    }

    /**Funkcja sprawdza ktory guzik zostal wcisniety jesli otrzymalismy dany hash
     *
     * @param hash unikalna wartosc wcisnietego guzika
     * @return wspolrzedne wcisnietego guzika ( -1 gdy restart, -2 gdy settings )
     */
    int[] buttonHash( int hash )
    {
        int[] cords = { -1, -1 };
        if( restartButton.hashCode() == hash )
            return cords;
        if( settingsButton.hashCode() == hash ){
            cords[ 0 ] = -2;
            cords[ 1 ] = -2;
            return cords;
        }
        for( int i = 0; i < fieldHeight; ++i ){
            for( int j = 0; j < fieldWidth; ++j ){
                if( buttonField[ j ][ i ].hashCode() == hash ){
                    cords[ 0 ] = j;
                    cords[ 1 ] = i;
                    return cords;
                }
            }
        }
        return cords;
    }

    /**Funkcja dodaje sluchacza na kazdym guziku*/
    void listener( MouseListener action )
    {
        restartButton.addMouseListener( action );
        settingsButton.addMouseListener( action );
        for( int i = 0; i < fieldHeight; ++i ){
            for( int j = 0; j < fieldWidth; ++j )
                buttonField[ j ][ i ].addMouseListener( action );
        }
    }

    /**Funckja (wywolywana przy zakonczeniu gry) usuwa sluchaczy z pola minowego tak aby mozliwe bylo klikniecie tylko restartu badz ustawien*/
    void removeButtonListeners( MouseListener action )
    {
        for( int i = 0; i < fieldHeight; ++i ){
            for( int j = 0; j < fieldWidth; ++j )
                buttonField[ j ][ i ].removeMouseListener( action );
        }
    }

    /**Funkcja ustawia na przycisku flage, znak zapytania badz usuwa z niego obrazek
     *
      * @param value obrazek ktory ma zostac ustawiony
     * @param x wspolrzedna x przycisku
     * @param y wspolrzedna y przycisku
     */
    void setImageButton( int value, int x, int y )
    {
        switch( value )
        {
            case 0:
                buttonField[ x ][ y ].setIcon( null );
                break;
            case 1:
                buttonField[ x ][ y ].setIcon( imageIcons[ 11 ] );
                break;
            case 2:
                buttonField[ x ][ y ].setIcon( imageIcons[ 13 ] );
                break;
        }
    }

    /**Funkcja zwraca przeskalowany obrazek
     *
     * @param name sciezka do obrazka
     * @param width docelowa szerokosc
     * @param height docelowa wysokosc
     * @return przeskalowany obrazek
     */
    private ImageIcon getResizedImage( String name, int width, int height )
    {
        return new ImageIcon( new ImageIcon( name ).getImage().getScaledInstance( width, height, Image.SCALE_DEFAULT ) );
    }

    /**Funkcja (wywolywana przy zwyciestwie) ustawia na bocznym panelu obrazek informujacy o zwyciestwie
     *
     * @param image sciezka do obrazka
     */
    void setEndImage( String image )
    {
        options.add( new JLabel( new ImageIcon( image ), SwingConstants.CENTER ) );
    }

    /**Funkcja zmienia ilosc min pozostalych do zaflagowania i odswierza informacje na panelu bocznym
     *
     * @param a wartosc, o ktora zmienia sie licznik min
     */
    void addMinesLeft( int a )
    {
        minesLeftCounter += a;
        minesLeft.setText( minesLeftCounter + " min pozostalo" );
    }

    /**Funkcja zwraca ilosc min niezaflagowanych
     *
     * @return ilosc min niezaflagowanych
     */
    int getMinesLeft(){ return minesLeftCounter; }

    /**Funkcja oblicza rozmiar pojedynczego kwadratu w zaleznosci od rozdzielczosci ekranu i wymiarow pola
     *
     * @param windowWidth szerokosc ekranu w px
     * @param windowHeight wysokosc ekranu w px
     */
    private void countFieldSide( int windowWidth, int windowHeight )
    {
        if( fieldWidth / fieldHeight > windowWidth / windowHeight )
            fieldSide = windowWidth / fieldWidth;
        else
            fieldSide = windowHeight / fieldHeight;
        if( fieldSide < 20 )
            fieldSide = 20;
        else if( fieldSide > 40 )
            fieldSide = 40;

    }

    /**Funkcja wywoluje funkcje skalujaca obrazek dla kazdego obrazka i umieszcza ja w tablicy przeskalowanych obrazkow*/
    private void resizeAllImages()
    {
        imageIcons = new ImageIcon[ 14 ];
        for( int i = 0; i < 14; ++i )
            imageIcons[ i ] = getResizedImage( "img/" + i + ".png", fieldSide, fieldSide );
    }
}
