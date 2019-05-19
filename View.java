import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class View
{
    public JFrame frame;

    public View()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setResizable( false );
        frame.setLocationRelativeTo( null );
    }

    void listener( ActionListener action ){}
    void setWidth( int i ){}
    void setHeight( int i ){}
    void setMines( int i ){}
    int getWidth(){ return 0; }
    int getHeight(){ return 0; }
    int getMines(){ return 0; }
}
