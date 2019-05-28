import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**Klasa View - klasa z pustymi funkcjami, po ktorej dziedziczy klasa Settings i Game*/
class View
{
    /**Wyswietlane okno*/
    JFrame frame;

    /**Konstruktor tworzy okno i ustawia odpowiednie wlasciwosci tego okna*/
    View()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setResizable( false );
        frame.setLocationRelativeTo( null );
    }

    void listener( ActionListener action ){}
    void listener( MouseListener action ){}
    void removeButtonListeners( MouseListener action ){}
    void setWidth( int i ){}
    void setHeight( int i ){}
    void setMines( int i ){}
    int getWidth(){ return 0; }
    int getHeight(){ return 0; }
    int getMines(){ return 0; }
    void drawLabelField( int[][] i ){}
    void hideButton( int x, int y ){}
    int[] buttonHash( int hash ){ int[] i = { 0, 0 }; return i; }
    void openAllMines( int[][] minesField, int[][] displayed ){}
    void setImageLabel( int x, int y, int number ){}
    void setImageButton( int a, int b, int c ){}
    void addMinesLeft( int a ){}
    int getMinesLeft(){ return 0; }
    void setEndImage( String name ){}
    void setVisible( boolean visible ){ frame.setVisible( visible ); }
}
