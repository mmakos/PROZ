import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import static java.lang.Integer.parseInt;

/**Klasa Settings przedstawia wyglad i funkje menu uzytkownika, ktore ukazuje sie po wlaczeniu gry badz po wlaczeniu menu przez uzytkownika.
 * Wyglada jak to w Linuxie tylko bez okna do wyboru standardowych opcji
 * @author Michal Makos
 */
public class Settings extends View
{
    GridBagConstraints gbc = new GridBagConstraints();

    /**Panel zawierajacy ustawienia min*/
    JPanel minesPanel;
    /**Panel zawierajacy ustawienia szerokosci*/
    JPanel widthPanel;
    /**Panel zawierajacy ustawienia wysokosci*/
    JPanel heightPanel;

    /**Pole wyswietlajace napis "ilosc min"*/
    JLabel minesLabel;
    /**Pole wyswietlajace napis "szerokosc"*/
    JLabel widthLabel;
    /**Pole wyswietlajace napis "wysokosc"*/
    JLabel heightLabel;

    /**Pole do ustawienia liczby min*/
    JTextField minesField;
    /**Pole do ustawienia szerokosci planszy*/
    JTextField widthField;
    /**Pole do ustawienia wysokosci planszy*/
    JTextField heightField;

    /**Strzalka do zwiekszania liczby min*/
    BasicArrowButton minesUpArrow;
    /**Strzalka do zmniejszania liczby min*/
    BasicArrowButton minesDownArrow;
    /**Strzalka do zwiekszania wysokosci pola*/
    BasicArrowButton heightUpArrow;
    /**Strzalka do zmniejszania wysokosci pola*/
    BasicArrowButton heightDownArrow;
    /**Strzalka do zwiekszania szerokosci pola*/
    BasicArrowButton widthUpArrow;
    /**Strzalka do zmniejszania szerokosci pola*/
    BasicArrowButton widthDownArrow;

    /**Przycisk do rozpoczecia gry*/
    JButton startButton;

    /**Konstruktor wyswietla wszystkie ustawienia
     *
     * @param minesQuantity domyslna ilosc min
     * @param width domyslna szerokosc
     * @param height domyslna wysokosc
     */
    public Settings(int minesQuantity, int width, int height )
    {
        addWidthFields( width );
        addHeightFields( height );
        addMinesFields( minesQuantity );
        addStartButton();

        this.frame.getContentPane().setLayout( new GridLayout( 4, 1 ) );
        this.frame.add( widthPanel );
        this.frame.add( heightPanel );
        this.frame.add( minesPanel );
        this.frame.add( startButton );
        this.frame.setSize( 300,300 );
        this.frame.setTitle( "Settings" );
        this.frame.setVisible( true );
    }

    /**Funkcja wyswietla pola do ustawiania szerokosci (napis, pole do wpisania, strzalki )
     *
     * @param width poczatkowa wartosc wpisana w pole
     */
    void addWidthFields( int width )
    {
        widthPanel = new JPanel();
        widthPanel.setLayout( new GridBagLayout() );
        widthLabel = new JLabel("Szerokosc pola: " );
        widthField = new JTextField( String.valueOf( width ), 2 );
        widthUpArrow = new BasicArrowButton( SwingConstants.NORTH );
        widthUpArrow.setActionCommand( "upWidth" );
        widthDownArrow = new BasicArrowButton( SwingConstants.SOUTH );
        widthDownArrow.setActionCommand( "downWidth" );

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 4;
        widthPanel.add( widthLabel, gbc );
        gbc.gridx = 4;
        gbc.gridwidth = 1;
        widthPanel.add( widthField, gbc );
        gbc.gridx = 5;
        gbc.gridheight = 1;
        widthPanel.add( widthUpArrow, gbc );
        gbc.gridy = 1;
        widthPanel.add( widthDownArrow, gbc );
    }
    /**Funkcja wyswietla pola do ustawiania wysokosci (napis, pole do wpisania, strzalki )
     *
     * @param height poczatkowa wartosc wpisana w pole
     */
    void addHeightFields( int height )
    {
        heightPanel = new JPanel();
        heightPanel.setLayout( new GridBagLayout() );
        heightLabel = new JLabel("Wysokosc pola: " );
        heightField = new JTextField( String.valueOf( height ), 2 );
        heightUpArrow = new BasicArrowButton( SwingConstants.NORTH );
        heightUpArrow.setActionCommand( "upHeight" );
        heightDownArrow = new BasicArrowButton( SwingConstants.SOUTH );
        heightDownArrow.setActionCommand( "downHeight" );

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 4;
        heightPanel.add( heightLabel, gbc );
        gbc.gridx = 4;
        gbc.gridwidth = 1;
        heightPanel.add( heightField, gbc );
        gbc.gridx = 5;
        gbc.gridheight = 1;
        heightPanel.add( heightUpArrow, gbc );
        gbc.gridy = 1;
        heightPanel.add( heightDownArrow, gbc );
    }
    /**Funkcja wyswietla pola do ustawiania ilosci min (napis, pole do wpisania, strzalki )
     *
     * @param minesQuantity poczatkowa wartosc wpisana w pole
     */
    void addMinesFields( int minesQuantity )
    {
        minesPanel = new JPanel();
        minesPanel.setLayout( new GridBagLayout() );
        minesLabel = new JLabel("Procentowa ilosc min: " );
        minesField = new JTextField( String.valueOf( minesQuantity ), 2 );
        minesUpArrow = new BasicArrowButton( SwingConstants.NORTH );
        minesUpArrow.setActionCommand( "upMines" );
        minesDownArrow = new BasicArrowButton( SwingConstants.SOUTH );
        minesDownArrow.setActionCommand( "downMines" );

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 4;
        minesPanel.add( minesLabel, gbc );
        gbc.gridx = 4;
        gbc.gridwidth = 1;
        minesPanel.add( minesField, gbc );
        gbc.gridx = 5;
        gbc.gridheight = 1;
        minesPanel.add( minesUpArrow, gbc );
        gbc.gridy = 1;
        minesPanel.add( minesDownArrow, gbc );
    }

    void addStartButton()
    {
        startButton = new JButton( "Start" );
        startButton.setActionCommand( "start" );
    }

    /**Funkcja dodaje sluchacza, ktory nasluchuje akcji po wcisnieciu roznych przyciskow
     *
     * @param action Sluchacz, ktory nasluchuje danego przycisku
     */
    void listener( ActionListener action )
    {
        startButton.addActionListener( action );
        widthUpArrow.addActionListener( action );
        widthDownArrow.addActionListener( action );
        heightUpArrow.addActionListener( action );
        heightDownArrow.addActionListener( action );
        minesUpArrow.addActionListener( action );
        minesDownArrow.addActionListener( action );
    }

    /**Funkcja wpisuje dana wartosc do pola z szerokoscia
     *
     * @param width dana, ktora zostanie wpisana
     */
    void setWidth( int width )
    {
        widthField.setText( "" + width );
    }
    /**Funkcja wpisuje dana wartosc do pola z wysokoscia
     *
     * @param height dana, ktora zostanie wpisana
     */
    void setHeight( int height )
    {
        heightField.setText( "" + height );
    }
    /**Funkcja wpisuje dana wartosc do pola z iloscia min
     *
     * @param mines dana, ktora zostanie wpisana
     */
    void setMines( int mines )
    {
        minesField.setText( "" + mines );
    }
    /**Funkcja sprawdza poprawnosc wprowadzonych danych i zwraca wpisana szerokosc*/
    int getWidth()
    {
        int i = 0;
        try{
            i = parseInt( widthField.getText() );
        }catch( NumberFormatException e ){}
        if( i <= 50 && i >= 4 )
            return i;
        return 0;
    }
    /**Funkcja sprawdza poprawnosc wprowadzonych danych i zwraca wpisana wysokosc*/
    int getHeight()
    {
        int i = 0;
        try{
            i = parseInt( heightField.getText() );
        }catch( NumberFormatException e ){}
        if( i <= 50 && i >= 4 )
            return i;
        return 0;
    }
    /**Funkcja sprawdza poprawnosc wprowadzonych danych i zwraca wpisana ilosc min*/
    int getMines()
    {
        int i = 0;
        try{
            i = parseInt( minesField.getText() );
        }catch( NumberFormatException e ){}
        if( i <= 80 && i >= 10)
            return i;
        return 0;
    }

}