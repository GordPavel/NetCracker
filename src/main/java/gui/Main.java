package gui;

import buildings.dwelling.Dwelling;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import util.Buildings;
import util.factories.DwellingFactory;
import util.factories.OfficeFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

import static java.lang.Math.max;

public class Main extends JFrame{

    private JScrollPane scrollPane;
    private JPanel      buildingPanel;
    private JLabel      buildingInfo;
    private JLabel      floorInfo;
    private JLabel      spaceInfo;
    private Container   allContent;

    private void showBuilding( Building building ){
        buildingPanel.removeAll();
        List<Floor> floors = new ArrayList<>( Arrays.asList( building.getFloors() ) );
        Collections.reverse( floors );
        int floorsIndex = floors.size(), spacesIndex;
        buildingPanel.setBounds( 0 , 0 , Arrays.stream( building.getFloors() ).mapToInt( Floor::getSpacesCount ).max()
                                               .orElse( scrollPane.getWidth() ) ,
                                 max( building.getFloorsCount() * 20 , scrollPane.getHeight() ) );
        for( Floor floor : floors ){
            JPanel floorPanel = new JPanel();
            spacesIndex = 0;
            floorPanel.setLayout( new BoxLayout( floorPanel , BoxLayout.X_AXIS ) );
            floorPanel.setBounds( 0 , 0 , buildingPanel.getWidth() , 20 );
            floorPanel.setBorder( BorderFactory.createTitledBorder( String.valueOf( floorsIndex-- ) ) );
            for( Space space : floor ){
                JButton button = new JButton( String.valueOf( ++spacesIndex ) );
                int     i      = floorsIndex, j = spacesIndex;
                button.addActionListener( event -> {
                    showFloor( i , floor );
                    showSpace( j , space );
                } );
                floorPanel.add( button , spacesIndex - 1 );
            }
            floorPanel.setAlignmentX( LEFT_ALIGNMENT );
            buildingPanel.add( floorPanel );
        }
        buildingPanel.updateUI();

        buildingInfo.setText( String.format( "<html>Type : %s<br>Floors count: %d<br>All area : %.2f</html>" ,
                                             building instanceof Dwelling ? "Dwelling" : "Office building" ,
                                             building.getFloorsCount() , building.getSpacesArea() ) );
    }

    private void showFloor( int index , Floor floor ){
        floorInfo.setText( String.format( "<html>Index : %d<br>Spaces count : %d<br>All area : %.2f</html>" , index ,
                                          floor.getSpacesCount() , floor.getSpacesArea() ) );
    }

    private void showSpace( int index , Space space ){
        spaceInfo.setText( String.format( "<html>Index : %d<br>Rooms count : %d<br>Area : %.2f</html>" , index ,
                                          space.getRoomsCount() , space.getArea() ) );
    }

    private void clearPanes(){
        buildingPanel.removeAll();
        buildingPanel.removeAll();
        buildingInfo.removeAll();
        floorInfo.removeAll();
        spaceInfo.removeAll();
    }

    private Main() throws HeadlessException{
        super( "Buildings" );
        this.setBounds( 0 , 0 , 600 , 400 );
        this.setMinimumSize( new Dimension( 500 , 300 ) );
        this.setMaximumSize( new Dimension( 1300 , 800 ) );
        this.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

        this.setJMenuBar( initMenuBar() );

        allContent = this.getContentPane();
        allContent.setLayout( new GridLayout( 1 , 4 ) );

        buildingPanel = initBuildingPanel();
        buildingInfo = initBuildingInfo();
        floorInfo = initFloorInfo();
        spaceInfo = initSpaceInfo();

        this.addComponentListener( new ComponentListener(){
            @Override
            public void componentResized( ComponentEvent e ){
                buildingPanel.setBounds( 0 , 0 , max( buildingPanel.getWidth() , scrollPane.getWidth() ) ,
                                         max( buildingPanel.getHeight() , scrollPane.getHeight() ) );
            }

            @Override
            public void componentMoved( ComponentEvent e ){
            }

            @Override
            public void componentShown( ComponentEvent e ){

            }

            @Override
            public void componentHidden( ComponentEvent e ){

            }
        } );

        setVisible( true );
    }

    private JMenuBar initMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu     open         = new JMenu( "Open" );
        JMenuItem openDwelling = new JMenuItem( "Open dwelling…" );
        openDwelling.addActionListener( event -> {
            Buildings.setFactory( new DwellingFactory() );
            openFileChooser().ifPresentOrElse( this::showBuilding , this::clearPanes );
        } );
        JMenuItem openOffice = new JMenuItem( "Open office building…" );
        openOffice.addActionListener( event -> {
            Buildings.setFactory( new OfficeFactory() );
            openFileChooser().ifPresentOrElse( this::showBuilding , this::clearPanes );
        } );

        open.add( openDwelling );
        open.add( openOffice );

        JMenu       lookAndFeel = new JMenu( "Look&Feel" );
        ButtonGroup group       = new ButtonGroup();

        Arrays.stream( UIManager.getInstalledLookAndFeels() ).forEachOrdered( design -> {
            JRadioButtonMenuItem radioButtonMenuItem = new JRadioButtonMenuItem( design.getName() );
            if( design.getName().equals( "Mac OS X" ) ) radioButtonMenuItem.setSelected( true );
            radioButtonMenuItem.addActionListener( e -> {
                try{
                    UIManager.setLookAndFeel( design.getClassName() );
                    SwingUtilities.updateComponentTreeUI( getContentPane() );
                }catch( UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException e1 ){
                    e1.printStackTrace();
                }
            } );
            group.add( radioButtonMenuItem );
            lookAndFeel.add( radioButtonMenuItem );
        } );

        menuBar.add( open );
        menuBar.add( lookAndFeel );
        return menuBar;
    }

    private JPanel initBuildingPanel(){
        JPanel panel = new JPanel();
        panel.setBorder( BorderFactory.createTitledBorder( "Building panel" ) );
        panel.setLayout( new BorderLayout() );
        this.buildingPanel = new JPanel();
        buildingPanel.setLayout( new BoxLayout( buildingPanel , BoxLayout.Y_AXIS ) );
        scrollPane = new JScrollPane( buildingPanel , ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED ,
                                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        buildingPanel.setBounds( 0 , 0 , scrollPane.getWidth() , scrollPane.getHeight() );
        panel.add( scrollPane );
        panel.setVisible( true );
        allContent.add( panel );
        return buildingPanel;
    }

    private JLabel initBuildingInfo(){
        JPanel panel = new JPanel();
        panel.setBorder( BorderFactory.createTitledBorder( "Building info" ) );
        panel.setVisible( true );
        JLabel buildingInfo = new JLabel( "" );
        panel.add( buildingInfo );
        allContent.add( panel );
        return buildingInfo;
    }

    private JLabel initFloorInfo(){
        JPanel panel = new JPanel();
        panel.setBorder( BorderFactory.createTitledBorder( "Floor info" ) );
        JLabel floorInfo = new JLabel( "" );
        panel.add( floorInfo );
        allContent.add( panel );
        return floorInfo;
    }

    private JLabel initSpaceInfo(){
        JPanel panel = new JPanel();
        panel.setBorder( BorderFactory.createTitledBorder( "Space info" ) );
        JLabel space = new JLabel( "" );
        panel.add( space );
        allContent.add( panel );
        return space;
    }

    private Optional<Building> openFileChooser(){
        JFileChooser fileChooser = new JFileChooser( "/Users/pavelgordeev/Desktop/buildings" );
        boolean      isDialogFinished;
        do{
            if( fileChooser.showDialog( this , "Open" ) == JFileChooser.APPROVE_OPTION ){
                try( Scanner scanner = new Scanner( fileChooser.getSelectedFile() ) ){
                    return Optional.of( Buildings.readBuilding( scanner ) );
                }catch( FileNotFoundException e ){
                    isDialogFinished = showError( fileChooser , e ) == JOptionPane.OK_OPTION;
                }
            }else{
                isDialogFinished = true;
            }
        }while( !isDialogFinished );
        return Optional.empty();
    }

    private int showError( Container parent , Exception exception ){
        return JOptionPane.showConfirmDialog( parent , exception.getMessage() , "Error" , JOptionPane.OK_CANCEL_OPTION ,
                                              JOptionPane.ERROR_MESSAGE );
    }

    public static void main( String[] args ){
        new Main();
    }

}
