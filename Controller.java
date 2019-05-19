import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class Controller{
    private Model model;
    private View view;

    private Controller(){
        model = new Model( 16, 16, 15 );
        view = new Settings( 15, 16, 16 );
        view.listener( new SettingsListener() );
        view.frame.addMouseListener( new SettingsMouseListener() );
    }

    public static void main( String args[] ){
        Controller c = new Controller();
    }

    class SettingsListener implements ActionListener{
        @Override
        public void actionPerformed( ActionEvent actionEvent ){
            String actionCommand = actionEvent.getActionCommand();

            switch ( actionCommand ){
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
                    //startGame();
                    break;
            }
        }
    }

    public class SettingsMouseListener implements MouseListener{
        @Override
        public void mouseClicked( MouseEvent mouseEvent ){
            view.setWidth( model.setWidth( view.getWidth() ) );
            view.setHeight( model.setHeight( view.getHeight() ) );
            view.setMines( model.setMines( view.getMines() ) );
        }

        @Override
        public void mousePressed( MouseEvent mouseEvent ){}

        @Override
        public void mouseReleased( MouseEvent mouseEvent ){}

        @Override
        public void mouseEntered( MouseEvent mouseEvent ){}

        @Override
        public void mouseExited( MouseEvent mouseEvent ){}
    }
}