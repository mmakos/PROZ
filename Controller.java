import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**Klasa Kontroler tworzy rozgrywke i zawiaduje tym co dzieje sie w klasie Model oraz View*/
class Controller{
    /**Obiekt klasy Model*/
    private final Model model;
    /**Obiekt klasy View*/
    private View view;
    /**Obiekt klasy wewnetrzej GameListener*/
    private GameListener listener;

    /**Konstruktor klasy wywolywany przy starcie gry
     * Tworzy nowy Model z domyslnymi wartosciami, nowy widok (ustawienia) i wlacza nasluchiwanie
     */
    private Controller(){
        model = new Model( 16, 16, 20 );
        view = new Settings( 20, 16, 16 );
        view.listener( new SettingsListener() );
        view.frame.addMouseListener( new SettingsMouseListener() );
    }

    /**Konstruktor klasy wywolywany przy kliknieciu ustawien podczas rozgrywki
     * Otwiera okno z ustawieniami zachowujace poprzednie wartosci
     * @param width poprzednia szerokosc
     * @param height poprzednia wysokosc
     * @param mines poprzednia ilosc min
     */
    private Controller( int width, int height, int mines ){
        model = new Model( width, height, mines );
        view = new Settings( mines, width, height );
        view.listener( new SettingsListener() );
        view.frame.addMouseListener( new SettingsMouseListener() );
    }

    /**Main - funkcja od ktorej program rozpoczyna dzialanie, tworzy obiekt klasy kontroler*/
    public static void main( String [] args ){
        new Controller();
    }

    /**funkcja tworzy nowe okno - okno gry*/
    private void startGame(){
        view.setVisible( false );
        model.newModel();
        view = new Game( model.setWidth( 0 ), model.setHeight( 0 ), model.countMinesQuantity() );
        listener = new GameListener();
        view.listener( listener );
    }

    /**Funkcja zamyka okno gry i otwiera okno z ustawieniami*/
    private void toMenu(){
        view.setVisible( false );
        new Controller( model.setWidth( 0 ), model.setHeight( 0 ), model.setMines( 0 ) );
    }

    /**Funkcja wywoluje odpowiednie funkcje przy przegraniu*/
    private void loseGame(){
        view.removeButtonListeners( listener );
    }

    /**Funkcja wywoluje odpowiednie funkcje przy wygraniu*/
    private void winGame()
    {
        view.removeButtonListeners( listener );
        view.setEndImage( "src/img/win.png" );
    }

    /**Klasa nasluchuje klikniec w przyciski ustawien wywoluje inkrementajce i dekrementacje ustawien jesli nacisnieto strzalki*/
    class SettingsListener implements ActionListener{
        @Override
        public void actionPerformed( ActionEvent actionEvent ){
            String actionCommand = actionEvent.getActionCommand();

            switch( actionCommand ){
                case "upWidth":
                    view.setWidth( model.setWidth( -1 ) );
                    break;
                case "downWidth":
                    view.setWidth( model.setWidth( -2 ) );
                    break;
                case "upHeight":
                    view.setHeight( model.setHeight( -1 ) );
                    break;
                case "downHeight":
                    view.setHeight( model.setHeight( -2 ) );
                    break;
                case "upMines":
                    view.setMines( model.setMines( -1 ) );
                    break;
                case "downMines":
                    view.setMines( model.setMines( -2 ) );
                    break;
                case "start":
                    view.setWidth( model.setWidth( view.getWidth() ) );
                    view.setHeight( model.setHeight( view.getHeight() ) );
                    view.setMines( model.setMines( view.getMines() ) );
                    startGame();
                    break;
            }
        }
    }

    /**Klasa nasluchuje klikniecia myszka gdziekolwiek aby potwierdzic wprowadzone ustawienia, ustawia wartosc na ta z pola tekstowego jesli kliknieto przycisk myszy*/
    public class SettingsMouseListener implements MouseListener{
        @Override
        public void mouseClicked( MouseEvent mouseEvent ){
            view.setWidth( model.setWidth( view.getWidth() ) );
            view.setHeight( model.setHeight( view.getHeight() ) );
            view.setMines( model.setMines( view.getMines() ) );
        }

        @Override
        public void mousePressed( MouseEvent mouseEvent ){
        }

        @Override
        public void mouseReleased( MouseEvent mouseEvent ){
        }

        @Override
        public void mouseEntered( MouseEvent mouseEvent ){
        }

        @Override
        public void mouseExited( MouseEvent mouseEvent ){
        }
    }

    /**Klasa nasluchuje akcji podczas rozgrywki*/
    public class GameListener implements MouseListener{
        /**Funkcja nasluchuje zdarzen i wykonuje odpowiednie czynnosci w zaleznosci od odebranego zdarzenia
         *
         * @param mouseEvent uslyszane zdarzenie
         */
        @Override
        public void mouseClicked( MouseEvent mouseEvent ){
            int[] hash = view.buttonHash( mouseEvent.getSource().hashCode() );
            if( mouseEvent.getButton() == MouseEvent.BUTTON1 && model.getDisplayed( hash[ 0 ], hash[ 1 ] ) != 1 ){
                int stan = model.clickButton( hash[ 0 ], hash[ 1 ], view );
                switch( stan ){
                    case -3:
                        view.drawLabelField( model.putMines( hash[ 0 ], hash[ 1 ] ) );
                        model.clickButton( hash[ 0 ], hash[ 1 ], view );
                        break;
                    case -2:
                        toMenu();
                        break;
                    case -1:
                        startGame();
                        break;
                    case 0:
                        loseGame();
                        break;
                    case 2:
                        winGame();
                        break;
                }
            }else if( mouseEvent.getButton() == MouseEvent.BUTTON3){
                switch( model.getDisplayed( hash[ 0 ], hash[ 1 ] ) ){
                    case 0:
                        if( view.getMinesLeft() <= 0 ) break;
                        model.setDisplayed( 1, hash[ 0 ], hash[ 1 ] );
                        view.setImageButton( 1, hash[ 0 ], hash[ 1 ] );
                        view.addMinesLeft( -1 );
                        break;
                    case 1:
                        model.setDisplayed( 2, hash[ 0 ], hash[ 1 ] );
                        view.setImageButton( 2, hash[ 0 ], hash[ 1 ] );
                        view.addMinesLeft( 1 );
                        break;
                    case 2:
                        model.setDisplayed( 0, hash[ 0 ], hash[ 1 ] );
                        view.setImageButton( 0, hash[ 0 ], hash[ 1 ] );
                        break;
                }
            }

        }

        @Override
        public void mousePressed( MouseEvent mouseEvent ){
        }

        @Override
        public void mouseReleased( MouseEvent mouseEvent ){
        }

        @Override
        public void mouseEntered( MouseEvent mouseEvent ){
        }

        @Override
        public void mouseExited( MouseEvent mouseEvent ){
        }
    }
}